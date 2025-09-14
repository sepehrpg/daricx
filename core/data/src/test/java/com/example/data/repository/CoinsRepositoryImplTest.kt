package com.example.data.repository

import app.cash.turbine.test
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.LoadState
import com.example.data.testing.PagingTestUtils
import com.example.data.testing.TestFixtures
import com.example.network.api.ApiService
import com.example.network.datasource.coins.CoinsDataSourceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalCoroutinesApi::class)
class CoinsRepositoryImplTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var server: MockWebServer
    private lateinit var repository: CoinsRepository

    @BeforeEach
    fun setUp() {
        server = MockWebServer()
        server.start()
        val json = Json { ignoreUnknownKeys = true }
        val client = OkHttpClient.Builder()
            .readTimeout(100, TimeUnit.MILLISECONDS)
            .writeTimeout(100, TimeUnit.MILLISECONDS)
            .connectTimeout(100, TimeUnit.MILLISECONDS)
            .build()
        val api = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(ApiService::class.java)
        val remote = CoinsDataSourceImpl(api)
        repository = CoinsRepositoryImpl(remote, dispatcher)
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `getCoinMarketsPaged emits data on success`() = runTest(dispatcher) {
        server.enqueue(MockResponse().setResponseCode(200).setBody(TestFixtures.coinMarketsJson))
        repository.getCoinMarketsPaged("usd", pageSize = 1, order = null, sparkline = null, priceChangePercentage = null, pageTransform = null)
            .test {
                val pagingData = awaitItem()
                val differ = AsyncPagingDataDiffer(
                    diffCallback = PagingTestUtils.CoinMarketDiff,
                    updateCallback = PagingTestUtils.noopListUpdateCallback,
                    workerDispatcher = dispatcher,
                )
                differ.submitData(pagingData)
                advanceUntilIdle()
                assertThat(differ.snapshot().size).isEqualTo(1)
                val item = differ.snapshot()[0]
                assertThat(item.id).isEqualTo("bitcoin")
                assertThat(item.currentPrice).isEqualTo(1.23)
                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun `getCoinMarketsPaged returns Http 404 error`() = runTest(dispatcher) {
        server.enqueue(MockResponse().setResponseCode(404))
        repository.getCoinMarketsPaged("usd", 1, null, null, null, null).test {
            val pagingData = awaitItem()
            val differ = AsyncPagingDataDiffer(
                diffCallback = PagingTestUtils.CoinMarketDiff,
                updateCallback = PagingTestUtils.noopListUpdateCallback,
                workerDispatcher = dispatcher,
            )
            differ.submitData(pagingData)
            advanceUntilIdle()
            val state = differ.loadStateFlow.value.refresh as LoadState.Error
            val error = state.error as HttpException
            assertThat(error.code()).isEqualTo(404)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCoinMarketsPaged returns Http 500 error`() = runTest(dispatcher) {
        server.enqueue(MockResponse().setResponseCode(500))
        repository.getCoinMarketsPaged("usd", 1, null, null, null, null).test {
            val pagingData = awaitItem()
            val differ = AsyncPagingDataDiffer(
                diffCallback = PagingTestUtils.CoinMarketDiff,
                updateCallback = PagingTestUtils.noopListUpdateCallback,
                workerDispatcher = dispatcher,
            )
            differ.submitData(pagingData)
            advanceUntilIdle()
            val state = differ.loadStateFlow.value.refresh as LoadState.Error
            val error = state.error as HttpException
            assertThat(error.code()).isEqualTo(500)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCoinMarketsPaged handles timeout`() = runTest(dispatcher) {
        server.enqueue(MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE))
        repository.getCoinMarketsPaged("usd", 1, null, null, null, null).test {
            val pagingData = awaitItem()
            val differ = AsyncPagingDataDiffer(
                diffCallback = PagingTestUtils.CoinMarketDiff,
                updateCallback = PagingTestUtils.noopListUpdateCallback,
                workerDispatcher = dispatcher,
            )
            differ.submitData(pagingData)
            advanceUntilIdle()
            val state = differ.loadStateFlow.value.refresh as LoadState.Error
            assertThat(state.error).isInstanceOf(SocketTimeoutException::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCoinMarketsPaged handles malformed json`() = runTest(dispatcher) {
        server.enqueue(MockResponse().setResponseCode(200).setBody("{"))
        repository.getCoinMarketsPaged("usd", 1, null, null, null, null).test {
            val pagingData = awaitItem()
            val differ = AsyncPagingDataDiffer(
                diffCallback = PagingTestUtils.CoinMarketDiff,
                updateCallback = PagingTestUtils.noopListUpdateCallback,
                workerDispatcher = dispatcher,
            )
            differ.submitData(pagingData)
            advanceUntilIdle()
            val state = differ.loadStateFlow.value.refresh as LoadState.Error
            assertThat(state.error).isInstanceOf(kotlinx.serialization.SerializationException::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCoinMarketsPaged emits empty list`() = runTest(dispatcher) {
        server.enqueue(MockResponse().setResponseCode(200).setBody("[]"))
        repository.getCoinMarketsPaged("usd", 1, null, null, null, null).test {
            val pagingData = awaitItem()
            val differ = AsyncPagingDataDiffer(
                diffCallback = PagingTestUtils.CoinMarketDiff,
                updateCallback = PagingTestUtils.noopListUpdateCallback,
                workerDispatcher = dispatcher,
            )
            differ.submitData(pagingData)
            advanceUntilIdle()
            assertThat(differ.snapshot()).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }
}
