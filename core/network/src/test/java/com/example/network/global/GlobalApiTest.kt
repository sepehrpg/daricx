// com/example/network/global/GlobalApiTest.kt
package com.example.network.global

import com.example.network.TestNetwork
import com.example.network.jsonOkResponse
import com.example.network.api.getGlobalKtor
import com.example.network.model.GlobalCryptoMarketDataDto
import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

/**
 * Tests the Global Crypto Market Ktor endpoint using MockEngine.
 *
 * Scenarios:
 * - Request: verifies GET method, `/global` path, and absence of query params.
 * - Response: checks main counters, market cap/volume maps, dominance, and update timestamp.
 */
class GlobalApiTest {

    private lateinit var client: HttpClient

    @After
    fun tearDown() {
        if (::client.isInitialized) client.close()
    }

    // -------------------------------------------------------------------------
    // GET /global
    // -------------------------------------------------------------------------

    @Test
    fun `getGlobal uses GET and has no query parameters`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var queryNames: Set<String>? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            queryNames = request.url.parameters.names()

            jsonOkResponse(SampleJsonGlobal.globalResponse)
        }

        client.getGlobalKtor()

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/global")
        assertThat(queryNames).isEmpty()
    }

    @Test
    fun `deserializes global crypto market json response`() = runTest {
        client = TestNetwork.ktorTestClient {
            jsonOkResponse(SampleJsonGlobal.globalResponse)
        }

        val dto: GlobalCryptoMarketDataDto = client.getGlobalKtor()
        val data = dto.data
        assertThat(data).isNotNull()

        assertThat(data!!.activeCryptocurrencies).isEqualTo(12_045)
        assertThat(data.ongoingIcos).isEqualTo(12)
        assertThat(data.endedIcos).isEqualTo(3_400)
        assertThat(data.markets).isEqualTo(830)

        assertThat(data.totalMarketCap?.get("usd"))
            .isWithin(0.000001)
            .of(2_350_000_000_000.0)
        assertThat(data.totalMarketCap?.get("btc"))
            .isWithin(0.000001)
            .of(38_000_000.123)
        assertThat(data.totalMarketCap?.get("eth"))
            .isWithin(0.000001)
            .of(1_250_000_000.456)

        assertThat(data.totalVolume?.get("usd"))
            .isWithin(0.000001)
            .of(120_000_000_000.0)

        assertThat(data.marketCapPercentage?.get("btc"))
            .isWithin(0.000001)
            .of(52.1)
        assertThat(data.marketCapPercentage?.get("eth"))
            .isWithin(0.000001)
            .of(17.3)

        assertThat(data.marketCapChangePercentage24hUsd)
            .isWithin(0.000001)
            .of(-0.56)
        assertThat(data.updatedAt).isEqualTo(1_725_148_800L)
    }
}
