// com/example/network/search/SearchApiTest.kt
package com.example.network.search

import com.example.network.TestNetwork
import com.example.network.jsonOkResponse
import com.example.network.api.searchKtor
import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

/**
 * Tests the Search Ktor endpoint using MockEngine.
 *
 * Scenarios:
 * - Request: verifies GET method, `/search` path, and `query` parameter.
 * - Response: checks deserialization of coins, exchanges, categories, NFTs, and ICOs lists.
 */
class SearchApiTest {

    private lateinit var client: HttpClient

    @After
    fun tearDown() {
        if (::client.isInitialized) client.close()
    }

    // -------------------------------------------------------------------------
    // GET /search
    // -------------------------------------------------------------------------

    @Test
    fun `search uses GET and sends query parameter`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var query: String? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            query = request.url.parameters["query"]

            jsonOkResponse(SampleJsonSearch.response)
        }

        client.searchKtor(query = "bitcoin")

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/search")
        assertThat(query).isEqualTo("bitcoin")
    }

    @Test
    fun `deserializes search json response`() = runTest {
        client = TestNetwork.ktorTestClient {
            jsonOkResponse(SampleJsonSearch.response)
        }

        val dto = client.searchKtor(query = "btc")

        // Coins
        assertThat(dto.coins).hasSize(1)
        val coin = dto.coins?.first()
        assertThat(coin?.id).isEqualTo("bitcoin")
        assertThat(coin?.name).isEqualTo("Bitcoin")
        assertThat(coin?.symbol).isEqualTo("btc")
        assertThat(coin?.marketCapRank).isEqualTo(1)
        assertThat(coin?.thumb)
            .isEqualTo("https://assets.coingecko.com/coins/images/1/thumb/bitcoin.png")
        assertThat(coin?.large)
            .isEqualTo("https://assets.coingecko.com/coins/images/1/large/bitcoin.png")

        // Exchanges
        assertThat(dto.exchanges).hasSize(1)
        val ex = dto.exchanges?.first()
        assertThat(ex?.id).isEqualTo("binance")
        assertThat(ex?.name).isEqualTo("Binance")
        assertThat(ex?.marketType).isEqualTo("spot")
        assertThat(ex?.thumb).isEqualTo("t")
        assertThat(ex?.large).isEqualTo("l")

        // Categories
        assertThat(dto.categories).hasSize(1)
        val cat = dto.categories?.first()
        assertThat(cat?.id).isEqualTo("defi")
        assertThat(cat?.name).isEqualTo("DeFi")

        // NFTs
        assertThat(dto.nfts).hasSize(1)
        val nft = dto.nfts?.first()
        assertThat(nft?.id).isEqualTo("bored-ape-yacht-club")
        assertThat(nft?.name).isEqualTo("Bored Ape Yacht Club")
        assertThat(nft?.symbol).isEqualTo("BAYC")
        assertThat(nft?.thumb).isEqualTo("thumb.png")

        assertThat(dto.icos).contains("ico1")
    }
}
