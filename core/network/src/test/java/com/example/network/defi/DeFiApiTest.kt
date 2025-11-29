// com/example/network/defi/DeFiApiTest.kt
package com.example.network.defi

import com.example.network.TestNetwork
import com.example.network.jsonOkResponse
import com.example.network.api.getGlobalDeFiKtor
import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test



/**
 * Tests the Global DeFi Ktor endpoint using MockEngine.
 *
 * Scenarios:
 * - Request: verifies GET method, `/global/decentralized_finance_defi` path, and no query params.
 * - Response: verifies that `data` is present and key numeric/string fields are parsed correctly.
 */
class DeFiApiTest {

    private lateinit var client: HttpClient

    @After
    fun tearDown() {
        if (::client.isInitialized) client.close()
    }

    // -------------------------------------------------------------------------
    // GET /global/decentralized_finance_defi
    // -------------------------------------------------------------------------

    @Test
    fun `getGlobalDeFi uses GET and correct path`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var querySize: Int? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            querySize = request.url.parameters.names().size

            jsonOkResponse(SampleJsonDeFi.defiResponse)
        }

        client.getGlobalDeFiKtor()

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/global/decentralized_finance_defi")
        assertThat(querySize).isEqualTo(0)
    }

    @Test
    fun `deserializes global defi json response`() = runTest {
        client = TestNetwork.ktorTestClient {
            jsonOkResponse(SampleJsonDeFi.defiResponse)
        }

        val dto = client.getGlobalDeFiKtor()
        val data = requireNotNull(dto.data)

        assertThat(data.defiMarketCap)
            .isEqualTo("105273842288.229620442228701667")
        assertThat(data.ethMarketCap)
            .isEqualTo("406184911478.5772415794509920285")
        assertThat(data.defiToEthRatio)
            .isEqualTo("25.91771366026773")
        assertThat(data.tradingVolume24h)
            .isEqualTo("5046503746.288261")
        assertThat(data.defiDominance)
            .isEqualTo("3.86765030846147")

        assertThat(data.topCoinName).isEqualTo("Lido Staked Ether")
        assertThat(data.topCoinDefiDominance)
            .isWithin(0.000001)
            .of(30.589442518868)
    }
}
