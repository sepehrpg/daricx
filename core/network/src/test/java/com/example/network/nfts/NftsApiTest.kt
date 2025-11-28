package com.example.network.nfts

import com.example.network.TestNetwork
import com.example.network.api.Nfts
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.create

/**
 * NftsApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for NFT endpoints.
 * - Ensure required & optional query parameters are passed correctly.
 * - Verify basic deserialization for NftsListDto & NftDetailsDto.
 */
class NftsApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: Nfts

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
    // GET /nfts/list
    // -------------------------------------------------------------------------

    @Test
    fun `getNftsList uses GET and sends default query parameters`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonNfts.listResponse))

        // Act
        api.getNftsList()

        // Assert request
        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/nfts/list")

        // Default query params -> all null
        assertThat(req.requestUrl?.queryParameter("order")).isNull()
        assertThat(req.requestUrl?.queryParameter("per_page")).isNull()
        assertThat(req.requestUrl?.queryParameter("page")).isNull()
    }

    @Test
    fun `deserializes nfts list json response`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonNfts.listResponse))

        // Act
        val dto = api.getNftsList(
            order = "market_cap_desc",
            perPage = 1,
            page = 1
        )

        // Assert
        assertThat(dto).hasSize(1)

        val first = dto.first()
        assertThat(first.id).isEqualTo("bored-ape-yacht-club")
        assertThat(first.name).isEqualTo("Bored Ape Yacht Club")
        assertThat(first.symbol).isEqualTo("BAYC")
        assertThat(first.assetPlatformId).isEqualTo("ethereum")
        assertThat(first.contractAddress)
            .isEqualTo("0xbc4ca0eda7647a8ab7c2061c2e118a18a936f13d")
    }

    // -------------------------------------------------------------------------
    // GET /nfts/{id}
    // -------------------------------------------------------------------------

    @Test
    fun `getNftById uses GET and correct path`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonNfts.nftDetailResponse))

        // Act
        api.getNftById(id = "bored-ape-yacht-club")

        // Assert
        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath)
            .isEqualTo("/nfts/bored-ape-yacht-club")

        assertThat(req.requestUrl?.queryParameterNames).isEmpty()
    }

    @Test
    fun `deserializes nft detail json response`() = runTest {
        // Arrange
        server.enqueue(MockResponse().setBody(SampleJsonNfts.nftDetailResponse))

        // Act
        val dto = api.getNftById(id = "bored-ape-yacht-club")

        assertThat(dto.id).isEqualTo("bored-ape-yacht-club")
        assertThat(dto.name).isEqualTo("Bored Ape Yacht Club")
        assertThat(dto.symbol).isEqualTo("BAYC")
        assertThat(dto.assetPlatformId).isEqualTo("ethereum")
        assertThat(dto.contractAddress)
            .isEqualTo("0xbc4ca0eda7647a8ab7c2061c2e118a18a936f13d")

        assertThat(dto.floorPrice?.usd).isWithin(0.000001).of(24.5)
        assertThat(dto.marketCap?.usd).isWithin(0.000001).of(3_000_000_000.0)
        assertThat(dto.totalSupply).isEqualTo(10_000.0)
    }
}
