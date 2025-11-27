package com.example.network.trending

import com.example.network.TestNetwork
import com.example.network.api.Trending
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.create

/**
 * TrendingApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for /search/trending endpoint.
 * - Ensure no query parameters are sent.
 * - Verify basic deserialization for TrendingDto.
 */
class TrendingApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: Trending

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork
            .retrofit(server.url("/").toString())
            .create()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    // -------------------------------------------------------------------------
    // GET /search/trending
    // -------------------------------------------------------------------------

    @Test
    fun `getTrending uses GET and sends no query parameters`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonTrending.response))

        // Act
        api.getTrending()

        // Assert request
        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/search/trending")
        assertThat(req.requestUrl?.encodedQuery).isNull()
    }

    @Test
    fun `deserializes trending json response`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonTrending.response))

        // Act
        val dto = api.getTrending()

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
        // nested data
        assertThat(coin?.data?.price).isWithin(0.001).of(65234.12)
        assertThat(coin?.data?.priceChangePercentage24h?.get("usd")).isWithin(0.001).of(1.2)

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
