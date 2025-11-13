package com.example.data.nfts.m1

import com.google.common.truth.Truth.assertThat
import com.example.network.model.nfts.NftsDto
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.nfts.toDomain
import org.junit.Test

/**
 * Mapper Tests: DTO -> Domain for NFTs.
 *
 * Test Goal:
 * - Verify that all fields transfer correctly from [NftsDto] to domain model.
 *
 * Scenarios Covered:
 * 1) Happy path mapping with non-null fields.
 * 2) (Optional) You can extend with null-or-empty checks if your mapper handles them.
 */
class NftsMappersTest {

    @Test
    fun `toDomain maps all fields`() {
        val dto = NftsDto(
            assetPlatformId = "ethereum",
            contractAddress = "0xabc",
            id = "bored-ape-yacht-club",
            name = "BAYC",
            symbol = "BAYC"
        )

        val domain = dto.toDomain()
        assertThat(domain.assetPlatformId).isEqualTo("ethereum")
        assertThat(domain.contractAddress).isEqualTo("0xabc")
        assertThat(domain.id).isEqualTo("bored-ape-yacht-club")
        assertThat(domain.name).isEqualTo("BAYC")
        assertThat(domain.symbol).isEqualTo("BAYC")
    }
}
