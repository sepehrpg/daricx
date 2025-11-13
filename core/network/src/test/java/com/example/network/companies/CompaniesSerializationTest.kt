package com.example.network.companies


import com.example.network.model.CompaniesTreasuryDto
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

/**
 * CompaniesSerializationTest
 *
 * Test Goal:
 * - Ensure CompaniesTreasuryDto can be deserialized from JSON, even with unknown fields.
 *
 * Scenarios:
 * 1) Deserialize sample BTC treasury JSON and validate top-level fields.
 * 2) Nested company objects deserialize correctly.
 * 3) Unknown fields in JSON are ignored without error.
 */
class CompaniesSerializationTest {

    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    @Test fun `deserializes btc treasury with nested companies`() {
        val dto: CompaniesTreasuryDto = json.decodeFromString(SampleJsonCompanies.btcTreasuryResponse)

        assertThat(dto.totalHoldings).isWithin(0.0001).of(264136.0)
        assertThat(dto.totalValueUsd).isWithin(0.0001).of(18403306939.1513)
        assertThat(dto.marketCapDominance).isWithin(0.0001).of(1.34)
        assertThat(dto.companies).isNotNull()
        val c = dto.companies!!.first()
        assertThat(c.name).isEqualTo("MicroStrategy Inc.")
        assertThat(c.symbol).isEqualTo("NASDAQ:MSTR")
        assertThat(c.country).isEqualTo("US")
        assertThat(c.totalHoldings).isWithin(0.0001).of(226164.0)
        assertThat(c.totalCurrentValueUsd).isWithin(1.0).of(14678000000.0)
        assertThat(c.percentageOfTotalSupply).isWithin(0.0001).of(1.075)
    }
}
