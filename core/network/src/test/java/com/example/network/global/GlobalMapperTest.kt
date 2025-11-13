package com.example.network.global


import com.example.model.GlobalCryptoMarketData
import com.example.network.model.GlobalCryptoMarketDataDto
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.toDomain
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * GlobalMapperTest
 *
 * Test Goal:
 * - Verify DTO -> Domain mapping mirrors all fields, including maps.
 *
 * Scenarios:
 * 1) Map a fully-populated DTO and assert domain equality on key fields.
 * 2) Handle null data safely -> returns null.
 */
class GlobalMapperTest {

    @Test fun `maps populated dto to domain`() {
        val dto = GlobalCryptoMarketDataDto(
            data = GlobalCryptoMarketDataDto.Data(
                activeCryptocurrencies = 12045,
                endedIcos = 3400,
                marketCapChangePercentage24hUsd = -0.56,
                marketCapPercentage = mapOf("btc" to 52.1, "eth" to 17.3),
                markets = 830,
                ongoingIcos = 12,
                totalMarketCap = mapOf("usd" to 2_350_000_000_000.0, "btc" to 38_000_000.123),
                totalVolume = mapOf("usd" to 120_000_000_000.0),
                upcomingIcos = 25,
                updatedAt = 1_725_148_800L
            )
        )

        val domain: GlobalCryptoMarketData? = dto.toDomain()
        requireNotNull(domain)

        assertThat(domain.activeCryptocurrencies).isEqualTo(12045)
        assertThat(domain.marketCapChangePercentage24hUsd).isWithin(0.001).of(-0.56)
        assertThat(domain.totalMarketCap?.get("usd")).isWithin(0.001).of(2_350_000_000_000.0)
        assertThat(domain.marketCapPercentage?.get("btc")).isWithin(0.0001).of(52.1)
        assertThat(domain.updatedAt).isEqualTo(1_725_148_800L)
    }

    @Test fun `null data maps to null domain`() {
        val dto = GlobalCryptoMarketDataDto(data = null)
        val domain = dto.toDomain()
        assertThat(domain).isNull()
    }
}
