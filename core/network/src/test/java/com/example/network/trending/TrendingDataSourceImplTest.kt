package com.example.network.trending


import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.example.network.datasource.trending.TrendingDataSource
import com.example.network.datasource.trending.TrendingDataSourceImpl
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
 * TrendingDataSourceImplTest
 *
 * Test Goal:
 * - Ensure DataSource calls correct endpoint.
 * - Propagates errors as HttpException.
 *
 * Scenarios:
 * 1) Request hits /search/trending.
 * 2) HTTP 500 -> throws HttpException.
 */
class TrendingDataSourceImplTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService
    private lateinit var ds: TrendingDataSource

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
        ds  = TrendingDataSourceImpl(api)
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `calls trending endpoint`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonTrending.response))

        ds.getTrending()
        val req = server.takeRequest()

        assertThat(req.requestUrl?.encodedPath).isEqualTo("/search/trending")
    }

    @Test(expected = HttpException::class)
    fun `http 500 bubbles as HttpException`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("""{"error":"server"}"""))
        ds.getTrending()
    }
}
