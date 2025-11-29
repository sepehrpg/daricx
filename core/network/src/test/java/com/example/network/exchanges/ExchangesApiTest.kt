// com/example/network/exchanges/ExchangesApiTest.kt
package com.example.network.exchanges

import com.example.network.TestNetwork
import com.example.network.jsonOkResponse
import com.example.network.api.getExchangeByIdKtor
import com.example.network.api.getExchangeTickersByIdKtor
import com.example.network.api.getExchangeVolumeChartKtor
import com.example.network.api.getExchangesKtor
import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

/**
 * Tests Exchange-related Ktor endpoints using MockEngine.
 *
 * Scenarios:
 * - /exchanges: checks default paging query params and exchanges list parsing.
 * - /exchanges/{id}: checks path and exchange detail deserialization.
 * - /exchanges/{id}/tickers: checks default/optional query params and tickers list.
 * - /exchanges/{id}/volume_chart: checks required `days` param and basic response handling.
 */
class ExchangesApiTest {

    private lateinit var client: HttpClient

    @After
    fun tearDown() {
        if (::client.isInitialized) client.close()
    }

    // -------------------------------------------------------------------------
    // GET /exchanges
    // -------------------------------------------------------------------------

    @Test
    fun `getExchanges uses GET and sends default paging params`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var perPage: String? = null
        var page: String? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            perPage = request.url.parameters["per_page"]
            page = request.url.parameters["page"]

            jsonOkResponse(SampleJsonExchanges.exchangesResponse)
        }

        client.getExchangesKtor() // defaults: per_page=100, page=1

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/exchanges")
        assertThat(perPage).isEqualTo("100")
        assertThat(page).isEqualTo("1")
    }

    @Test
    fun `deserializes exchanges list json response`() = runTest {
        client = TestNetwork.ktorTestClient {
            jsonOkResponse(SampleJsonExchanges.exchangesResponse)
        }

        val dto = client.getExchangesKtor(perPage = 50, page = 2)

        assertThat(dto).hasSize(1)
        val first = dto.first()

        assertThat(first.id).isEqualTo("binance")
        assertThat(first.name).isEqualTo("Binance")
        assertThat(first.yearEstablished).isEqualTo(2017)
        assertThat(first.country).isEqualTo("Cayman Islands")
        assertThat(first.url).isEqualTo("https://www.binance.com/")
        assertThat(first.image)
            .isEqualTo("https://assets.coingecko.com/markets/images/52/small/binance.jpg")
        assertThat(first.hasTradingIncentive).isFalse()
        assertThat(first.trustScore).isEqualTo(10)
        assertThat(first.trustScoreRank).isEqualTo(1)
        assertThat(first.tradeVolume24hBtc)
            .isWithin(0.000001)
            .of(123_456.789)
    }

    // -------------------------------------------------------------------------
    // GET /exchanges/{id}
    // -------------------------------------------------------------------------

    @Test
    fun `getExchangeById uses GET and sends path and default query params`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var dexPairFormat: String? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            dexPairFormat = request.url.parameters["dex_pair_format"]

            jsonOkResponse(SampleJsonExchanges.exchangeDetailResponse)
        }

        client.getExchangeByIdKtor(id = "binance")

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/exchanges/binance")
        assertThat(dexPairFormat).isNull()
    }

    @Test
    fun `deserializes exchange detail json response`() = runTest {
        client = TestNetwork.ktorTestClient {
            jsonOkResponse(SampleJsonExchanges.exchangeDetailResponse)
        }

        val dto = client.getExchangeByIdKtor(id = "binance")

        assertThat(dto.name).isEqualTo("Binance")
        assertThat(dto.yearEstablished).isEqualTo(2017)
        assertThat(dto.country).isEqualTo("Cayman Islands")
        assertThat(dto.url).isEqualTo("https://www.binance.com/")
        assertThat(dto.image)
            .isEqualTo("https://assets.coingecko.com/markets/images/52/small/binance.jpg")
        assertThat(dto.trustScore).isEqualTo(10)
        assertThat(dto.trustScoreRank).isEqualTo(1)
        assertThat(dto.tradeVolume24hBtc)
            .isWithin(0.000001)
            .of(123_456.789)
    }

    // -------------------------------------------------------------------------
    // GET /exchanges/{id}/tickers
    // -------------------------------------------------------------------------

    @Test
    fun `getExchangeTickersById uses GET and sends default query parameters`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var coinIds: String? = null
        var includeExchangeLogo: String? = null
        var depth: String? = null
        var dexPairFormat: String? = null
        var page: String? = null
        var order: String? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            coinIds = request.url.parameters["coin_ids"]
            includeExchangeLogo = request.url.parameters["include_exchange_logo"]
            depth = request.url.parameters["depth"]
            dexPairFormat = request.url.parameters["dex_pair_format"]
            page = request.url.parameters["page"]
            order = request.url.parameters["order"]

            jsonOkResponse(SampleJsonExchanges.exchangeTickersResponse)
        }

        client.getExchangeTickersByIdKtor(id = "binance")

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/exchanges/binance/tickers")

        assertThat(coinIds).isNull()
        assertThat(includeExchangeLogo).isEqualTo("true")
        assertThat(depth).isNull()
        assertThat(dexPairFormat).isNull()
        assertThat(page).isNull()
        assertThat(order).isNull()
    }

    @Test
    fun `deserializes exchange tickers json response`() = runTest {
        client = TestNetwork.ktorTestClient {
            jsonOkResponse(SampleJsonExchanges.exchangeTickersResponse)
        }

        val dto = client.getExchangeTickersByIdKtor(id = "binance")

        assertThat(dto.name).isEqualTo("Binance")
        assertThat(dto.tickers).isNotNull()
        assertThat(dto.tickers).isNotEmpty()

        val first = requireNotNull(dto.tickers).first()
        assertThat(first?.base).isEqualTo("BTC")
        assertThat(first?.target).isEqualTo("USDT")
        assertThat(first?.market?.name).isEqualTo("Binance")
        assertThat(first?.market?.identifier).isEqualTo("binance")
        assertThat(first?.trustScore).isEqualTo("green")
        assertThat(first?.last).isWithin(0.001).of(50_000.0)
        assertThat(first?.volume).isWithin(0.001).of(100_000.0)
    }

    // -------------------------------------------------------------------------
    // GET /exchanges/{id}/volume_chart
    // -------------------------------------------------------------------------

    @Test
    fun `exchangeVolumeChart uses GET and sends required days param`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var days: String? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            days = request.url.parameters["days"]

            jsonOkResponse(SampleJsonExchanges.exchangeVolumeChartResponse)
        }

        client.getExchangeVolumeChartKtor(
            id = "binance",
            days = "7"
        )

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/exchanges/binance/volume_chart")
        assertThat(days).isEqualTo("7")
    }

    @Test
    fun `deserializes exchange volume chart json response`() = runTest {
        client = TestNetwork.ktorTestClient {
            jsonOkResponse(SampleJsonExchanges.exchangeVolumeChartResponse)
        }

        val dto = client.getExchangeVolumeChartKtor(
            id = "binance",
            days = "7"
        )

        // TODO: assert on dto depending on your ExchangeVolumeChartDto structure
        // e.g.:
        // assertThat(dto.points).hasSize(2)
        // val first = dto.points.first()
        // assertThat(first.timestampMillis).isEqualTo(1711929600000L)
        // assertThat(first.volume).isWithin(0.000001).of(123456.789)
    }
}
