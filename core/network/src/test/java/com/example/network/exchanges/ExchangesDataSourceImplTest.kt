package com.example.network.exchanges


import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.datasource.exchanges.ExchangesDataSourceImpl
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
 * ExchangesDataSourceImplTest
 *
 * Test Goal:
 * - Ensure DataSource forwards paging parameters to API correctly.
 * - Ensure HTTP errors bubble up as HttpException.
 *
 * Scenarios:
 * 1) page=3, perPage=50 → query contains page=3 & per_page=50.
 * 2) HTTP 500 → throws HttpException.
 */
class ExchangesDataSourceImplTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService
    private lateinit var ds: ExchangesDataSource

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
        ds  = ExchangesDataSourceImpl(api)
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `forwards paging params`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonExchanges.exchangesResponse))

        ds.getExchanges(page = 3, perPage = 50)
        val req = server.takeRequest()
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/exchanges")
        assertThat(req.requestUrl?.queryParameter("page")).isEqualTo("3")
        assertThat(req.requestUrl?.queryParameter("per_page")).isEqualTo("50")
    }

    @Test(expected = HttpException::class)
    fun `http 500 bubbles as HttpException`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("""{"error":"server"}"""))
        ds.getExchanges(page = 1, perPage = 25)
    }
}
