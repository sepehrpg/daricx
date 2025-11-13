package com.example.network.defi


import com.example.network.model.GlobalDeFiMarketDataDto
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

/**
 * DeFiSerializationTest
 *
 * Test Goal:
 * - Ensure GlobalDeFiMarketDataDto deserializes correctly and ignores unknown fields.
 *
 * Scenarios:
 * 1) Top-level 'data' object deserializes.
 * 2) Numeric strings (market caps, ratios, dominance) are preserved as strings.
 * 3) 'top_coin_defi_dominance' (Double) deserializes accurately.
 * 4) Unknown fields at any level are ignored.
 */
class DeFiSerializationTest {

    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    @Test fun `deserializes defi payload with nested data`() {
        val dto: GlobalDeFiMarketDataDto = json.decodeFromString(SampleJsonDeFi.defiResponse)

        val d = dto.data!!
        assertThat(d.defiMarketCap).isEqualTo("105273842288.229620442228701667")
        assertThat(d.ethMarketCap).isEqualTo("406184911478.5772415794509920285")
        assertThat(d.defiToEthRatio).isEqualTo("25.91771366026773")
        assertThat(d.tradingVolume24h).isEqualTo("5046503746.288261")
        assertThat(d.defiDominance).isEqualTo("3.86765030846147")
        assertThat(d.topCoinName).isEqualTo("Lido Staked Ether")
        assertThat(d.topCoinDefiDominance).isWithin(0.000001).of(30.589442518868)
    }
}
