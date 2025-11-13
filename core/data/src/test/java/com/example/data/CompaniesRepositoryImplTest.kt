package com.example.data

import app.cash.turbine.test
import com.example.data.repository.companies.CompaniesRepository
import com.example.data.repository.companies.CompaniesRepositoryImpl
import com.example.model.CompaniesTreasury
import com.example.model.option.TreasuryAsset
import com.example.network.datasource.companies.CompaniesTreasuryDataSource
import com.example.network.model.CompaniesTreasuryDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Test suite for [CompaniesRepositoryImpl].
 *
 * 🎯 Goals:
 * - Ensure repository delegates calls to [CompaniesTreasuryDataSource].
 * - Verify DTOs are mapped to domain [CompaniesTreasury] correctly.
 * - Validate exception propagation.
 *
 * 🧪 Scenarios:
 * 1) getCompaniesTreasury Bitcoin:
 *    - Given: DataSource returns DTO.
 *    - Expect: Repository emits mapped domain object.
 *    - Verify: DataSource called once with TreasuryAsset.Bitcoin.
 *
 * 2) getCompaniesTreasury Ethereum:
 *    - Similar check for Ethereum.
 *
 * 3) getCompaniesTreasury throws:
 *    - Given: DataSource throws IOException.
 *    - Expect: Flow propagates exception.
 */
class CompaniesRepositoryImplTest {

    private lateinit var repository: CompaniesRepository
    private lateinit var dataSource: CompaniesTreasuryDataSource
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        dataSource = mockk(relaxed = true)
        repository = CompaniesRepositoryImpl(
            remote = dataSource,
            io = testDispatcher
        )
    }

    // Helper: Sample DTO
    private fun sampleDto() = CompaniesTreasuryDto(
        totalHoldings = 1000.0,
        totalValueUsd = 50000.0,
        marketCapDominance = 1.2,
        companies = listOf(
            CompaniesTreasuryDto.Company(
                name = "Tesla",
                symbol = "TSLA",
                country = "US",
                totalHoldings = 500.0,
                totalEntryValueUsd = 25000.0,
                totalCurrentValueUsd = 30000.0,
                percentageOfTotalSupply = 0.05
            ),
            CompaniesTreasuryDto.Company(
                name = "MicroStrategy",
                symbol = "MSTR",
                country = "US",
                totalHoldings = 500.0,
                totalEntryValueUsd = 25000.0,
                totalCurrentValueUsd = 20000.0,
                percentageOfTotalSupply = 0.05
            )
        )
    )

    @Test
    fun `getCompaniesTreasury BTC - emits mapped domain`() = runTest(testDispatcher) {
        // Given
        val dto = sampleDto()
        coEvery { dataSource.getCompaniesTreasury(TreasuryAsset.Bitcoin) } returns dto

        // When / Then
        repository.getCompaniesTreasury(TreasuryAsset.Bitcoin).test {
            val item: CompaniesTreasury = awaitItem()
            assertEquals(dto.totalHoldings, item.totalHoldings)
            assertEquals(dto.totalValueUsd, item.totalValueUsd)
            assertEquals(dto.marketCapDominance, item.marketCapDominance)
            assertEquals(2, item.companies?.size)
            assertEquals("Tesla", item.companies?.get(0)?.name)
            awaitComplete()
        }

        coVerify(exactly = 1) { dataSource.getCompaniesTreasury(TreasuryAsset.Bitcoin) }
    }

    @Test
    fun `getCompaniesTreasury ETH - passes enum to dataSource`() = runTest(testDispatcher) {
        // Given
        val dto = sampleDto()
        coEvery { dataSource.getCompaniesTreasury(TreasuryAsset.Ethereum) } returns dto

        // When
        repository.getCompaniesTreasury(TreasuryAsset.Ethereum).test {
            val item = awaitItem()
            assertEquals(dto.totalHoldings, item.totalHoldings)
            awaitComplete()
        }

        // Verify correct delegation
        coVerify(exactly = 1) { dataSource.getCompaniesTreasury(TreasuryAsset.Ethereum) }
    }

    @Test
    fun `getCompaniesTreasury propagates exception`() = runTest(testDispatcher) {
        // Given
        coEvery { dataSource.getCompaniesTreasury(any()) } throws IOException("boom")

        // When / Then
        repository.getCompaniesTreasury(TreasuryAsset.Bitcoin).test {
            val error = awaitError()
            assertTrue(error is IOException)
        }
    }
}
