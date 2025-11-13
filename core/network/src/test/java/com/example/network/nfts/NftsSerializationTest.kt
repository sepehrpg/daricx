package com.example.network.nfts


import com.example.network.model.nfts.NftsListDto
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.Json
import org.junit.Test

/**
 * NftsSerializationTest
 *
 * Test Goal:
 * - Ensure NftsListDto deserializes correctly and ignores unknown fields.
 *
 * Scenarios:
 * 1) Deserialize list with one NFT containing all fields + unknown field.
 * 2) Assert representative fields match expected values.
 */
class NftsSerializationTest {

    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    @Test fun `deserializes list and ignores unknown`() {
        val dto: NftsListDto = json.decodeFromString(SampleJsonNfts.listResponse)

        assertThat(dto).hasSize(1)
        val first = dto.first()
        assertThat(first.id).isEqualTo("bored-ape-yacht-club")
        assertThat(first.name).isEqualTo("Bored Ape Yacht Club")
        assertThat(first.symbol).isEqualTo("BAYC")
        assertThat(first.assetPlatformId).isEqualTo("ethereum")
        assertThat(first.contractAddress?.lowercase())
            .isEqualTo("0xbc4ca0eda7647a8ab7c2061c2e118a18a936f13d")
    }
}
