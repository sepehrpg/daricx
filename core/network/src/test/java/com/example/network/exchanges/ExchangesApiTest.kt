package com.example.network.exchanges

import com.example.network.TestNetwork
import com.example.network.api.Exchanges
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.create

/**
 * ExchangesApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for Exchanges endpoints.
 * - Ensure default and optional query parameters are sent correctly.
 * - Verify basic deserialization for DTOs.
 */
class ExchangesApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: Exchanges

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork
            .retrofit(server.url("/").toString())
            .create()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    // -------------------------------------------------------------------------
    // GET /exchanges
    // -------------------------------------------------------------------------

    @Test
    fun `getExchanges uses GET and sends default paging params`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonExchanges.exchangesResponse))

        // Act
        api.getExchanges() // using defaults: per_page=100, page=1

        // Assert request
        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/exchanges")
        assertThat(req.requestUrl?.queryParameter("per_page")).isEqualTo("100")
        assertThat(req.requestUrl?.queryParameter("page")).isEqualTo("1")
    }

    @Test
    fun `deserializes exchanges list json response`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonExchanges.exchangesResponse))

        // Act
        val dto = api.getExchanges(perPage = 50, page = 2)

        // Assert
        assertThat(dto).hasSize(1)
        val first = dto.first()

        // NOTE: adjust field names to your ExchangesListDto item
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
            .of(123456.789)
    }

    // -------------------------------------------------------------------------
    // GET /exchanges/{id}
    // -------------------------------------------------------------------------

    @Test
    fun `getExchangeById uses GET and sends path and default query params`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonExchanges.exchangeDetailResponse))

        // Act
        api.getExchangeById(id = "binance")

        // Assert request
        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/exchanges/binance")
        // dex_pair_format default is null → no query
        assertThat(req.requestUrl?.queryParameter("dex_pair_format")).isNull()
    }

    @Test
    fun `deserializes exchange detail json response`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonExchanges.exchangeDetailResponse))

        // Act
        val dto = api.getExchangeById(id = "binance")

        // Assert (adjust field names to your ExchangeDetailDto)
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
            .of(123456.789)
    }

    // -------------------------------------------------------------------------
    // GET /exchanges/{id}/tickers
    // -------------------------------------------------------------------------

    @Test
    fun `getExchangeTickersById uses GET and sends default query parameters`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonExchanges.exchangeTickersResponse))

        // Act
        api.getExchangeTickersById(id = "binance")

        // Assert request
        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath)
            .isEqualTo("/exchanges/binance/tickers")

        // Default & optional query params
        assertThat(req.requestUrl?.queryParameter("coin_ids")).isNull()
        assertThat(req.requestUrl?.queryParameter("include_exchange_logo"))
            .isEqualTo("true")
        assertThat(req.requestUrl?.queryParameter("depth")).isNull()
        assertThat(req.requestUrl?.queryParameter("dex_pair_format")).isNull()
        assertThat(req.requestUrl?.queryParameter("page")).isNull()
        assertThat(req.requestUrl?.queryParameter("order")).isNull()
    }

    @Test
    fun `deserializes exchange tickers json response`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonExchanges.exchangeTickersResponse))

        // Act
        val dto = api.getExchangeTickersById(id = "binance")

        // Assert (similar to CoinTickers)
        assertThat(dto.name).isEqualTo("Binance")
        assertThat(dto.tickers).isNotNull()
        assertThat(dto.tickers).isNotEmpty()

        val first = requireNotNull(dto.tickers).first()
        assertThat(first?.base).isEqualTo("BTC")
        assertThat(first?.target).isEqualTo("USDT")
        assertThat(first?.market?.name).isEqualTo("Binance")
        assertThat(first?.market?.identifier).isEqualTo("binance")
        assertThat(first?.trustScore).isEqualTo("green")
        assertThat(first?.last).isWithin(0.001).of(50000.0)
        assertThat(first?.volume).isWithin(0.001).of(100000.0)
    }

    // -------------------------------------------------------------------------
    // GET /exchanges/{id}/volume_chart
    // -------------------------------------------------------------------------

    @Test
    fun `exchangeVolumeChart uses GET and sends required days param`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonExchanges.exchangeVolumeChartResponse))

        // Act
        api.exchangeVolumeChart(
            id = "binance",
            days = "7"
        )

        // Assert request
        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath)
            .isEqualTo("/exchanges/binance/volume_chart")
        assertThat(req.requestUrl?.queryParameter("days")).isEqualTo("7")
    }

    @Test
    fun `deserializes exchange volume chart json response`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonExchanges.exchangeVolumeChartResponse))

        // Act
        val dto = api.exchangeVolumeChart(
            id = "binance",
            days = "7"
        )

        // NOTE:
        // This depends on how ExchangeVolumeChartDto is defined.
        // Example (adjust to your model):
        //
        //   assertThat(dto.points).hasSize(2)
        //   val first = dto.points.first()
        //   assertThat(first.timestampMillis).isEqualTo(1711929600000L)
        //   assertThat(first.volume).isWithin(0.000001).of(123456.789)
    }
}
