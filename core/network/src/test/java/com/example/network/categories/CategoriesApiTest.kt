package com.example.network.categories

import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.create

/**
 * CategoriesApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for /coins/categories endpoint.
 * - Ensure required and optional query parameters are passed correctly.
 * - Verify basic deserialization for CategoriesListDto.
 */
class CategoriesApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    // -------------------------------------------------------------------------
    // /coins/categories
    // -------------------------------------------------------------------------

    @Test
    fun `getCoinCategories uses GET and sends default query parameters`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCategories.categoriesResponse))

        // order is null by default
        api.getCoinCategories()

        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/categories")

        // order is optional, so it should not be present when not provided
        assertThat(req.requestUrl?.queryParameter("order")).isNull()
    }

    @Test
    fun `deserializes coin categories json response`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCategories.categoriesResponse))

        val dto = api.getCoinCategories(order = "market_cap_desc")

        // Basic list size check
        assertThat(dto).hasSize(2)

        val first = dto.first()
        assertThat(first.id).isEqualTo("layer-1")
        assertThat(first.name).isEqualTo("Layer 1 (L1)")
        // Field names may need adjustment to match your CategoriesListDto
        assertThat(first.marketCap).isWithin(0.001).of(150_000_000_000.0)
        assertThat(first.volume24h).isWithin(0.001).of(5_000_000_000.0)
        assertThat(first.top3Coins).hasSize(3)
    }
}
