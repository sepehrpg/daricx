// com/example/network/companies/CompaniesApiTest.kt
package com.example.network.companies

import com.example.network.TestNetwork
import com.example.network.jsonOkResponse
import com.example.network.api.getCompaniesTreasuryKtor
import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

/**
 * Tests the Companies Treasury Ktor endpoint using MockEngine.
 *
 * Scenarios:
 * - Request: verifies GET method and `/companies/public_treasury/{coin_id}` path with no query params.
 * - Response (BTC): checks aggregate treasury fields and first company entry.
 * - Response (ETH): same checks for an Ethereum sample payload.
 */
class CompaniesApiTest {

    private lateinit var client: HttpClient

    @After
    fun tearDown() {
        if (::client.isInitialized) client.close()
    }

    // -------------------------------------------------------------------------
    // GET /companies/public_treasury/{coin_id}
    // -------------------------------------------------------------------------

    @Test
    fun `getCompaniesTreasury uses GET and builds correct path for bitcoin`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var querySize: Int? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            querySize = request.url.parameters.names().size

            jsonOkResponse(SampleJsonCompanies.btcTreasuryResponse)
        }

        client.getCompaniesTreasuryKtor(coinId = "bitcoin")

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/companies/public_treasury/bitcoin")
        assertThat(querySize).isEqualTo(0)
    }

    @Test
    fun `deserializes companies treasury json response for bitcoin`() = runTest {
        client = TestNetwork.ktorTestClient {
            jsonOkResponse(SampleJsonCompanies.btcTreasuryResponse)
        }

        val dto = client.getCompaniesTreasuryKtor(coinId = "bitcoin")

        assertThat(dto.totalHoldings).isWithin(0.001).of(264_136.0)
        assertThat(dto.totalValueUsd).isWithin(0.001).of(18_403_306_939.1513)
        assertThat(dto.marketCapDominance).isWithin(0.001).of(1.34)

        assertThat(dto.companies).isNotNull()
        assertThat(dto.companies).isNotEmpty()
        assertThat(dto.companies).hasSize(1)

        val first = dto.companies?.first()
        assertThat(first?.name).isEqualTo("MicroStrategy Inc.")
        assertThat(first?.symbol).isEqualTo("NASDAQ:MSTR")
        assertThat(first?.country).isEqualTo("US")
        assertThat(first?.totalHoldings).isWithin(0.001).of(226_164.0)
        assertThat(first?.totalEntryValueUsd).isWithin(0.001).of(8_238_000_000.0)
        assertThat(first?.totalCurrentValueUsd).isWithin(0.001).of(14_678_000_000.0)
        assertThat(first?.percentageOfTotalSupply).isWithin(0.00001).of(1.075)
    }

    @Test
    fun `deserializes companies treasury json response for ethereum`() = runTest {
        client = TestNetwork.ktorTestClient {
            jsonOkResponse(SampleJsonCompanies.ethTreasuryResponse)
        }

        val dto = client.getCompaniesTreasuryKtor(coinId = "ethereum")

        assertThat(dto.totalHoldings).isWithin(0.001).of(915_000.5)
        assertThat(dto.totalValueUsd).isWithin(0.001).of(2_780_033_069.12)
        assertThat(dto.marketCapDominance).isWithin(0.001).of(0.48)

        assertThat(dto.companies).isNotNull()
        assertThat(dto.companies).isNotEmpty()
        assertThat(dto.companies).hasSize(1)

        val first = dto.companies?.first()
        assertThat(first?.name).isEqualTo("Some Corp")
        assertThat(first?.symbol).isEqualTo("NYSE:SOME")
        assertThat(first?.country).isEqualTo("US")
        assertThat(first?.totalHoldings).isWithin(0.001).of(250_000.0)
        assertThat(first?.totalEntryValueUsd).isWithin(0.001).of(350_000_000.0)
        assertThat(first?.totalCurrentValueUsd).isWithin(0.001).of(420_000_000.0)
        assertThat(first?.percentageOfTotalSupply).isWithin(0.00001).of(0.021)
    }
}
