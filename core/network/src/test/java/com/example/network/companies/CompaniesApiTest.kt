package com.example.network.companies

import com.example.network.TestNetwork
import com.example.network.api.Companies
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.create

/**
 * CompaniesApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for Companies endpoints.
 * - Ensure path parameter {coin_id} is bound correctly.
 * - Verify basic deserialization for CompaniesTreasuryDto.
 */
class CompaniesApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: Companies

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
    // GET /companies/public_treasury/{coin_id}
    // -------------------------------------------------------------------------

    @Test
    fun `getCompaniesTreasury uses GET and builds correct path for bitcoin`() = runTest {
        // Arrange
        server.enqueue(
            MockResponse().setBody(SampleJsonCompanies.btcTreasuryResponse)
        )

        // Act
        api.getCompaniesTreasury(coinId = "bitcoin")

        // Assert request
        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath)
            .isEqualTo("/companies/public_treasury/bitcoin")

        // No query params expected
        assertThat(req.requestUrl?.querySize).isEqualTo(0)
    }

    @Test
    fun `deserializes companies treasury json response for bitcoin`() = runTest {
        // Arrange
        server.enqueue(
            MockResponse().setBody(SampleJsonCompanies.btcTreasuryResponse)
        )

        // Act
        val dto = api.getCompaniesTreasury(coinId = "bitcoin")

        // Assert top-level fields
        assertThat(dto.totalHoldings).isWithin(0.001).of(264_136.0)
        assertThat(dto.totalValueUsd).isWithin(0.001).of(18_403_306_939.1513)
        assertThat(dto.marketCapDominance).isWithin(0.001).of(1.34)

        // Assert companies list
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
        // Arrange
        server.enqueue(
            MockResponse().setBody(SampleJsonCompanies.ethTreasuryResponse)
        )

        // Act
        val dto = api.getCompaniesTreasury(coinId = "ethereum")

        // Assert top-level fields
        assertThat(dto.totalHoldings).isWithin(0.001).of(915_000.5)
        assertThat(dto.totalValueUsd).isWithin(0.001).of(2_780_033_069.12)
        assertThat(dto.marketCapDominance).isWithin(0.001).of(0.48)

        // Assert companies list
        assertThat(dto.companies).isNotNull()
        assertThat(dto.companies).isNotEmpty()
        assertThat(dto.companies).hasSize(1)

        val first = dto.companies?.first()
        // Unknown fields ("unknown_field", "unknown_top") should be ignored
        assertThat(first?.name).isEqualTo("Some Corp")
        assertThat(first?.symbol).isEqualTo("NYSE:SOME")
        assertThat(first?.country).isEqualTo("US")
        assertThat(first?.totalHoldings).isWithin(0.001).of(250_000.0)
        assertThat(first?.totalEntryValueUsd).isWithin(0.001).of(350_000_000.0)
        assertThat(first?.totalCurrentValueUsd).isWithin(0.001).of(420_000_000.0)
        assertThat(first?.percentageOfTotalSupply).isWithin(0.00001).of(0.021)
    }
}
