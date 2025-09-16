package com.example.network

import com.example.network.datasource.defi.GlobalDeFiDataSource
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

/**
 * Verifies /global/decentralized_finance_defi can be fetched successfully.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class GlobalDeFiDataSourceTest {

    private val json = Json { prettyPrint = true }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var remoteDataSource: GlobalDeFiDataSource

    @Before
    fun init() {
        hiltRule.inject()
        Timber.plant(Timber.DebugTree())
    }

    @Test
    fun getGlobalDeFi_showDataInLog_returnsNonNull() = runTest {
        try {
            val result = remoteDataSource.getGlobalDeFi()
            Timber.d("--- Global DeFi ---\n%s", json.encodeToString(result))
            assertNotNull("Global DeFi response should not be null", result)
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
