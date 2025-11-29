// com/example/network/coins/CoinsApiTest.kt
package com.example.network.coins

import com.example.network.TestNetwork
import com.example.network.jsonOkResponse
import com.example.network.api.getCoinDetailKtor
import com.example.network.api.getCoinHistoricalChartKtor
import com.example.network.api.getCoinHistoricalChartWithTimeRangeKtor
import com.example.network.api.getCoinHistoricalDataByIdKtor
import com.example.network.api.getCoinMarketsKtor
import com.example.network.api.getCoinOHLCChartCandleKtor
import com.example.network.api.getCoinTickersByIdKtor
import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

/**
 * Tests Coin-related Ktor endpoints using MockEngine.
 *
 * Scenarios:
 * - /coins/markets: checks method, path, query params, and markets list parsing.
 * - /coins/{id}: checks default flags and coin detail deserialization.
 * - /coins/{id}/tickers: checks required/optional query params and tickers list.
 * - /coins/{id}/history: checks date/localization params and basic identity fields.
 * - /coins/{id}/market_chart(+range): checks required params and chart arrays.
 * - /coins/{id}/ohlc: checks OHLC query params and candle deserialization.
 */
class CoinsApiTest {

    private lateinit var client: HttpClient

    @After
    fun tearDown() {
        if (::client.isInitialized) {
            client.close()
        }
    }

    // -------------------------------------------------------------------------
    // /coins/markets
    // -------------------------------------------------------------------------

    @Test
    fun `getCoinMarkets uses GET and sends default query parameters`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var vsCurrency: String? = null
        var page: String? = null
        var perPage: String? = null
        var order: String? = null
        var sparkline: String? = null
        var priceChangePercentage: String? = null
        var locale: String? = null
        var precision: String? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            vsCurrency = request.url.parameters["vs_currency"]
            page = request.url.parameters["page"]
            perPage = request.url.parameters["per_page"]
            order = request.url.parameters["order"]
            sparkline = request.url.parameters["sparkline"]
            priceChangePercentage = request.url.parameters["price_change_percentage"]
            locale = request.url.parameters["locale"]
            precision = request.url.parameters["precision"]

            jsonOkResponse(SampleJsonCoins.marketsResponse)
        }

        client.getCoinMarketsKtor(
            vsCurrency = "usd",
        )

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/coins/markets")
        assertThat(vsCurrency).isEqualTo("usd")
        assertThat(page).isNull()
        assertThat(perPage).isNull()
        // no optional params
        assertThat(order).isNull()
        assertThat(sparkline).isNull()
        assertThat(priceChangePercentage).isNull()
        assertThat(locale).isNull()
        assertThat(precision).isNull()
    }

    @Test
    fun `deserializes coins markets json response`() = runTest {
        client = TestNetwork.ktorTestClient { _ ->
            jsonOkResponse(SampleJsonCoins.marketsResponse)
        }

        val dto = client.getCoinMarketsKtor(
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
        assertThat(first.currentPrice).isWithin(0.001).of(65_234.12)
        assertThat(first.sparklineIn7d?.price).containsAtLeast(65_000.0, 65_234.12)
        assertThat(first.lastUpdated).isEqualTo("2025-09-01T00:00:00Z")
    }

    // -------------------------------------------------------------------------
    // /coins/{id}
    // -------------------------------------------------------------------------

    @Test
    fun `getCoinDetail uses GET and sends default query parameters`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var localization: String? = null
        var tickers: String? = null
        var marketData: String? = null
        var communityData: String? = null
        var developerData: String? = null
        var sparkline: String? = null
        var dexPairFormat: String? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            localization = request.url.parameters["localization"]
            tickers = request.url.parameters["tickers"]
            marketData = request.url.parameters["market_data"]
            communityData = request.url.parameters["community_data"]
            developerData = request.url.parameters["developer_data"]
            sparkline = request.url.parameters["sparkline"]
            dexPairFormat = request.url.parameters["dex_pair_format"]

            jsonOkResponse(SampleJsonCoins.sampleCoinDetailResponse)
        }

        client.getCoinDetailKtor(id = "bitcoin")

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/coins/bitcoin")

        // Boolean flags default
        assertThat(localization).isEqualTo("true")
        assertThat(tickers).isEqualTo("true")
        assertThat(marketData).isEqualTo("true")
        assertThat(communityData).isEqualTo("true")
        assertThat(developerData).isEqualTo("true")
        assertThat(sparkline).isEqualTo("false")

        // dex_pair_format default
        assertThat(dexPairFormat).isEqualTo("contract_address")
    }

    @Test
    fun `deserializes coin detail json response`() = runTest {
        client = TestNetwork.ktorTestClient { _ ->
            jsonOkResponse(SampleJsonCoins.sampleCoinDetailResponse)
        }

        val dto = client.getCoinDetailKtor(
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
        assertThat(dto.marketData?.currentPrice?.get("usd"))
            .isWithin(0.001).of(50_000.0)
        assertThat(dto.marketData?.marketCap?.get("usd"))
            .isWithin(0.001).of(1_000_000_000_000.0)
    }

    // -------------------------------------------------------------------------
    // /coins/{id}/tickers
    // -------------------------------------------------------------------------

    @Test
    fun `getCoinTickersById uses GET and sends default query parameters`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var includeExchangeLogo: String? = null
        var exchangeIds: String? = null
        var depth: String? = null
        var dexPairFormat: String? = null
        var page: String? = null
        var order: String? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            includeExchangeLogo = request.url.parameters["include_exchange_logo"]
            exchangeIds = request.url.parameters["exchange_ids"]
            depth = request.url.parameters["depth"]
            dexPairFormat = request.url.parameters["dex_pair_format"]
            page = request.url.parameters["page"]
            order = request.url.parameters["order"]

            jsonOkResponse(SampleJsonCoins.sampleCoinTickersResponse)
        }

        client.getCoinTickersByIdKtor(id = "bitcoin")

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/coins/bitcoin/tickers")

        // Required default boolean
        assertThat(includeExchangeLogo).isEqualTo("true")

        // Optional ones should be null
        assertThat(exchangeIds).isNull()
        assertThat(depth).isNull()
        assertThat(dexPairFormat).isNull()
        assertThat(page).isNull()
        assertThat(order).isNull()
    }

    @Test
    fun `deserializes coin tickers json response`() = runTest {
        client = TestNetwork.ktorTestClient { _ ->
            jsonOkResponse(SampleJsonCoins.sampleCoinTickersResponse)
        }

        val dto = client.getCoinTickersByIdKtor(id = "bitcoin")

        assertThat(dto.name).isEqualTo("Bitcoin")
        assertThat(dto.tickers).isNotNull()
        assertThat(dto.tickers).isNotEmpty()

        val first = requireNotNull(dto.tickers).first()!!
        assertThat(first.base).isEqualTo("BTC")
        assertThat(first.target).isEqualTo("USDT")
        assertThat(first.market?.name).isEqualTo("Binance")
        assertThat(first.market?.identifier).isEqualTo("binance")
        assertThat(first.trustScore).isEqualTo("green")
        assertThat(first.last).isWithin(0.001).of(50_000.0)
    }

    // -------------------------------------------------------------------------
    // /coins/{id}/history
    // -------------------------------------------------------------------------

    @Test
    fun `coinHistoricalDataByID uses GET and sends default query parameters`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var date: String? = null
        var localization: String? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            date = request.url.parameters["date"]
            localization = request.url.parameters["localization"]

            jsonOkResponse(SampleJsonCoins.sampleCoinHistoryResponse)
        }

        client.getCoinHistoricalDataByIdKtor(
            id = "bitcoin",
            date = "01-01-2024"
        )

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/coins/bitcoin/history")
        assertThat(date).isEqualTo("01-01-2024")
        assertThat(localization).isEqualTo("true")
    }

    @Test
    fun `deserializes coin history json response`() = runTest {
        client = TestNetwork.ktorTestClient { _ ->
            jsonOkResponse(SampleJsonCoins.sampleCoinHistoryResponse)
        }

        val dto = client.getCoinHistoricalDataByIdKtor(
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
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var vsCurrency: String? = null
        var days: String? = null
        var interval: String? = null
        var precision: String? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            vsCurrency = request.url.parameters["vs_currency"]
            days = request.url.parameters["days"]
            interval = request.url.parameters["interval"]
            precision = request.url.parameters["precision"]

            jsonOkResponse(SampleJsonCoins.sampleMarketChartResponse)
        }

        client.getCoinHistoricalChartKtor(
            id = "bitcoin",
            vsCurrency = "usd",
            days = "7",
            interval = null,
            precision = null
        )

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/coins/bitcoin/market_chart")
        assertThat(vsCurrency).isEqualTo("usd")
        assertThat(days).isEqualTo("7")
        assertThat(interval).isNull()
        assertThat(precision).isNull()
    }

    @Test
    fun `deserializes coin market chart json response`() = runTest {
        client = TestNetwork.ktorTestClient { _ ->
            jsonOkResponse(SampleJsonCoins.sampleMarketChartResponse)
        }

        val dto = client.getCoinHistoricalChartKtor(
            id = "bitcoin",
            vsCurrency = "usd",
            days = "7"
        )

        assertThat(dto.prices).hasSize(2)
        assertThat(dto.marketCaps).hasSize(1)
        assertThat(dto.totalVolumes).hasSize(1)
    }

    // -------------------------------------------------------------------------
    // /coins/{id}/market_chart/range
    // -------------------------------------------------------------------------

    @Test
    fun `coinHistoricalChartWithTimeRange uses GET and sends default query parameters`() =
        runTest {
            var capturedMethod: HttpMethod? = null
            var capturedPath: String? = null
            var vsCurrency: String? = null
            var from: String? = null
            var to: String? = null
            var precision: String? = null

            client = TestNetwork.ktorTestClient { request ->
                capturedMethod = request.method
                capturedPath = request.url.encodedPath
                vsCurrency = request.url.parameters["vs_currency"]
                from = request.url.parameters["from"]
                to = request.url.parameters["to"]
                precision = request.url.parameters["precision"]

                jsonOkResponse(SampleJsonCoins.sampleMarketChartResponse)
            }

            client.getCoinHistoricalChartWithTimeRangeKtor(
                id = "bitcoin",
                vsCurrency = "usd",
                from = 1_711_929_600L,
                to = 1_712_016_000L,
                precision = null
            )

            assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
            assertThat(capturedPath)
                .isEqualTo("/coins/bitcoin/market_chart/range")
            assertThat(vsCurrency).isEqualTo("usd")
            assertThat(from).isEqualTo("1711929600")
            assertThat(to).isEqualTo("1712016000")
            assertThat(precision).isNull()
        }

    @Test
    fun `deserializes coin market chart range json response`() = runTest {
        client = TestNetwork.ktorTestClient { _ ->
            jsonOkResponse(SampleJsonCoins.sampleMarketChartResponse)
        }

        val dto = client.getCoinHistoricalChartWithTimeRangeKtor(
            id = "bitcoin",
            vsCurrency = "usd",
            from = 1_711_929_600L,
            to = 1_712_016_000L
        )

        assertThat(dto.prices).hasSize(2)
    }

    // -------------------------------------------------------------------------
    // /coins/{id}/ohlc
    // -------------------------------------------------------------------------

    @Test
    fun `coinOHLCChartCandle uses GET and sends default query parameters`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var vsCurrency: String? = null
        var days: String? = null
        var precision: String? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            vsCurrency = request.url.parameters["vs_currency"]
            days = request.url.parameters["days"]
            precision = request.url.parameters["precision"]

            jsonOkResponse(SampleJsonCoins.sampleOhlcResponse)
        }

        client.getCoinOHLCChartCandleKtor(
            id = "bitcoin",
            vsCurrency = "usd",
            days = "30",
            precision = null
        )

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/coins/bitcoin/ohlc")
        assertThat(vsCurrency).isEqualTo("usd")
        assertThat(days).isEqualTo("30")
        assertThat(precision).isNull()
    }

    @Test
    fun `deserializes coin ohlc json response`() = runTest {
        client = TestNetwork.ktorTestClient { _ ->
            jsonOkResponse(SampleJsonCoins.sampleOhlcResponse)
        }

        val dto = client.getCoinOHLCChartCandleKtor(
            id = "bitcoin",
            vsCurrency = "usd",
            days = "1"
        )

        assertThat(dto.candles).hasSize(2)
        val first = dto.candles.first()
        assertThat(first.timestampMillis).isEqualTo(1_711_929_600_000L)
        assertThat(first.open).isWithin(0.001).of(50_000.0)
        assertThat(first.high).isWithin(0.001).of(51_000.0)
        assertThat(first.low).isWithin(0.001).of(49_500.0)
        assertThat(first.close).isWithin(0.001).of(50_500.0)
    }
}
