package com.example.network.nfts


import com.example.model.nfts.Nfts
import com.example.network.model.nfts.NftsDto
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.nfts.toDomain
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * NftsMapperTest
 *
 * Test Goal:
 * - Verify DTO -> Domain mapping copies all fields.
 *
 * Scenarios:
 * 1) Map a populated DTO to domain and assert key fields.
 * 2) Map a null-populated DTO to domain stays null accordingly (no crash).
 */
class NftsMapperTest {

    @Test fun `maps populated dto`() {
        val dto = NftsDto(
            assetPlatformId = "ethereum",
            contractAddress = "0xabc",
            id = "cool-cats",
            name = "Cool Cats",
            symbol = "COOL"
        )

        val domain: Nfts = dto.toDomain()
        assertThat(domain.id).isEqualTo("cool-cats")
        assertThat(domain.name).isEqualTo("Cool Cats")
        assertThat(domain.symbol).isEqualTo("COOL")
        assertThat(domain.assetPlatformId).isEqualTo("ethereum")
        assertThat(domain.contractAddress).isEqualTo("0xabc")
    }

    @Test fun `maps null fields safely`() {
        val dto = NftsDto(
            assetPlatformId = null,
            contractAddress = null,
            id = null,
            name = null,
            symbol = null
        )
        val domain = dto.toDomain()
        assertThat(domain.id).isNull()
        assertThat(domain.symbol).isNull()
    }
}
