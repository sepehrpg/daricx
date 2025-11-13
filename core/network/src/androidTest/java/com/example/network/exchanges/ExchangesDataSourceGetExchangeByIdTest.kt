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
class ExchangesDataSourceGetExchangeByIdTest {

    private val json = Json { prettyPrint = true }

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
    fun getExchangeById_binance_showDataInLog_doesNotThrow() = runTest {
        try {
            // Act
            val result = remoteDataSource.getExchangeById(
                id = "binance",
                dexPairFormat = null
            )

            val formatted = json.encodeToString(result)
            Timber.Forest.d("--- Exchange Detail (binance) ---\n%s", formatted)

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
}
