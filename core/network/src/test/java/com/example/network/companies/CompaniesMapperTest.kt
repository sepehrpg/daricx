package com.example.network.companies


import com.example.model.CompaniesTreasury
import com.example.network.model.CompaniesTreasuryDto
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.toDomain
import com.google.common.truth.Truth.assertThat
import org.junit.Test


/**
 * CompaniesMapperTest
 *
 * Test Goal:
 * - Verify mapping from CompaniesTreasuryDto to domain model CompaniesTreasury.
 *
 * Scenarios:
 * 1) Maps top-level fields (totalHoldings, totalValueUsd, marketCapDominance).
 * 2) Maps nested Company list with all fields preserved.
 */
class CompaniesMapperTest {

    @Test fun `maps dto to domain with companies list`() {
        val dto = CompaniesTreasuryDto(
            totalHoldings = 10.5,
            totalValueUsd = 100_000_000.0,
            marketCapDominance = 0.42,
            companies = listOf(
                CompaniesTreasuryDto.Company(
                    name = "ACME Corp",
                    symbol = "NYSE:ACM",
                    country = "US",
                    totalHoldings = 5.25,
                    totalEntryValueUsd = 20000000.0,
                    totalCurrentValueUsd = 25000000.0,
                    percentageOfTotalSupply = 0.001
                )
            )
        )

        val domain: CompaniesTreasury = dto.toDomain()
        assertThat(domain.totalHoldings).isWithin(0.0001).of(10.5)
        assertThat(domain.totalValueUsd).isWithin(0.0001).of(100_000_000.0)
        assertThat(domain.marketCapDominance).isWithin(0.0001).of(0.42)
        assertThat(domain.companies?.first()?.name).isEqualTo("ACME Corp")
        assertThat(domain.companies?.first()?.totalCurrentValueUsd).isWithin(0.0001).of(25_000_000.0)
    }
}
