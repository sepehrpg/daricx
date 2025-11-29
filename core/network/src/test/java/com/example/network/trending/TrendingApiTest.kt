// com/example/network/trending/TrendingApiTest.kt
package com.example.network.trending

import com.example.network.TestNetwork
import com.example.network.jsonOkResponse
import com.example.network.api.getTrendingKtor
import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

/**
 * Tests the Trending Ktor endpoint using MockEngine.
 *
 * Scenarios:
 * - Request: verifies GET method, `/search/trending` path, and absence of query params.
 * - Response: checks deserialization of trending categories, coins (wrapper + item), and NFTs.
 */
class TrendingApiTest {

    private lateinit var client: HttpClient

    @After
    fun tearDown() {
        if (::client.isInitialized) client.close()
    }

    // -------------------------------------------------------------------------
    // GET /search/trending
    // -------------------------------------------------------------------------

    @Test
    fun `getTrending uses GET and sends no query parameters`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var query: String? = null
        var queryNames: Set<String>? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            query = request.url.encodedQuery
            queryNames = request.url.parameters.names()

            jsonOkResponse(SampleJsonTrending.response)
        }

        client.getTrendingKtor()

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/search/trending")
        assertThat(queryNames).isEmpty()
    }

    @Test
    fun `deserializes trending json response`() = runTest {
        client = TestNetwork.ktorTestClient {
            jsonOkResponse(SampleJsonTrending.response)
        }

        val dto = client.getTrendingKtor()

        // ----- Categories -----
        assertThat(dto.categories).isNotNull()
        assertThat(dto.categories).hasSize(1)

        val category = dto.categories?.first()
        assertThat(category?.name).isEqualTo("DeFi")
        assertThat(category?.slug).isEqualTo("defi")
        assertThat(category?.coinsCount).isEqualTo(10)
        assertThat(category?.data?.marketCap).isWithin(0.001).of(1_000_000.0)
        assertThat(category?.data?.marketCapBtc).isWithin(0.001).of(50.0)

        // ----- Coins -----
        assertThat(dto.coins).isNotNull()
        assertThat(dto.coins).hasSize(1)

        val coinWrapper = dto.coins?.first()
        val coin = coinWrapper?.item
        assertThat(coin?.id).isEqualTo("bitcoin")
        assertThat(coin?.name).isEqualTo("Bitcoin")
        assertThat(coin?.symbol).isEqualTo("BTC")
        assertThat(coin?.marketCapRank).isEqualTo(1)
        assertThat(coin?.thumb).isEqualTo("thumb.png")
        assertThat(coin?.small).isEqualTo("small.png")
        assertThat(coin?.large).isEqualTo("large.png")
        assertThat(coin?.data?.price).isWithin(0.001).of(65_234.12)
        assertThat(coin?.data?.priceChangePercentage24h?.get("usd"))
            .isWithin(0.001).of(1.2)

        // ----- NFTs -----
        assertThat(dto.nfts).isNotNull()
        assertThat(dto.nfts).hasSize(1)

        val nft = dto.nfts?.first()
        assertThat(nft?.id).isEqualTo("bored-ape-yacht-club")
        assertThat(nft?.name).isEqualTo("Bored Ape Yacht Club")
        assertThat(nft?.symbol).isEqualTo("BAYC")
        assertThat(nft?.thumb).isEqualTo("thumb.png")
        assertThat(nft?.nativeCurrencySymbol).isEqualTo("ETH")
        assertThat(nft?.floorPriceInNativeCurrency).isWithin(0.0001).of(0.01)
        assertThat(nft?.floorPrice24hPercentageChange).isWithin(0.0001).of(1.5)
    }
}
