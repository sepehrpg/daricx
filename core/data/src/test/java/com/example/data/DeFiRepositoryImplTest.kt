package com.example.data

import app.cash.turbine.test
import com.example.data.repository.defi.DeFiRepository
import com.example.data.repository.defi.DeFiRepositoryImpl
import com.example.model.GlobalDeFiMarketData
import com.example.network.datasource.defi.GlobalDeFiDataSource
import com.example.network.model.GlobalDeFiMarketDataDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Test suite for [DeFiRepositoryImpl].
 *
 * 🎯 Goals:
 * - Ensure repository delegates to [GlobalDeFiDataSource].
 * - Verify DTOs (with String fields) are mapped to domain [GlobalDeFiMarketData].
 * - Validate null handling and exception propagation.
 *
 * 🧪 Scenarios:
 * 1) Happy path: DataSource returns DTO → repository emits mapped domain.
 * 2) Null case: DataSource returns DTO with null data → repository emits null.
 * 3) Exception case: DataSource throws IOException → repository propagates exception.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DeFiRepositoryImplTest {

    private lateinit var repository: DeFiRepository
    private lateinit var dataSource: GlobalDeFiDataSource
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        dataSource = mockk(relaxed = true)
        repository = DeFiRepositoryImpl(
            remote = dataSource,
            io = testDispatcher
        )
    }

    // ---------------------------
    // Helpers
    // ---------------------------

    private fun sampleDto() = GlobalDeFiMarketDataDto(
        data = GlobalDeFiMarketDataDto.Data(
            defiMarketCap = "100000000.0",
            ethMarketCap = "500000000.0",
            defiToEthRatio = "0.2",
            tradingVolume24h = "50000000.0",
            defiDominance = "12.3",
            topCoinName = "Uniswap",
            topCoinDefiDominance = 5.5
        )
    )

    // ---------------------------
    // Tests
    // ---------------------------

    @Test
    fun `getGlobalDeFi - emits mapped domain`() = runTest(testDispatcher) {
        // Given
        val dto = sampleDto()
        coEvery { dataSource.getGlobalDeFi() } returns dto

        // When / Then
        repository.getGlobalDeFi().test {
            val item: GlobalDeFiMarketData? = awaitItem()
            assertEquals("100000000.0", item?.defiMarketCap)
            assertEquals("500000000.0", item?.ethMarketCap)
            assertEquals("0.2", item?.defiToEthRatio)
            assertEquals("12.3", item?.defiDominance)
            assertEquals("Uniswap", item?.topCoinName)
            assertEquals(5.5, item?.topCoinDefiDominance)
            awaitComplete()
        }

        // Verify delegation
        coVerify(exactly = 1) { dataSource.getGlobalDeFi() }
    }

    @Test
    fun `getGlobalDeFi - emits null when data is null`() = runTest(testDispatcher) {
        // Given
        val dto = GlobalDeFiMarketDataDto(data = null)
        coEvery { dataSource.getGlobalDeFi() } returns dto

        // When / Then
        repository.getGlobalDeFi().test {
            val item = awaitItem()
            assertNull(item)
            awaitComplete()
        }
    }

    @Test
    fun `getGlobalDeFi - propagates exception`() = runTest(testDispatcher) {
        // Given
        coEvery { dataSource.getGlobalDeFi() } throws IOException("boom")

        // When / Then
        repository.getGlobalDeFi().test {
            val error = awaitError()
            assertTrue(error is IOException)
        }
    }
}
