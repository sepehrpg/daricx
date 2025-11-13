package com.example.network.coins


import com.example.model.sort.CoinsSort
import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.datasource.coins.CoinsDataSourceImpl
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
 * CoinsDataSourceImplTest
 *
 * Test Goal:
 * - Ensure DataSource forwards domain params to API correctly.
 * - Ensure CoinsSort enum maps to order query param (or null).
 * - Ensure HTTP errors bubble up.
 *
 * Scenarios:
 * 1) MarketCapDesc → "market_cap_desc" in query string.
 * 2) Client-only sort (PriceAsc) → null order param.
 * 3) Server returns HTTP 500 → throws HttpException.
 */
class CoinsDataSourceImplTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService
    private lateinit var ds: CoinsDataSource

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
        ds = CoinsDataSourceImpl(api)
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `forwards MarketCapDesc as market_cap_desc`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.marketsResponse))

        ds.getCoinMarkets(
            vsCurrency = "usd", page = 3, perPage = 100,
            order = CoinsSort.MarketCapDesc, sparkline = false,
            priceChangePercentage = "24h,7d", locale = "en", precision = "2"
        )

        val req = server.takeRequest()
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/markets")
        assertThat(req.requestUrl?.queryParameter("vs_currency")).isEqualTo("usd")
        assertThat(req.requestUrl?.queryParameter("page")).isEqualTo("3")
        assertThat(req.requestUrl?.queryParameter("per_page")).isEqualTo("100")
        assertThat(req.requestUrl?.queryParameter("order")).isEqualTo("market_cap_desc")
        assertThat(req.requestUrl?.queryParameter("sparkline")).isEqualTo("false")
        assertThat(req.requestUrl?.queryParameter("price_change_percentage")).isEqualTo("24h,7d")
        assertThat(req.requestUrl?.queryParameter("locale")).isEqualTo("en")
        assertThat(req.requestUrl?.queryParameter("precision")).isEqualTo("2")
    }

    @Test fun `client-only sort PriceAsc results in null order`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.marketsResponse))

        ds.getCoinMarkets(
            vsCurrency = "eur", page = 1, perPage = 25,
            order = CoinsSort.PriceAsc, sparkline = null,
            priceChangePercentage = null, locale = null, precision = null
        )

        val req = server.takeRequest()
        assertThat(req.requestUrl?.queryParameter("order")).isNull()
    }

    @Test(expected = HttpException::class)
    fun `http 500 bubbles as HttpException`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("""{"error":"server"}"""))

        ds.getCoinMarkets(
            vsCurrency = "usd", page = 1, perPage = 25,
            order = CoinsSort.VolumeDesc, sparkline = true,
            priceChangePercentage = "24h", locale = "en", precision = "2"
        )
    }
}
