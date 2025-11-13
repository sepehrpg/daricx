package com.example.network.coins.mapper


import com.example.model.coins.Coins
import com.example.network.model.coins.CoinsDto
import com.example.network.model.mappers.coins.toDomain
import com.google.common.truth.Truth.assertThat
import org.junit.Test


/**
 * CoinsMapperTest
 *
 * Test Goal:
 * - Verify CoinsDto → Coins domain mapping, including nested ROI and sparkline.
 * - Ensure safe handling of null fields.
 *
 * Scenarios:
 * 1) DTO with ROI and sparkline maps to domain with all values.
 * 2) DTO with null ROI/sparkline maps safely without crash (nulls preserved).
 */
class CoinsMapperTest {

    @Test fun `maps nested ROI and sparkline`() {
        val dto = CoinsDto(
            ath = 70000.0,
            athChangePercentage = -5.0,
            athDate = "2024-03-14T12:00:00Z",
            atl = 65.0,
            atlChangePercentage = 100000.0,
            atlDate = "2013-07-06T00:00:00Z",
            circulatingSupply = 19500000.0,
            currentPrice = 65234.12,
            fullyDilutedValuation = 1360000000000.0,
            high24h = 66000.0,
            id = "bitcoin",
            image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png",
            lastUpdated = "2025-09-01T00:00:00Z",
            low24h = 64000.0,
            marketCap = 1287654321987.0,
            marketCapChange24h = -1000000000.0,
            marketCapChangePercentage24h = -0.08,
            marketCapRank = 1,
            maxSupply = 21000000.0,
            name = "Bitcoin",
            priceChange24h = -123.456,
            priceChangePercentage24h = -0.189,
            roi = CoinsDto.Roi(currency = "btc", percentage = 100.0, times = 2.0),
            symbol = "btc",
            totalSupply = 21000000.0,
            totalVolume = 2854321987.0,
            sparklineIn7d = CoinsDto.SparklineIn7d(price = listOf(65000.0, 65100.5, 65234.12))
        )

        val domain: Coins = dto.toDomain()
        assertThat(domain.id).isEqualTo("bitcoin")
        assertThat(domain.roi?.currency).isEqualTo("btc")
        assertThat(domain.roi?.times).isWithin(0.0001).of(2.0)
        assertThat(domain.sparklineIn7d?.price).containsAtLeast(65000.0, 65234.12)
    }

    @Test fun `null nested maps safely`() {
        val dto = CoinsDto(
            ath = null, athChangePercentage = null, athDate = null,
            atl = null, atlChangePercentage = null, atlDate = null,
            circulatingSupply = null, currentPrice = null, fullyDilutedValuation = null,
            high24h = null, id = null, image = null, lastUpdated = null, low24h = null,
            marketCap = null, marketCapChange24h = null, marketCapChangePercentage24h = null,
            marketCapRank = null, maxSupply = null, name = null, priceChange24h = null,
            priceChangePercentage24h = null, roi = null, symbol = null, totalSupply = null,
            totalVolume = null, sparklineIn7d = null
        )
        val domain = dto.toDomain()
        // We just assert it doesn't crash and leaves fields null accordingly
        assertThat(domain.roi).isNull()
        assertThat(domain.sparklineIn7d).isNull()
    }
}
