// com/example/network/nfts/NftsApiTest.kt
package com.example.network.nfts

import com.example.network.TestNetwork
import com.example.network.jsonOkResponse
import com.example.network.api.getNftByIdKtor
import com.example.network.api.getNftsListKtor
import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

/**
 * Tests NFT-related Ktor endpoints using MockEngine.
 *
 * Scenarios:
 * - /nfts/list: checks method, path, default/explicit query params, and list deserialization.
 * - /nfts/{id}: checks path, no query params, and NFT detail fields (pricing, supply, metadata).
 */
class NftsApiTest {

    private lateinit var client: HttpClient

    @After
    fun tearDown() {
        if (::client.isInitialized) client.close()
    }

    // -------------------------------------------------------------------------
    // GET /nfts/list
    // -------------------------------------------------------------------------

    @Test
    fun `getNftsList uses GET and sends default query parameters`() = runTest {
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var order: String? = null
        var perPage: String? = null
        var page: String? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            order = request.url.parameters["order"]
            perPage = request.url.parameters["per_page"]
            page = request.url.parameters["page"]

            jsonOkResponse(SampleJsonNfts.listResponse)
        }

        client.getNftsListKtor()

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/nfts/list")
        assertThat(order).isNull()
        assertThat(perPage).isNull()
        assertThat(page).isNull()
    }

    @Test
    fun `deserializes nfts list json response`() = runTest {
        client = TestNetwork.ktorTestClient {
            jsonOkResponse(SampleJsonNfts.listResponse)
        }

        val dto = client.getNftsListKtor(
            order = "market_cap_desc",
            perPage = 1,
            page = 1
        )

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
        var capturedMethod: HttpMethod? = null
        var capturedPath: String? = null
        var queryNames: Set<String>? = null

        client = TestNetwork.ktorTestClient { request ->
            capturedMethod = request.method
            capturedPath = request.url.encodedPath
            queryNames = request.url.parameters.names()

            jsonOkResponse(SampleJsonNfts.nftDetailResponse)
        }

        client.getNftByIdKtor(id = "bored-ape-yacht-club")

        assertThat(capturedMethod).isEqualTo(HttpMethod.Get)
        assertThat(capturedPath).isEqualTo("/nfts/bored-ape-yacht-club")
        assertThat(queryNames).isEmpty()
    }

    @Test
    fun `deserializes nft detail json response`() = runTest {
        client = TestNetwork.ktorTestClient {
            jsonOkResponse(SampleJsonNfts.nftDetailResponse)
        }

        val dto = client.getNftByIdKtor(id = "bored-ape-yacht-club")

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
