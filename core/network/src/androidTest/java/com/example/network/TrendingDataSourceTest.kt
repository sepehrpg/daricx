package com.example.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.network.datasource.trending.TrendingDataSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
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
import junit.framework.TestCase.assertNotNull

/**
 * Verifies /search/trending can be fetched successfully.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class TrendingDataSourceTest {

    private val json = Json { prettyPrint = true }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var remoteDataSource: TrendingDataSource

    @Before
    fun init() {
        hiltRule.inject()
        Timber.plant(Timber.DebugTree())
    }

    @Test
    fun getTrending_showDataInLog_returnsNonNull() = runTest {
        try {
            val result = remoteDataSource.getTrending()
            val formatted = json.encodeToString(result)
            Timber.d("--- Trending ---\n%s", formatted)
            assertNotNull("Trending response should not be null", result)

        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Timber.e("HTTP Error: ${e.code()} - Response: $errorBody", e)
            fail("API call failed with HTTP error ${e.code()}.")
        } catch (e: Exception) {
            Timber.e(e, "Unexpected error")
            fail("Test failed: ${e.message}")
        }
    }
}
