package com.example.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.network.datasource.global.GlobalDataSource
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
 * Verifies /global can be fetched successfully.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class GlobalDataSourceTest {

    private val json = Json { prettyPrint = true }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var remoteDataSource: GlobalDataSource

    @Before
    fun init() {
        hiltRule.inject()
        Timber.plant(Timber.DebugTree())
    }

    @Test
    fun getGlobal_showDataInLog_returnsNonNull() = runTest {
        try {
            val result = remoteDataSource.getGlobal()
            Timber.d("--- Global ---\n%s", json.encodeToString(result))
            assertNotNull("Global response should not be null", result)
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
