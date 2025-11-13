package com.example.network.global


import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.example.network.datasource.global.GlobalDataSource
import com.example.network.datasource.global.GlobalDataSourceImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.create

/**
 * GlobalDataSourceImplTest
 *
 * Test Goal:
 * - Ensure DataSource calls the API once and forwards the response.
 * - Propagate HTTP errors (non-2xx) as HttpException.
 *
 * Scenarios:
 * 1) 200 OK -> returns DTO; request path is "/global".
 * 2) 500 -> throws HttpException.
 */
class GlobalDataSourceImplTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService
    private lateinit var ds: GlobalDataSource

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
        ds  = GlobalDataSourceImpl(api)
    }

    @After fun tearDown() { server.shutdown() }

    @Test
    fun `ok response returns dto and correct path`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonGlobal.globalResponse))

        val dto = ds.getGlobal()
        val req = server.takeRequest()

        assertThat(req.requestUrl?.encodedPath).isEqualTo("/global")
        assertThat(dto.data?.markets).isEqualTo(830)
    }

    @Test(expected = HttpException::class)
    fun `http 500 bubbles as HttpException`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("""{"error":"server"}"""))
        ds.getGlobal()
    }
}
