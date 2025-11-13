package com.example.network.global


import com.example.network.model.GlobalCryptoMarketDataDto
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

/**
 * GlobalSerializationTest
 *
 * Test Goal:
 * - Ensure GlobalCryptoMarketDataDto deserializes correctly and ignores unknown fields.
 *
 * Scenarios:
 * 1) Deserialize nested structure (data + maps) with an extra unknown field.
 * 2) Validate representative map entries and numeric values.
 */
class GlobalSerializationTest {

    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    @Test fun `deserializes with maps and ignores unknowns`() {
        val dto: GlobalCryptoMarketDataDto =
            json.decodeFromString(SampleJsonGlobal.globalResponseWithUnknown)

        val d = dto.data!!
        assertThat(d.activeCryptocurrencies).isEqualTo(12045)
        assertThat(d.totalMarketCap?.get("usd")).isWithin(0.001).of(2_350_000_000_000.0)
        assertThat(d.totalVolume?.get("usd")).isWithin(0.001).of(120_000_000_000.0)
        assertThat(d.marketCapPercentage?.get("eth")).isWithin(0.0001).of(17.3)
        assertThat(d.updatedAt).isEqualTo(1_725_148_800L)
    }
}
