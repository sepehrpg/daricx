package com.example.network.exchanges


import com.example.network.model.exchanges.ExchangesListDto
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.Json
import org.junit.Test

/**
 * ExchangesSerializationTest
 *
 * Test Goal:
 * - Ensure ExchangesListDto deserializes from JSON and ignores unknown fields.
 *
 * Scenarios:
 * 1) Deserialize list payload; first item has expected scalar fields.
 * 2) Unknown fields are ignored without error.
 */
class ExchangesSerializationTest {

    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    @Test fun `deserializes exchanges payload`() {
        val dto: ExchangesListDto = json.decodeFromString(SampleJsonExchanges.exchangesResponse)
        assertThat(dto).hasSize(1)

        val ex = dto.first()
        assertThat(ex.id).isEqualTo("binance")
        assertThat(ex.name).isEqualTo("Binance")
        assertThat(ex.country).isEqualTo("Cayman Islands")
        assertThat(ex.trustScoreRank).isEqualTo(1)
        assertThat(ex.tradeVolume24hBtc).isWithin(0.001).of(123456.789)
        assertThat(ex.hasTradingIncentive).isFalse()
    }
}
