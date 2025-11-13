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
 * - Validate HTTP contract for GET /coins/categories endpoint.
 *
 * Scenarios:
 * 1) Sends GET to correct path without "order" when null.
 * 2) Sends GET with "order" query param when provided.
 * 3) Deserializes a valid JSON payload into CategoriesListDto.
 */
class CategoriesApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        val retrofit = TestNetwork.retrofit(server.url("/").toString())
        api = retrofit.create()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `GET without order has no query param`() = runTest {
        server.enqueue(MockResponse().setResponseCode(200).setBody(SampleJsonCategories.categoriesResponse))

        api.getCoinCategories(order = null)
        val req = server.takeRequest()
        println("➡️ Request path: ${req.path}")

        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/categories")
        assertThat(req.requestUrl?.query).isNull()
    }

    @Test
    fun `GET with order includes query param`() = runTest {
        server.enqueue(MockResponse().setResponseCode(200).setBody(SampleJsonCategories.categoriesResponse))

        api.getCoinCategories(order = "market_cap_desc")
        val req = server.takeRequest()
        println("➡️ Request path: ${req.path}")

        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/coins/categories")
        assertThat(req.requestUrl?.queryParameter("order")).isEqualTo("market_cap_desc")
    }

    @Test
    fun `deserializes json payload`() = runTest {
        server.enqueue(MockResponse().setResponseCode(200).setBody(SampleJsonCategories.categoriesResponse))

        val dto = api.getCoinCategories(order = null)
        println("➡️ Parsed response: $dto")

        assertThat(dto).hasSize(1)
        val first = dto.first()
        assertThat(first.id).isEqualTo("decentralized-finance-defi")
        assertThat(first.name).isEqualTo("Decentralized Finance (DeFi)")
        assertThat(first.marketCap).isWithin(0.001).of(123456789.12)
        assertThat(first.top3Coins).hasSize(3)
        assertThat(first.top3CoinsId?.get(0)).isEqualTo("bitcoin")
    }
}
