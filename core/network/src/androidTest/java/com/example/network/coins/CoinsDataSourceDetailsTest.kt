package com.example.network.coins


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.network.datasource.coins.CoinsDataSource
import com.example.model.sort.DexPairFormat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

/**
 * Instrumentation tests for getCoinDetail endpoint through CoinsDataSource.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CoinsDetailDataSourceTest {

    private val json = Json { prettyPrint = true }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var remoteDataSource: CoinsDataSource

    @Before
    fun init() {
        hiltRule.inject()
        Timber.plant(Timber.DebugTree())
    }

    /**
     * Call getCoinDetail with default params and assert returned id matches requested id.
     */
    @Test
    fun getCoinDetail_defaultParams_returnsCoinDetail() = runTest {
        val requestedId = "bitcoin"

        try {
            val result = remoteDataSource.getCoinDetail(id = requestedId)

            // Log the whole DTO for manual inspection
            val formattedJson = json.encodeToString(result)
            Timber.d("--- getCoinDetail (default) ---\n%s", formattedJson)

            // Basic assertions (adjust field names if your DTO differs)
            assertTrue("Returned coin id should match requested id", result.id == requestedId)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Timber.e(e, "HTTP Error: ${e.code()} - Response: $errorBody")
            fail("API call failed with HTTP error ${e.code()}. See logs for details.")
        } catch (e: Exception) {
            Timber.e(e, "Unexpected error during getCoinDetail (default)")
            fail("Test failed with unexpected exception: ${e.message}")
        }
    }

    /**
     * Call getCoinDetail with dexPairFormat = SYMBOL to verify that path and mapping work.
     */
    @Test
    fun getCoinDetail_withDexPairFormatSymbol_returnsCoinDetail() = runTest {
        val requestedId = "ethereum"

        try {
            val result = remoteDataSource.getCoinDetail(
                id = requestedId,
                dexPairFormat = DexPairFormat.SYMBOL // named param to override default
            )

            val formattedJson = json.encodeToString(result)
            Timber.d("--- getCoinDetail (dexPairFormat = SYMBOL) ---\n%s", formattedJson)

            // Basic assertions (adjust as needed)
            assertTrue("Returned coin id should match requested id", result.id == requestedId)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Timber.e(e, "HTTP Error: ${e.code()} - Response: $errorBody")
            fail("API call failed with HTTP error ${e.code()}. See logs for details.")
        } catch (e: Exception) {
            Timber.e(e, "Unexpected error during getCoinDetail (dexPairFormat = SYMBOL)")
            fail("Test failed with unexpected exception: ${e.message}")
        }
    }
}
