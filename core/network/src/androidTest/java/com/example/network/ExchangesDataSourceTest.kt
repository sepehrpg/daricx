package com.example.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.network.datasource.exchanges.ExchangesDataSource
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


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ExchangesDataSourceTest {

    private val json = Json { prettyPrint = true }


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var remoteDataSource: ExchangesDataSource

    @Before
    fun init() {
        hiltRule.inject()
        // Plant a debug tree to see Timber logs in Logcat during the test run.
        Timber.plant(Timber.DebugTree())
    }


    @Test
    fun getExchangeList_showDataInLog_returnsNonEmptyList() = runTest {
        try {
            // Arrange & Act: Call the function from the data source.
            val result = remoteDataSource.getExchanges(page = 1, perPage = 50)

            // Log the fetched data for manual verification.
            val formattedJson = json.encodeToString(result)
            Timber.d("--- Fetched Data ---\n%s", formattedJson)

            // Assert: Verify that the result is not empty, confirming a successful fetch.
            assertTrue("The returned list should not be empty", result.isNotEmpty())

        } catch (e: HttpException) {
            // This block specifically catches HTTP errors from the server.
            val errorBody = e.response()?.errorBody()?.string()
            Timber.e(
                "HTTP Error: ${e.code()} - Response: $errorBody",
                e
            )
            fail("API call failed with HTTP error ${e.code()}. Check Logcat for details.")
        } catch (e: Exception) {
            // This catches other exceptions like network connectivity issues.
            Timber.e(e, "An unexpected error occurred during the test.")
            fail("Test failed with an unexpected exception: ${e.message}")
        }
    }
}
