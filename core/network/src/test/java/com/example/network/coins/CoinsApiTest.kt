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
 * - Validate HTTP contract for Coins endpoints.
 * - Ensure required and optional query parameters are passed correctly.
 * - Verify basic deserialization for DTOs.
 */
class CoinsApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService


    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    // -------------------------------------------------------------------------
    // /coins/markets
    // -------------------------------------------------------------------------
    @Test
    fun `getCoinMarkets uses GET and sends default query parameters`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.marketsResponse))

        api.getCoinMarkets(
            vsCurrency = "usd",
        )

        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/markets")
        assertThat(req.requestUrl?.queryParameter("vs_currency")).isEqualTo("usd")
        assertThat(req.requestUrl?.queryParameter("page")).isNull()
        assertThat(req.requestUrl?.queryParameter("per_page")).isNull()
        // no optional params
        assertThat(req.requestUrl?.queryParameter("order")).isNull()
        assertThat(req.requestUrl?.queryParameter("sparkline")).isNull()
        assertThat(req.requestUrl?.queryParameter("price_change_percentage")).isNull()
        assertThat(req.requestUrl?.queryParameter("locale")).isNull()
        assertThat(req.requestUrl?.queryParameter("precision")).isNull()
    }

    // Need to Better Sample And Improve Test
    @Test
    fun `deserializes coins markets json response`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.marketsResponse))
        val dto = api.getCoinMarkets(
            vsCurrency = "usd",
            page = 1,
            perPage = 1,
            order = null,
            sparkline = true,
            priceChangePercentage = "24h",
            locale = "en",
            precision = "full"
        )
        assertThat(dto).hasSize(1)
        val first = dto.first()
        assertThat(first.id).isEqualTo("bitcoin")
        assertThat(first.symbol).isEqualTo("btc")
        assertThat(first.currentPrice).isWithin(0.001).of(65234.12)
        assertThat(first.sparklineIn7d?.price).containsAtLeast(65000.0, 65234.12)
        assertThat(first.lastUpdated).isEqualTo("2025-09-01T00:00:00Z")
    }

    // -------------------------------------------------------------------------
    // /coins/{id}
    // -------------------------------------------------------------------------


    @Test
    fun `getCoinDetail uses GET and sends default query parameters`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.sampleCoinDetailResponse))

        api.getCoinDetail(id = "bitcoin")

        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/bitcoin")

        // Boolean flags default
        assertThat(req.requestUrl?.queryParameter("localization")).isEqualTo("true")
        assertThat(req.requestUrl?.queryParameter("tickers")).isEqualTo("true")
        assertThat(req.requestUrl?.queryParameter("market_data")).isEqualTo("true")
        assertThat(req.requestUrl?.queryParameter("community_data")).isEqualTo("true")
        assertThat(req.requestUrl?.queryParameter("developer_data")).isEqualTo("true")
        assertThat(req.requestUrl?.queryParameter("sparkline")).isEqualTo("false")

        // dex_pair_format default
        assertThat(req.requestUrl?.queryParameter("dex_pair_format")).isEqualTo("contract_address")
    }

    // Need to Better Sample And Improve Test
    @Test
    fun `deserializes coin detail json response`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.sampleCoinDetailResponse))

        val dto = api.getCoinDetail(
            id = "bitcoin",
            localization = false,
            tickers = false,
            marketData = true,
            communityData = false,
            developerData = false,
            sparkline = false,
            dexPairFormat = "id"
        )

        assertThat(dto.id).isEqualTo("bitcoin")
        assertThat(dto.symbol).isEqualTo("btc")
        assertThat(dto.name).isEqualTo("Bitcoin")
        assertThat(dto.marketCapRank).isEqualTo(1)
        // basic check on nested market_data
        assertThat(dto.marketData?.currentPrice?.get("usd")).isWithin(0.001).of(50000.0)
        assertThat(dto.marketData?.marketCap?.get("usd")).isWithin(0.001).of(1_000_000_000_000.0)
    }

    // -------------------------------------------------------------------------
    // /coins/{id}/tickers
    // -------------------------------------------------------------------------

    @Test
    fun `getCoinTickersById uses GET and sends default query parameters`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.sampleCoinTickersResponse))

        api.getCoinTickersById(ids = "bitcoin")

        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/bitcoin/tickers")

        // Required default boolean
        assertThat(req.requestUrl?.queryParameter("include_exchange_logo")).isEqualTo("true")

        // Optional ones should be null
        assertThat(req.requestUrl?.queryParameter("exchange_ids")).isNull()
        assertThat(req.requestUrl?.queryParameter("depth")).isNull()
        assertThat(req.requestUrl?.queryParameter("dex_pair_format")).isNull()
        assertThat(req.requestUrl?.queryParameter("page")).isNull()
        assertThat(req.requestUrl?.queryParameter("order")).isNull()
    }

    // Need to Better Sample And Improve Test
    @Test
    fun `deserializes coin tickers json response`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.sampleCoinTickersResponse))

        val dto = api.getCoinTickersById(ids = "bitcoin")

        assertThat(dto.name).isEqualTo("Bitcoin")
        assertThat(dto.tickers).isNotNull()
        assertThat(dto.tickers).isNotEmpty()

        val first = requireNotNull(dto.tickers).first()!!
        assertThat(first.base).isEqualTo("BTC")
        assertThat(first.target).isEqualTo("USDT")
        assertThat(first.market?.name).isEqualTo("Binance")
        assertThat(first.market?.identifier).isEqualTo("binance")
        assertThat(first.trustScore).isEqualTo("green")
        assertThat(first.last).isWithin(0.001).of(50000.0)
    }

    // -------------------------------------------------------------------------
    // /coins/{id}/history
    // -------------------------------------------------------------------------

    @Test
    fun `coinHistoricalDataByID uses GET and sends default query parameters`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.sampleCoinHistoryResponse))

        api.coinHistoricalDataByID(
            id = "bitcoin",
            date = "01-01-2024"
        )

        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/bitcoin/history")
        assertThat(req.requestUrl?.queryParameter("date")).isEqualTo("01-01-2024")
        assertThat(req.requestUrl?.queryParameter("localization")).isEqualTo("true")
    }

    // Need to Better Sample And Improve Test
    @Test
    fun `deserializes coin history json response`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.sampleCoinHistoryResponse))
        val dto = api.coinHistoricalDataByID(
            id = "bitcoin",
            date = "01-01-2024",
            localization = false
        )
        assertThat(dto.id).isEqualTo("bitcoin")
        assertThat(dto.symbol).isEqualTo("btc")
        assertThat(dto.name).isEqualTo("Bitcoin")
    }

    // -------------------------------------------------------------------------
    // /coins/{id}/market_chart
    // -------------------------------------------------------------------------

    @Test
    fun `coinHistoricalChart uses GET and sends default query parameters`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.sampleMarketChartResponse))

        api.coinHistoricalChart(
            id = "bitcoin",
            vsCurrency = "usd",
            days = "7",
            interval = null,
            precision = null
        )

        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/bitcoin/market_chart")
        assertThat(req.requestUrl?.queryParameter("vs_currency")).isEqualTo("usd")
        assertThat(req.requestUrl?.queryParameter("days")).isEqualTo("7")
        assertThat(req.requestUrl?.queryParameter("interval")).isNull()
        assertThat(req.requestUrl?.queryParameter("precision")).isNull()
    }

    // Need to Better Sample And Improve Test
    @Test
    fun `deserializes coin market chart json response`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.sampleMarketChartResponse))

        val dto = api.coinHistoricalChart(
            id = "bitcoin",
            vsCurrency = "usd",
            days = "7"
        )

        assertThat(dto.prices).hasSize(2)
        assertThat(dto.marketCaps).hasSize(1)
        assertThat(dto.totalVolumes).hasSize(1)

       /* val firstPrice = dto.prices?.first()
        assertThat(firstPrice[0]).isWithin(0.0).of(1711929600000.0)
        assertThat(firstPrice[1]).isWithin(0.001).of(50000.0)*/
    }

    // -------------------------------------------------------------------------
    // /coins/{id}/market_chart/range
    // -------------------------------------------------------------------------

    @Test
    fun `coinHistoricalChartWithTimeRange uses GET and sends default query parameters`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.sampleMarketChartResponse))

        api.coinHistoricalChartWithTimeRange(
            id = "bitcoin",
            vsCurrency = "usd",
            from = 1711929600L,
            to = 1712016000L,
            precision = null
        )

        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/bitcoin/market_chart/range")
        assertThat(req.requestUrl?.queryParameter("vs_currency")).isEqualTo("usd")
        assertThat(req.requestUrl?.queryParameter("from")).isEqualTo("1711929600")
        assertThat(req.requestUrl?.queryParameter("to")).isEqualTo("1712016000")
        assertThat(req.requestUrl?.queryParameter("precision")).isNull()
    }

    // Need to Better Sample And Improve Test
    @Test
    fun `deserializes coin market chart range json response`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.sampleMarketChartResponse))

        val dto = api.coinHistoricalChartWithTimeRange(
            id = "bitcoin",
            vsCurrency = "usd",
            from = 1711929600L,
            to = 1712016000L
        )

        assertThat(dto.prices).hasSize(2)
        //val lastPrice = dto.prices.last()
       // assertThat(lastPrice[1]).isWithin(0.001).of(50500.0)
    }

    // -------------------------------------------------------------------------
    // /coins/{id}/ohlc
    // -------------------------------------------------------------------------

    @Test
    fun `coinOHLCChartCandle uses GET and sends default query parameters`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.sampleOhlcResponse))

        api.coinOHLCChartCandle(
            id = "bitcoin",
            vsCurrency = "usd",
            days = "30",
            precision = null
        )

        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/bitcoin/ohlc")
        assertThat(req.requestUrl?.queryParameter("vs_currency")).isEqualTo("usd")
        assertThat(req.requestUrl?.queryParameter("days")).isEqualTo("30")
        assertThat(req.requestUrl?.queryParameter("precision")).isNull()
    }

    // Need to Better Sample And Improve Test
    @Test
    fun `deserializes coin ohlc json response`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCoins.sampleOhlcResponse))

        val dto = api.coinOHLCChartCandle(
            id = "bitcoin",
            vsCurrency = "usd",
            days = "1"
        )

        // Assuming CoinOHLCChartCandleDto exposes a list of candles.
        assertThat(dto.candles).hasSize(2)
        val first = dto.candles.first()
        assertThat(first.timestampMillis).isEqualTo(1711929600000L)
        assertThat(first.open).isWithin(0.001).of(50000.0)
        assertThat(first.high).isWithin(0.001).of(51000.0)
        assertThat(first.low).isWithin(0.001).of(49500.0)
        assertThat(first.close).isWithin(0.001).of(50500.0)
    }
}
