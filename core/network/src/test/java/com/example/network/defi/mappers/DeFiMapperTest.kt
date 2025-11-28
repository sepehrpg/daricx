package com.example.network.defi.mappers

import com.example.model.GlobalDeFiMarketData
import com.example.network.model.GlobalDeFiMarketDataDto
import com.example.network.model.mappers.toDomain
import com.google.common.truth.Truth
import org.junit.Test

/**
 * DeFiMapperTest
 *
 * Test Goal:
 * - Verify mapping from GlobalDeFiMarketDataDto to domain model GlobalDeFiMarketData.
 * - Ensure null-safety when 'data' is absent.
 *
 * Scenarios:
 * 1) Non-null Data → all fields are copied to domain.
 * 2) Null Data → mapper returns null.
 */
class DeFiMapperTest {

    @Test
    fun `maps dto-data to domain`() {
        val dto = GlobalDeFiMarketDataDto(
            data = GlobalDeFiMarketDataDto.Data(
                defiMarketCap = "100.0",
                ethMarketCap = "400.0",
                defiToEthRatio = "0.25",
                tradingVolume24h = "50.0",
                defiDominance = "3.8",
                topCoinName = "Lido Staked Ether",
                topCoinDefiDominance = 30.5
            )
        )

        val domain: GlobalDeFiMarketData? = dto.toDomain()
        requireNotNull(domain)

        Truth.assertThat(domain.defiMarketCap).isEqualTo("100.0")
        Truth.assertThat(domain.ethMarketCap).isEqualTo("400.0")
        Truth.assertThat(domain.defiToEthRatio).isEqualTo("0.25")
        Truth.assertThat(domain.tradingVolume24h).isEqualTo("50.0")
        Truth.assertThat(domain.defiDominance).isEqualTo("3.8")
        Truth.assertThat(domain.topCoinName).isEqualTo("Lido Staked Ether")
        Truth.assertThat(domain.topCoinDefiDominance).isWithin(0.000001).of(30.5)
    }

    @Test
    fun `null data maps to null domain`() {
        val dto = GlobalDeFiMarketDataDto(data = null)
        val domain = dto.toDomain()
        Truth.assertThat(domain).isNull()
    }
}