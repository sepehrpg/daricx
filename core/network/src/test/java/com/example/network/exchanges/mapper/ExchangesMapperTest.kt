package com.example.network.exchanges.mapper


import com.example.model.exchanges.Exchanges
import com.example.network.model.exchanges.ExchangesDto
import com.example.network.model.mappers.exchanges.toDomain
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * ExchangesMapperTest
 *
 * Test Goal:
 * - Verify mapping from ExchangesDto to domain model Exchanges.
 *
 * Scenarios:
 * 1) All scalar fields copied correctly to domain.
 * 2) Nullable fields remain null when absent.
 */
class ExchangesMapperTest {

    @Test fun `maps dto to domain`() {
        val dto = ExchangesDto(
            country = "US",
            description = "A large centralized exchange",
            hasTradingIncentive = true,
            id = "coinbase",
            image = "https://assets.coingecko.com/markets/images/2/small/coinbase.jpg",
            name = "Coinbase",
            tradeVolume24hBtc = 1234.56,
            trustScore = 9,
            trustScoreRank = 2,
            url = "https://www.coinbase.com/",
            yearEstablished = 2012
        )

        val domain: Exchanges = dto.toDomain()
        assertThat(domain.id).isEqualTo("coinbase")
        assertThat(domain.name).isEqualTo("Coinbase")
        assertThat(domain.country).isEqualTo("US")
        assertThat(domain.trustScore).isEqualTo(9)
        assertThat(domain.trustScoreRank).isEqualTo(2)
        assertThat(domain.tradeVolume24hBtc).isWithin(0.001).of(1234.56)
        assertThat(domain.yearEstablished).isEqualTo(2012)
        assertThat(domain.url).isEqualTo("https://www.coinbase.com/")
        assertThat(domain.hasTradingIncentive).isTrue()
    }

    @Test fun `maps nullables safely`() {
        val dto = ExchangesDto(
            country = null,
            description = null,
            hasTradingIncentive = null,
            id = null,
            image = null,
            name = null,
            tradeVolume24hBtc = null,
            trustScore = null,
            trustScoreRank = null,
            url = null,
            yearEstablished = null
        )

        val domain = dto.toDomain()
        // No crash; values remain null.
        assertThat(domain.id).isNull()
        assertThat(domain.name).isNull()
        assertThat(domain.trustScore).isNull()
    }
}
