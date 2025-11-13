package com.example.network.nfts

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.model.sort.NftsSort
import com.example.network.datasource.nfts.NftsDataSource
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
class NftsDataSourceTest {

    private val json = Json { prettyPrint = true }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var remoteDataSource: NftsDataSource

    @Before
    fun init() {
        hiltRule.inject()
        Timber.Forest.plant(Timber.DebugTree())
    }

    @Test
    fun getNftsList_showDataInLog_returnsNonEmptyList() = runTest {
        try {

            val result = remoteDataSource.getNftsList(
                page = 1,
                perPage = 50,
                order = NftsSort.MarketCapUsdDesc
            )

            val formattedJson = json.encodeToString(result)
            Timber.Forest.d("--- Fetched NFTs ---\n%s", formattedJson)

            TestCase.assertTrue("The returned list should not be empty", result.isNotEmpty())
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            Timber.Forest.e("HTTP Error: ${e.code()} - Response: $errorBody", e)
            Assert.fail("API call failed with HTTP error ${e.code()}. Check Logcat for details.")
        } catch (e: Exception) {
            Timber.Forest.e(e, "An unexpected error occurred during the test.")
            Assert.fail("Test failed with an unexpected exception: ${e.message}")
        }
    }
}