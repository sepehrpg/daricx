package com.example.data


import app.cash.turbine.test
import com.example.data.repository.global.GlobalRepository
import com.example.data.repository.global.GlobalRepositoryImpl
import com.example.model.GlobalCryptoMarketData
import com.example.network.datasource.global.GlobalDataSource
import com.example.network.model.GlobalCryptoMarketDataDto
import io.mockk.coEvery
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
 * Test suite for [GlobalRepositoryImpl].
 *
 * 🎯 Goals:
 * - Ensure repository delegates to [GlobalDataSource] correctly.
 * - Verify DTO → domain mapping into [GlobalCryptoMarketData].
 * - Validate repository behavior for null data and exception propagation.
 *
 * 🧪 Scenarios:
 * 1) getGlobal returns valid DTO → emits mapped domain.
 * 2) getGlobal returns null data → emits null.
 * 3) getGlobal throws exception → propagates error downstream.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class GlobalRepositoryImplTest {

    private lateinit var repository: GlobalRepository
    private lateinit var remote: GlobalDataSource
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        remote = mockk(relaxed = true)
        repository = GlobalRepositoryImpl(remote, dispatcher)
    }

    private fun sampleDto(): GlobalCryptoMarketDataDto = GlobalCryptoMarketDataDto(
        data = GlobalCryptoMarketDataDto.Data(
            activeCryptocurrencies = 12000,
            endedIcos = 5000,
            marketCapChangePercentage24hUsd = -1.23,
            marketCapPercentage = mapOf("btc" to 45.0, "eth" to 18.0),
            markets = 800,
            ongoingIcos = 50,
            totalMarketCap = mapOf("usd" to 2_000_000_000_000.0),
            totalVolume = mapOf("usd" to 120_000_000_000.0),
            upcomingIcos = 10,
            updatedAt = 1700000000L
        )
    )

    @Test
    fun `getGlobal emits mapped domain data`() = runTest(dispatcher) {
        // Given
        coEvery { remote.getGlobal() } returns sampleDto()

        // When / Then
        repository.getGlobal().test {
            val item: GlobalCryptoMarketData? = awaitItem()

            assertEquals(12000, item?.activeCryptocurrencies)
            assertEquals(5000, item?.endedIcos)
            assertEquals(-1.23, item?.marketCapChangePercentage24hUsd)
            assertEquals(45.0, item?.marketCapPercentage?.get("btc"))
            assertEquals(2_000_000_000_000.0, item?.totalMarketCap?.get("usd"))
            assertEquals(120_000_000_000.0, item?.totalVolume?.get("usd"))
            assertEquals(1700000000L, item?.updatedAt)

            awaitComplete()
        }
    }

    @Test
    fun `getGlobal emits null when data is null`() = runTest(dispatcher) {
        // Given
        coEvery { remote.getGlobal() } returns GlobalCryptoMarketDataDto(data = null)

        // When / Then
        repository.getGlobal().test {
            val item = awaitItem()
            assertNull(item)
            awaitComplete()
        }
    }

    @Test
    fun `getGlobal propagates exception`() = runTest(dispatcher) {
        // Given
        coEvery { remote.getGlobal() } throws IOException("boom")

        // When / Then
        repository.getGlobal().test {
            val error = awaitError()
            assertTrue(error is IOException)
        }
    }
}
