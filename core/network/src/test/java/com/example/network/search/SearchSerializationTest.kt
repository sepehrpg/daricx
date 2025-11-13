package com.example.network.search


import com.example.network.model.SearchDto
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test

/**
 * SearchSerializationTest
 *
 * Test Goal:
 * - Ensure SearchDto deserializes correctly, ignoring unknown fields.
 *
 * Scenarios:
 * 1) Deserialize response with coins, exchanges, categories, nfts.
 * 2) Validate representative fields.
 */
class SearchSerializationTest {

    private val json = Json { ignoreUnknownKeys = true; explicitNulls = false }

    @Test fun `deserializes search response`() {
        val dto: SearchDto = json.decodeFromString(SampleJsonSearch.response)

        assertThat(dto.coins).isNotNull()
        assertThat(dto.coins!!.first().id).isEqualTo("bitcoin")
        assertThat(dto.exchanges!!.first().name).isEqualTo("Binance")
        assertThat(dto.categories!!.first().id).isEqualTo("defi")
        assertThat(dto.nfts!!.first().symbol).isEqualTo("BAYC")
    }
}
