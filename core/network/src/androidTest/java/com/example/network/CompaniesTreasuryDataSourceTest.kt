package com.example.network



import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.model.option.TreasuryAsset
import com.example.network.datasource.companies.CompaniesTreasuryDataSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertNotNull
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
 * Verifies /companies/public_treasury/{coin_id} can be fetched successfully.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CompaniesTreasuryDataSourceTest {

    private val json = Json { prettyPrint = true }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var remoteDataSource: CompaniesTreasuryDataSource

    @Before
    fun init() {
        hiltRule.inject()
        Timber.plant(Timber.DebugTree())
    }

    @Test
    fun getCompaniesTreasury_bitcoin_returnsData() = runTest {
        try {
            val result = remoteDataSource.getCompaniesTreasury(TreasuryAsset.Bitcoin)
            Timber.d("--- Public Treasury (BTC) ---\n%s", json.encodeToString(result))
            assertNotNull(result)
            // Basic sanity: should contain a non-empty companies list (usually true)
            assertTrue((result.companies?.isNotEmpty()) == true)
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
