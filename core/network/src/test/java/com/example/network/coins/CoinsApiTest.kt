package com.example.network.coins


import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.create


/**
 * CoinsApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for GET /coins/markets endpoint.
 * - Ensure required and optional query parameters are passed correctly.
 * - Verify deserialization of markets payload.
 *
 * Scenarios:
 * 1) Call with only required params → sets vs_currency, page, per_page; omits optional.
 * 2) Call with all params → query contains every expected key/value.
 * 3) Deserialize valid markets payload into CoinsListDto with nested sparkline.
 */
class CoinsApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `GET without optional params sets required vs_currency and paging`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.marketsResponse))

        api.getCoinMarkets(
            vsCurrency = "usd",
            page = 1,
            perPage = 50,
            order = null,
            sparkline = null,
            priceChangePercentage = null,
            locale = null,
            precision = null
        )

        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/markets")
        assertThat(req.requestUrl?.queryParameter("vs_currency")).isEqualTo("usd")
        assertThat(req.requestUrl?.queryParameter("page")).isEqualTo("1")
        assertThat(req.requestUrl?.queryParameter("per_page")).isEqualTo("50")
        // no optional params
        assertThat(req.requestUrl?.queryParameter("order")).isNull()
        assertThat(req.requestUrl?.queryParameter("sparkline")).isNull()
        assertThat(req.requestUrl?.queryParameter("price_change_percentage")).isNull()
        assertThat(req.requestUrl?.queryParameter("locale")).isNull()
        assertThat(req.requestUrl?.queryParameter("precision")).isNull()
    }

    @Test fun `GET with all params includes query`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.marketsResponse))

        api.getCoinMarkets(
            vsCurrency = "eur",
            page = 2,
            perPage = 25,
            order = "market_cap_desc",
            sparkline = true,
            priceChangePercentage = "1h,24h,7d",
            locale = "en",
            precision = "2"
        )

        val req = server.takeRequest()
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/markets")
        assertThat(req.requestUrl?.queryParameter("vs_currency")).isEqualTo("eur")
        assertThat(req.requestUrl?.queryParameter("page")).isEqualTo("2")
        assertThat(req.requestUrl?.queryParameter("per_page")).isEqualTo("25")
        assertThat(req.requestUrl?.queryParameter("order")).isEqualTo("market_cap_desc")
        assertThat(req.requestUrl?.queryParameter("sparkline")).isEqualTo("true")
        assertThat(req.requestUrl?.queryParameter("price_change_percentage")).isEqualTo("1h,24h,7d")
        assertThat(req.requestUrl?.queryParameter("locale")).isEqualTo("en")
        assertThat(req.requestUrl?.queryParameter("precision")).isEqualTo("2")
    }

    @Test fun `deserializes markets json`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.marketsResponse))
        val dto = api.getCoinMarkets(
            vsCurrency = "usd", page = 1, perPage = 1,
            order = null, sparkline = true, priceChangePercentage = "24h",
            locale = "en", precision = "full"
        )
        assertThat(dto).hasSize(1)
        val first = dto.first()
        assertThat(first.id).isEqualTo("bitcoin")
        assertThat(first.symbol).isEqualTo("btc")
        assertThat(first.currentPrice).isWithin(0.001).of(65234.12)
        assertThat(first.sparklineIn7d?.price).containsAtLeast(65000.0, 65234.12)
        assertThat(first.lastUpdated).isEqualTo("2025-09-01T00:00:00Z")
    }
}
