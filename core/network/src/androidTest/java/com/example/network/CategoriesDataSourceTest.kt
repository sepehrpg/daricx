package com.example.network


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.model.sort.CategoriesSort
import com.example.network.datasource.categories.CategoriesDataSource
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
class CategoriesDataSourceTest {

    private val json = Json { prettyPrint = true }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var remoteDataSource: CategoriesDataSource

    @Before
    fun init() {
        hiltRule.inject()
        Timber.plant(Timber.DebugTree())
    }

    @Test
    fun getCategories_showDataInLog_returnsNonEmptyList() = runTest {
        try {
            val result = remoteDataSource.getCategories(order = CategoriesSort.MarketCapDesc)
            val formattedJson = json.encodeToString(result)
            Timber.d("--- Categories ---\n%s", formattedJson)

            assertTrue("The returned list should not be empty", result.isNotEmpty())

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
