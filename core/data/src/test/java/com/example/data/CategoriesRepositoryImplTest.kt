package com.example.data

import com.example.common.result.AppResult
import com.example.data.repository.categories.CategoriesRepositoryImpl
import com.example.model.Categories
import com.example.model.sort.CategoriesSort
import com.example.network.datasource.categories.CategoriesDataSource
import com.example.network.model.CategoriesDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class CategoriesRepositoryImplTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var dataSource: CategoriesDataSource
    private lateinit var repository: CategoriesRepositoryImpl

    @Before
    fun setup() {
        dataSource = mockk(relaxed = true)
        repository = CategoriesRepositoryImpl(
            remote = dataSource,
            ioDispatcher = dispatcher
        )
    }

    // Helper
    private fun sampleDtos() = listOf(
        CategoriesDto(
            id = "layer-1",
            name = "Layer 1 (L1)",
            marketCap = 2_061_406_861_196.14,
            marketCapChange24h = -0.66,
            volume24h = 61_146_432_400.17,
            top3CoinsId = listOf("bitcoin", "ethereum", "binancecoin"),
            top3Coins = listOf(
                "https://assets.../bitcoin.png",
                "https://assets.../ethereum.png",
                "https://assets.../bnb.png"
            ),
            content = "",
            updatedAt = "2024-04-06T08:25:46.402Z"
        ),
        CategoriesDto(
            id = "defi",
            name = "DeFi",
            marketCap = 105_273_842_288.23,
            marketCapChange24h = 1.23,
            volume24h = 5_046_503_746.28,
            top3CoinsId = listOf("uniswap", "lido-dao", "aave"),
            top3Coins = listOf("u.png", "ldo.png", "aave.png"),
            content = "Decentralized Finance",
            updatedAt = "2024-04-06T09:00:00.000Z"
        )
    )

    @Test
    fun `getCategories without order - emits Loading then Success`() = runTest(dispatcher) {
        val dto = sampleDtos()
        coEvery { dataSource.getCategories(null) } returns dto

        val emissions = mutableListOf<AppResult<List<Categories>>>()

        val job = launch {
            repository.getCategories(order = null).collect { emissions.add(it) }
        }

        advanceUntilIdle()

        assertTrue(emissions.isNotEmpty())
        assertTrue(emissions[0] is AppResult.Loading)
        val success = emissions[1] as AppResult.Success
        assertEquals(2, success.data.size)
        assertEquals("layer-1", success.data[0].id)
        assertEquals("DeFi", success.data[1].name)

        coVerify(exactly = 1) { dataSource.getCategories(null) }
        job.cancel()
    }

    @Test
    fun `getCategories with order - emits Loading then Success`() = runTest(dispatcher) {
        val dto = sampleDtos()
        val order = CategoriesSort.NameAsc
        coEvery { dataSource.getCategories(order) } returns dto

        val emissions = mutableListOf<AppResult<List<Categories>>>()

        val job = launch {
            repository.getCategories(order).collect { emissions.add(it) }
        }

        advanceUntilIdle()

        assertTrue(emissions.isNotEmpty())
        assertTrue(emissions[0] is AppResult.Loading)
        val success = emissions[1] as AppResult.Success
        assertEquals(2, success.data.size)

        coVerify(exactly = 1) { dataSource.getCategories(order) }
        job.cancel()
    }

    @Test
    fun `getCategories returns empty list`() = runTest(dispatcher) {
        coEvery { dataSource.getCategories(any()) } returns emptyList()

        val emissions = mutableListOf<AppResult<List<Categories>>>()

        val job = launch {
            repository.getCategories(null).collect { emissions.add(it) }
        }

        advanceUntilIdle()

        val success = emissions[1] as AppResult.Success
        assertTrue(success.data.isEmpty())

        job.cancel()
    }

    @Test
    fun `getCategories propagates exception`() = runTest(dispatcher) {
        val ex = IOException("boom")
        coEvery { dataSource.getCategories(any()) } throws ex

        val emissions = mutableListOf<AppResult<List<Categories>>>()

        val job = launch {
            repository.getCategories(null).collect { emissions.add(it) }
        }

        advanceUntilIdle()

        val error = emissions[1] as AppResult.Error
        assertTrue(error.error.cause is IOException)

        job.cancel()
    }
}
