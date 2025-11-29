package com.example.network.categories
import com.example.network.TestNetwork
import com.example.network.api.getCoinCategoriesKtor
import com.example.network.jsonOkResponse
import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test


/**
 * Tests the Coin Categories Ktor API using MockEngine.
 *
 * Scenarios:
 * - Request: verifies GET method, `/coins/categories` path, and default/optional query params.
 * - Response: verifies JSON deserialization into a list of categories with basic fields.
 */
class CategoriesApiTest {

    private lateinit var client: HttpClient

    @After
    fun tearDown() {
        if (::client.isInitialized) {
            client.close()
        }
    }

    @Test
    fun `getCoinCategories uses GET and sends default query parameters`() = runTest {
        lateinit var capturedMethod: HttpMethod
        lateinit var capturedPath: String
        var capturedOrder: String? = "non-null"

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            capturedOrder = request.url.parameters["order"]

            jsonOkResponse(SampleJsonCategories.categoriesResponse)
        }

        client.getCoinCategoriesKtor()

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/coins/categories")
        assertThat(capturedOrder).isNull()
    }

    @Test
    fun `deserializes coin categories json response`() = runTest {
        client = TestNetwork.ktorTestClient { _ ->
            jsonOkResponse(SampleJsonCategories.categoriesResponse)
        }

        val dto = client.getCoinCategoriesKtor(order = "market_cap_desc")

        assertThat(dto).hasSize(2)

        val first = dto.first()
        assertThat(first.id).isEqualTo("layer-1")
        assertThat(first.name).isEqualTo("Layer 1 (L1)")
        assertThat(first.marketCap).isWithin(0.001).of(150_000_000_000.0)
        assertThat(first.volume24h).isWithin(0.001).of(5_000_000_000.0)
        assertThat(first.top3Coins).hasSize(3)
    }
}
