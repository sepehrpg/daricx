package com.example.network.global

import com.example.network.TestNetwork
import com.example.network.api.Global
import com.example.network.model.GlobalCryptoMarketDataDto
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.create

/**
 * GlobalApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for /global endpoint.
 * - Verify basic deserialization for GlobalCryptoMarketDataDto.
 */
class GlobalApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: Global

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
    // GET /global
    // -------------------------------------------------------------------------

    @Test
    fun `getGlobal uses GET and has no query parameters`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonGlobal.globalResponse))

        // Act
        api.getGlobal()

        // Assert request
        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/global")
        // No query params expected
        assertThat(req.requestUrl?.queryParameterNames).isEmpty()
    }

    @Test
    fun `deserializes global crypto market json response`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonGlobal.globalResponse))

        // Act
        val dto: GlobalCryptoMarketDataDto = api.getGlobal()

        val data = dto.data
        assertThat(data).isNotNull()

        // Basic scalar checks
        assertThat(data!!.activeCryptocurrencies).isEqualTo(12045)
        assertThat(data.ongoingIcos).isEqualTo(12)
        assertThat(data.endedIcos).isEqualTo(3400)
        assertThat(data.markets).isEqualTo(830)

        // total_market_cap map
        assertThat(data.totalMarketCap?.get("usd"))
            .isWithin(0.000001)
            .of(2_350_000_000_000.0)
        assertThat(data.totalMarketCap?.get("btc"))
            .isWithin(0.000001)
            .of(38_000_000.123)
        assertThat(data.totalMarketCap?.get("eth"))
            .isWithin(0.000001)
            .of(1_250_000_000.456)

        // total_volume
        assertThat(data.totalVolume?.get("usd"))
            .isWithin(0.000001)
            .of(120_000_000_000.0)

        // market_cap_percentage
        assertThat(data.marketCapPercentage?.get("btc"))
            .isWithin(0.000001)
            .of(52.1)
        assertThat(data.marketCapPercentage?.get("eth"))
            .isWithin(0.000001)
            .of(17.3)

        // change + updated_at
        assertThat(data.marketCapChangePercentage24hUsd)
            .isWithin(0.000001)
            .of(-0.56)
        assertThat(data.updatedAt).isEqualTo(1725148800L)
    }
}
