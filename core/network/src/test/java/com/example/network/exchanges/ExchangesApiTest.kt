package com.example.network.exchanges


import com.example.network.TestNetwork
import com.example.network.api.ApiService
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
 * ExchangesApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for GET /exchanges endpoint.
 * - Ensure default and custom query parameters are sent correctly.
 * - Verify deserialization of exchanges payload.
 *
 * Scenarios:
 * 1) Call with no args → defaults per_page=100, page=1 and correct path.
 * 2) Call with explicit per_page/page → query contains expected values.
 * 3) Deserialize a valid JSON payload into ExchangesListDto.
 * 4) Server returns 404 → propagated as HttpException.
 */
class ExchangesApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `GET with defaults sets per_page=100 and page=1`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonExchanges.exchangesResponse))

        api.getExchanges() // uses defaults from interface
        val req = server.takeRequest()

        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/exchanges")
        assertThat(req.requestUrl?.queryParameter("per_page")).isEqualTo("100")
        assertThat(req.requestUrl?.queryParameter("page")).isEqualTo("1")
    }

    @Test fun `GET with explicit paging includes query`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonExchanges.exchangesResponse))

        api.getExchanges(perPage = 25, page = 2)
        val req = server.takeRequest()

        assertThat(req.requestUrl?.encodedPath).isEqualTo("/exchanges")
        assertThat(req.requestUrl?.queryParameter("per_page")).isEqualTo("25")
        assertThat(req.requestUrl?.queryParameter("page")).isEqualTo("2")
    }

    @Test fun `deserializes exchanges json`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonExchanges.exchangesResponse))

        val dto = api.getExchanges()
        assertThat(dto).hasSize(1)
        val first = dto.first()
        assertThat(first.id).isEqualTo("binance")
        assertThat(first.name).isEqualTo("Binance")
        assertThat(first.trustScore).isEqualTo(10)
        assertThat(first.tradeVolume24hBtc).isWithin(0.001).of(123456.789)
        assertThat(first.yearEstablished).isEqualTo(2017)
    }

    @Test(expected = HttpException::class)
    fun `http 404 bubbles up as HttpException`() = runTest {
        server.enqueue(MockResponse().setResponseCode(404).setBody("""{"error":"not found"}"""))
        api.getExchanges()
    }
}
