package com.example.data.coins

import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.data.repository.coins.CoinsRepositoryImpl
import com.example.model.coins.Coins
import com.example.model.sort.CoinsSort
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.model.coins.CoinsDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

/**
 * Test suite for [com.example.data.repository.coins.CoinsRepositoryImpl].
 *
 * 🎯 Goals:
 * - Ensure `getCoinMarketsPaged` returns PagingData emitting domain models in order.
 * - Verify repository wires `order/sparkline/priceChangePercentage` through to data source via paging source.
 * - Validate pageTransform is applied when provided.
 * - Validate empty paging results.
 *
 * 🧪 Scenarios:
 * 1) First page success → snapshot contains mapped items.
 * 2) Order is forwarded → data source called with expected `order`.
 * 3) pageTransform changes order → snapshot reflects transformed order.
 * 4) Empty second page → no extra items after first page.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CoinsRepositoryImplTest {

    private lateinit var remote: CoinsDataSource
    private lateinit var repository: CoinsRepositoryImpl
    private val dispatcher = StandardTestDispatcher()

    // ---- helpers for paging snapshot ----
    private val noOpListCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
    private val diff = object : DiffUtil.ItemCallback<Coins>() {
        override fun areItemsTheSame(oldItem: Coins, newItem: Coins) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Coins, newItem: Coins) = oldItem == newItem
    }

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        remote = mockk(relaxed = true)
        repository = CoinsRepositoryImpl(
            remoteDataSource = remote,
            ioDispatcher = dispatcher
        )
    }

    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun dto(id: String, name: String, price: Double) = CoinsDto(
        ath = null, athChangePercentage = null, athDate = null,
        atl = null, atlChangePercentage = null, atlDate = null,
        circulatingSupply = null, currentPrice = price,
        fullyDilutedValuation = null, high24h = null, id = id,
        image = "img/$id.png", lastUpdated = null, low24h = null,
        marketCap = null, marketCapChange24h = null, marketCapChangePercentage24h = null,
        marketCapRank = null, maxSupply = null, name = name, priceChange24h = null,
        priceChangePercentage24h = null, roi = null, symbol = id.uppercase(),
        totalSupply = null, totalVolume = null, sparklineIn7d = null
    )

    @Test
    fun `getCoinMarketsPaged emits first page mapped items`() = runTest(dispatcher) {
        // Given: page1 has 2 items, page2 empty
        coEvery {
            remote.getCoinMarkets(
                "usd",
                page = 1,
                perPage = 2,
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns
                listOf(dto("btc", "Bitcoin", 100.0), dto("eth", "Ethereum", 50.0))
        coEvery {
            remote.getCoinMarkets(
                "usd",
                page = 2,
                perPage = 2,
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns emptyList()

        val flow = repository.getCoinMarketsPaged(
            vsCurrency = "usd",
            pageSize = 2,
            order = CoinsSort.MarketCapDesc,
            sparkline = false,
            priceChangePercentage = null
        )

        val differ = AsyncPagingDataDiffer(
            diffCallback = diff,
            updateCallback = noOpListCallback,
            workerDispatcher = dispatcher,
            mainDispatcher = dispatcher
        )

        val job = launch {
            flow.collectLatest { pagingData ->
                differ.submitData(pagingData)
            }
        }

        // Allow paging to run
        advanceUntilIdle()

        // Then
        val snapshot = differ.snapshot()
        TestCase.assertEquals(2, snapshot.size)
        TestCase.assertEquals("btc", snapshot[0]?.id)
        TestCase.assertEquals("eth", snapshot[1]?.id)

        // order was forwarded (at least for first page)
        coVerify(atLeast = 1) {
            remote.getCoinMarkets(
                vsCurrency = "usd",
                page = 1,
                perPage = 2,
                order = CoinsSort.MarketCapDesc,
                sparkline = false,
                priceChangePercentage = null,
                locale = null,
                precision = null
            )
        }

        job.cancel()
    }

    @Test
    fun `getCoinMarketsPaged applies pageTransform`() = runTest(dispatcher) {
        // Given: two items with different prices
        coEvery {
            remote.getCoinMarkets(
                any(),
                page = 1,
                perPage = 2,
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns
                listOf(dto("eth", "Ethereum", 50.0), dto("btc", "Bitcoin", 100.0))
        coEvery {
            remote.getCoinMarkets(
                any(),
                page = 2,
                perPage = 2,
                any(),
                any(),
                any(),
                any(),
                any()
            )
        } returns emptyList()

        val transform: (List<Coins>) -> List<Coins> = { list ->
            list.sortedByDescending { it.currentPrice ?: Double.MIN_VALUE }
        }

        val flow = repository.getCoinMarketsPaged(
            vsCurrency = "usd",
            pageSize = 2,
            order = null,
            sparkline = true,
            priceChangePercentage = null,
            pageTransform = transform
        )

        val differ = AsyncPagingDataDiffer(
            diffCallback = diff,
            updateCallback = noOpListCallback,
            workerDispatcher = dispatcher
        )

        val job = launch {
            flow.collectLatest { pagingData ->
                differ.submitData(pagingData)
            }
        }

        advanceUntilIdle()

        val idsInOrder = differ.snapshot().items.filterNotNull().map { it.id }
        // Expect btc (100) before eth (50) after transform
        TestCase.assertEquals(listOf("btc", "eth"), idsInOrder)

        job.cancel()
    }

    @Test
    fun `getCoinMarketsPaged empty second page keeps snapshot size from first page`() =
        runTest(dispatcher) {
            coEvery {
                remote.getCoinMarkets(
                    any(),
                    page = 1,
                    perPage = 2,
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns
                    listOf(dto("a", "A", 1.0), dto("b", "B", 2.0))
            coEvery {
                remote.getCoinMarkets(
                    any(),
                    page = 2,
                    perPage = 2,
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns emptyList()

            val flow = repository.getCoinMarketsPaged(
                vsCurrency = "usd",
                pageSize = 2,
                order = null,
                sparkline = true,
                priceChangePercentage = null
            )

            val differ = AsyncPagingDataDiffer(
                diffCallback = diff,
                updateCallback = noOpListCallback,
                workerDispatcher = dispatcher
            )

            val job = launch {
                flow.collectLatest { differ.submitData(it) }
            }

            advanceUntilIdle()

            TestCase.assertEquals(2, differ.snapshot().size)
            TestCase.assertTrue(differ.snapshot()[0]?.id in listOf("a", "b"))

            job.cancel()
        }
}