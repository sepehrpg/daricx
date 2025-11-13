package com.example.network.trending


import com.example.network.model.TrendingDto
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

/**
 * TrendingSerializationTest
 *
 * Test Goal:
 * - Ensure TrendingDto deserializes correctly, ignoring unknown fields.
 *
 * Scenarios:
 * 1) Deserialize response with categories, coins, nfts.
 * 2) Validate representative fields.
 */
class TrendingSerializationTest {

    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    @Test fun `deserializes trending response`() {
        val dto: TrendingDto = json.decodeFromString(SampleJsonTrending.response)

        assertThat(dto.categories).isNotNull()
        assertThat(dto.categories!!.first().name).isEqualTo("DeFi")

        assertThat(dto.coins!!.first().item?.name).isEqualTo("Bitcoin")
        assertThat(dto.coins!!.first().item?.symbol).isEqualTo("BTC")

        assertThat(dto.nfts!!.first().name).isEqualTo("Bored Ape Yacht Club")
        assertThat(dto.nfts!!.first().symbol).isEqualTo("BAYC")
    }
}
