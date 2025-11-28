package com.example.network.search

import com.example.network.TestNetwork
import com.example.network.api.Search
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.create

/**
 * SearchApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for /search endpoint.
 * - Ensure "query" is passed correctly as query parameter.
 * - Verify basic deserialization into SearchDto.
 */
class SearchApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: Search

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
    // GET /search
    // -------------------------------------------------------------------------

    @Test
    fun `search uses GET and sends query parameter`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonSearch.response))

        // Act
        api.search(query = "bitcoin")

        // Assert request
        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/search")
        assertThat(req.requestUrl?.queryParameter("query")).isEqualTo("bitcoin")
    }

    @Test
    fun `deserializes search json response`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonSearch.response))

        // Act
        val dto = api.search(query = "btc")

        // Assert coins
        assertThat(dto.coins).hasSize(1)
        val coin = dto.coins?.first()
        assertThat(coin?.id).isEqualTo("bitcoin")
        assertThat(coin?.name).isEqualTo("Bitcoin")
        assertThat(coin?.symbol).isEqualTo("btc")
        assertThat(coin?.marketCapRank).isEqualTo(1)
        assertThat(coin?.thumb).isEqualTo("https://assets.coingecko.com/coins/images/1/thumb/bitcoin.png")
        assertThat(coin?.large).isEqualTo("https://assets.coingecko.com/coins/images/1/large/bitcoin.png")

        // Assert exchanges
        assertThat(dto.exchanges).hasSize(1)
        val ex = dto.exchanges?.first()
        assertThat(ex?.id).isEqualTo("binance")
        assertThat(ex?.name).isEqualTo("Binance")
        assertThat(ex?.marketType).isEqualTo("spot")
        assertThat(ex?.thumb).isEqualTo("t")
        assertThat(ex?.large).isEqualTo("l")

        // Assert categories
        assertThat(dto.categories).hasSize(1)
        val cat = dto.categories?.first()
        assertThat(cat?.id).isEqualTo("defi")
        assertThat(cat?.name).isEqualTo("DeFi")

        // Assert nfts
        assertThat(dto.nfts).hasSize(1)
        val nft = dto.nfts?.first()
        assertThat(nft?.id).isEqualTo("bored-ape-yacht-club")
        assertThat(nft?.name).isEqualTo("Bored Ape Yacht Club")
        assertThat(nft?.symbol).isEqualTo("BAYC")
        assertThat(nft?.thumb).isEqualTo("thumb.png")

        assertThat(dto.icos).contains("ico1")
    }
}
