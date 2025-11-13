package com.example.network.exchanges


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.network.datasource.exchanges.ExchangesDataSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ExchangesDataSourceTickersAndVolumeChartTest {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var remoteDataSource: ExchangesDataSource

    @Before
    fun init() {
        hiltRule.inject()
        Timber.Forest.plant(Timber.DebugTree())
    }

    @Test
    fun getExchangeTickersById_showDataInLog_doesNotThrow() = runTest {
        try {
            val result = remoteDataSource.getExchangeTickersById(
                id = "binance",
                coinIds = "bitcoin",
                includeExchangeLogo = true,
                depth = false,
                dexPairFormat = null,
                page = 1,
                order = null
            )

            val formatted = json.encodeToString(result)
            Timber.Forest.d("--- Exchange Tickers (binance) ---\n%s", formatted)

            TestCase.assertNotNull("Response should not be null", result)

        } catch (e: HttpException) {
            val body = e.response()?.errorBody()?.string()
            if (e.code() == 429) {
                Timber.Forest.w("Rate-limited (429). Skipping assertion. Body=%s", body)
                return@runTest
            }
            Timber.Forest.e("HTTP ${e.code()} - $body", e)
            Assert.fail("API call failed with HTTP error ${e.code()}.")
        } catch (e: Exception) {
            Timber.Forest.e(e, "Unexpected error")
            Assert.fail("Unexpected: ${e.message}")
        }
    }

    @Test
    fun getExchangeVolumeChart_binance_7days_returnsPoints() = runTest {
        try {
            val result = remoteDataSource.getExchangeVolumeChart(
                id = "binance",
                days = "7"
            )

            val formatted = json.encodeToString(result)
            Timber.Forest.d("--- Exchange Volume Chart (binance, 7d) ---\n%s", formatted)

            val points = result.points
            TestCase.assertTrue("Volume chart points should not be empty", points.isNotEmpty())
            TestCase.assertTrue("Timestamps must be > 0", points.all { it.timestampMillis > 0 })

        } catch (e: HttpException) {
            val body = e.response()?.errorBody()?.string()
            if (e.code() == 429) {
                Timber.Forest.w("Rate-limited (429). Skipping assertion. Body=%s", body)
                return@runTest
            }
            Timber.Forest.e("HTTP ${e.code()} - $body", e)
            Assert.fail("API call failed with HTTP error ${e.code()}.")
        } catch (e: Exception) {
            Timber.Forest.e(e, "Unexpected error")
            Assert.fail("Unexpected: ${e.message}")
        }
    }
}
