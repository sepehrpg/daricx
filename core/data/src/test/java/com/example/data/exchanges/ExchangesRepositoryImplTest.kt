package com.example.data.exchanges

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.LoadState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.data.MainDispatcherRule
import com.example.data.repository.exchanges.ExchangesRepositoryImpl
import com.example.model.exchanges.Exchanges
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.model.exchanges.ExchangesDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

/**
 * Test suite for [ExchangesRepositoryImpl].
 *
 * 🎯 Goals:
 * - Verify that the Flow<PagingData<Exchanges>> produces the correct output.
 * - Ensure that when page 1 returns mapped data and page 2 is empty,
 *   the snapshot size remains equal to the first page only.
 * - Confirm that refresh errors are propagated via [LoadState.Error].
 * - Validate that the [pageSize] parameter is correctly forwarded
 *   to the [ExchangesDataSource].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ExchangesRepositoryImplTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private lateinit var remote: ExchangesDataSource
    private lateinit var repository: ExchangesRepositoryImpl

    private val diff = object : DiffUtil.ItemCallback<Exchanges>() {
        override fun areItemsTheSame(oldItem: Exchanges, newItem: Exchanges) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Exchanges, newItem: Exchanges) = oldItem == newItem
    }

    private val noOpListCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    @Before
    fun setup() {
        remote = mockk(relaxed = true)
        repository = ExchangesRepositoryImpl(
            remoteDataSource = remote,
            ioDispatcher = mainRule.testDispatcher
        )
    }

    private fun dto(id: String, name: String, score: Int = 10) = ExchangesDto(
        country = "US",
        description = "desc-$id",
        hasTradingIncentive = true,
        id = id,
        image = "https://ex/$id.png",
        name = name,
        tradeVolume24hBtc = 123.45,
        trustScore = score,
        trustScoreRank = 1,
        url = "https://$id.com",
        yearEstablished = 2017
    )

    @Test
    fun `getExchangesPaged - first page mapped items`() = runTest {
        // Given
        coEvery { remote.getExchanges(page = 1, perPage = 2) } returns listOf(
            dto("binance", "Binance"),
            dto("kraken", "Kraken")
        )
        coEvery { remote.getExchanges(page = 2, perPage = 2) } returns emptyList()

        val flow = repository.getExchangesPaged(pageSize = 2)

        val differ = AsyncPagingDataDiffer(
            diffCallback = diff,
            updateCallback = noOpListCallback,
            mainDispatcher = mainRule.testDispatcher,
            workerDispatcher = mainRule.testDispatcher
        )

        val job = launch {
            flow.collectLatest { differ.submitData(it) }
        }

        advanceUntilIdle()

        val snap = differ.snapshot()
        assertEquals(2, snap.size)
        assertEquals("binance", snap[0]?.id)
        assertEquals("Kraken", snap[1]?.name)

        // Verify pageSize → perPage forward
        coVerify(atLeast = 1) { remote.getExchanges(page = 1, perPage = 2) }

        job.cancel()
    }

    @Test
    fun `getExchangesPaged - empty second page keeps first-page size`() = runTest {
        coEvery { remote.getExchanges(page = 1, perPage = 2) } returns listOf(
            dto("a", "A"),
            dto("b", "B")
        )
        coEvery { remote.getExchanges(page = 2, perPage = 2) } returns emptyList()

        val flow = repository.getExchangesPaged(pageSize = 2)

        val differ = AsyncPagingDataDiffer(
            diffCallback = diff,
            updateCallback = noOpListCallback,
            mainDispatcher = mainRule.testDispatcher,
            workerDispatcher = mainRule.testDispatcher
        )

        val job = launch { flow.collectLatest { differ.submitData(it) } }

        advanceUntilIdle()

        val snap = differ.snapshot()
        assertEquals(2, snap.size)
        assertTrue(snap[0]?.id in listOf("a", "b"))

        job.cancel()
    }

    @Test
    fun `getExchangesPaged - refresh error surfaces via LoadState`() = runTest {
        coEvery { remote.getExchanges(page = 1, perPage = 2) } throws IOException("boom")

        val flow = repository.getExchangesPaged(pageSize = 2)

        val differ = AsyncPagingDataDiffer(
            diffCallback = diff,
            updateCallback = noOpListCallback,
            mainDispatcher = mainRule.testDispatcher,
            workerDispatcher = mainRule.testDispatcher
        )

        val job = launch { flow.collectLatest { differ.submitData(it) } }

        advanceUntilIdle()

        val state = differ.loadStateFlow.first()
        assertTrue(state.refresh is LoadState.Error)
        assertEquals(0, differ.snapshot().size)

        job.cancel()
    }

}