package com.example.network.categories


import com.example.model.sort.CategoriesSort
import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.example.network.datasource.categories.CategoriesDataSource
import com.example.network.datasource.categories.CategoriesDataSourceImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.create

/**
 * CategoriesDataSourceImplTest
 *
 * Test Goal:
 * - Ensure DataSource forwards domain sort -> API "order" via toApiOrderParam().
 * - Propagates HTTP errors as exceptions.
 *
 * Scenarios:
 * 1) order=NameAsc -> "name_asc" appears in request.
 * 2) null order -> request has no "order".
 * 3) HTTP 500 -> throws HttpException.
 */
class CategoriesDataSourceImplTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService
    private lateinit var ds: CategoriesDataSource

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        val retrofit = TestNetwork.retrofit(server.url("/").toString())
        api = retrofit.create()
        ds = CategoriesDataSourceImpl(api)
    }

    @After fun tearDown() {
        server.shutdown()
    }

    @Test fun `forwards NameAsc as name_asc`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCategories.categoriesResponse))

        ds.getCategories(CategoriesSort.NameAsc)

        val req = server.takeRequest()
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/categories")
        assertThat(req.requestUrl?.queryParameter("order")).isEqualTo("name_asc")
    }

    @Test fun `null order omits query param`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCategories.categoriesResponse))

        ds.getCategories(null)

        val req = server.takeRequest()
        assertThat(req.requestUrl?.queryParameter("order")).isNull()
    }

    @Test(expected = HttpException::class)
    fun `http 500 throws HttpException`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("""{"error":"server"}"""))

        ds.getCategories(CategoriesSort.MarketCapDesc)
    }
}
