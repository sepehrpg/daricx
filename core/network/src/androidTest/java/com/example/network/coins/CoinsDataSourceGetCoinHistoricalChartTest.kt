package com.example.network.coins



import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.network.datasource.coins.CoinsDataSource
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
class CoinsDataSourceGetCoinHistoricalChartTest {

    private val json = Json { prettyPrint = true }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var remoteDataSource: CoinsDataSource

    @Before
    fun init() {
        hiltRule.inject()
        Timber.Forest.plant(Timber.DebugTree())
    }

    @Test
    fun getCoinHistoricalChart_bitcoin_last7Days_returnsData() = runTest {
        try {
            val result = remoteDataSource.getCoinHistoricalChart(
                id = "bitcoin",
                vsCurrency = "usd",
                days = "7",
                interval = "daily",
                precision = "full"
            )

            Timber.Forest.d("--- Market Chart (7d) ---\n%s", json.encodeToString(result))

            val pricesSize = result.prices?.size ?: 0
            TestCase.assertTrue("prices should not be empty", pricesSize > 0)

        } catch (e: HttpException) {
            val body = e.response()?.errorBody()?.string()
            Timber.Forest.e("HTTP ${e.code()} - $body", e)
            Assert.fail("HTTP error ${e.code()}")
        } catch (e: Exception) {
            Timber.Forest.e(e, "Unexpected error")
            Assert.fail("Unexpected: ${e.message}")
        }
    }
}
