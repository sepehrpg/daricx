package com.example.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.network.datasource.RemoteCoinsDataSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

/**
 * An instrumentation test class for [RemoteCoinsDataSource].
 * This class verifies that the data source can successfully fetch data from the remote API.
 * It uses Hilt for dependency injection in a test environment.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RemoteCoinsDataSourceTest {

    /**
     * A Json instance configured for pretty-printing the output for better readability in logs.
     */
    private val json = Json { prettyPrint = true }

    /**
     * The Hilt rule that manages the state of the Hilt components and injects dependencies.
     * This must be present in any UI test that uses Hilt.
     */
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    /**
     * Injects an instance of [RemoteCoinsDataSource] provided by Hilt.
     * This is the class under test.
     */
    @Inject
    lateinit var remoteDataSource: RemoteCoinsDataSource

    /**
     * Sets up the test environment before each test case is run.
     * This function injects the dependencies and plants a Timber tree for logging.
     */
    @Before
    fun init() {
        hiltRule.inject()
        // Plant a debug tree to see Timber logs in Logcat during the test run.
        Timber.plant(Timber.DebugTree())
    }

    /**
     * Tests if the `coinsListByIDMap` function successfully fetches a non-empty list of currencies.
     * This test performs a real network call.
     */
    @Test
    fun getCoinMarketsList_showDataInLog_returnsNonEmptyList() = runTest {
        try {
            // Arrange & Act: Call the function from the data source.
            val result = remoteDataSource.getCoinMarkets("usd")

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
