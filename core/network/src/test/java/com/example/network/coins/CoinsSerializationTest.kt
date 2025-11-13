package com.example.network.coins


import com.example.network.model.coins.CoinsListDto
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.Json
import org.junit.Test

/**
 * CoinsSerializationTest
 *
 * Test Goal:
 * - Ensure CoinsListDto deserializes from JSON with unknown fields ignored.
 * - Validate nested ROI and sparkline parsing.
 *
 * Scenarios:
 * 1) Deserialize markets JSON payload with known + unknown fields.
 * 2) Nested ROI deserializes (may be null).
 * 3) Nested sparkline.price list deserializes correctly.
 */
class CoinsSerializationTest {

    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    @Test fun `deserializes markets payload`() {
        val dto: CoinsListDto = json.decodeFromString(SampleJsonCoins.marketsResponse)
        assertThat(dto).hasSize(1)
        val c = dto.first()
        assertThat(c.id).isEqualTo("bitcoin")
        assertThat(c.symbol).isEqualTo("btc")
        assertThat(c.currentPrice).isWithin(0.001).of(65234.12)
        // nested
        assertThat(c.roi).isNull()
        assertThat(c.sparklineIn7d?.price).containsAtLeast(65000.0, 65234.12)
    }
}
