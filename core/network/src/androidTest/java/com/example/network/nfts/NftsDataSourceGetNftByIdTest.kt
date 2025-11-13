package com.example.network.nfts


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.network.datasource.nfts.NftsDataSource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertNotNull
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
class NftsDataSourceGetNftByIdTest {

    private val json = Json { prettyPrint = true }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var remoteDataSource: NftsDataSource

    @Before
    fun init() {
        hiltRule.inject()
        Timber.plant(Timber.DebugTree())
    }

    @Test
    fun getNftById_bayc_showDataInLog_doesNotThrow() = runTest {
        val id = "bored-ape-yacht-club"
        try {
            val result = remoteDataSource.getNftById(id = id)

            val formattedJson = json.encodeToString(result)
            Timber.d("--- NFT Detail ($id) ---\n%s", formattedJson)

            assertNotNull("Response should not be null", result)

        } catch (e: HttpException) {
            val body = e.response()?.errorBody()?.string()
            if (e.code() == 429) {
                Timber.w("Rate-limited (429). Skipping assertion. Body=%s", body)
                return@runTest
            }
            Timber.e(e, "HTTP ${e.code()} - $body")
            Assert.fail("API call failed with HTTP error ${e.code()}.")
        } catch (e: Exception) {
            Timber.e(e, "Unexpected error")
            Assert.fail("Unexpected: ${e.message}")
        }
    }
}
