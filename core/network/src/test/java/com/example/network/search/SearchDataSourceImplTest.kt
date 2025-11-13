package com.example.network.search


import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.example.network.datasource.search.SearchDataSource
import com.example.network.datasource.search.SearchDataSourceImpl
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
 * SearchDataSourceImplTest
 *
 * Test Goal:
 * - Ensure DataSource forwards query param to API.
 * - Propagates HTTP errors as HttpException.
 *
 * Scenarios:
 * 1) query="ethereum" -> request contains correct query param.
 * 2) HTTP 500 -> throws HttpException.
 */
class SearchDataSourceImplTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService
    private lateinit var ds: SearchDataSource

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
        ds = SearchDataSourceImpl(api)
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `forwards query param`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonSearch.response))

        ds.search("ethereum")
        val req = server.takeRequest()

        assertThat(req.requestUrl?.encodedPath).isEqualTo("/search")
        assertThat(req.requestUrl?.queryParameter("query")).isEqualTo("ethereum")
    }

    @Test(expected = HttpException::class)
    fun `http 500 bubbles as HttpException`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("""{"error":"server"}"""))
        ds.search("btc")
    }
}
