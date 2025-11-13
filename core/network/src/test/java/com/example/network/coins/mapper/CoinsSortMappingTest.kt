package com.example.network.coins.mapper



import com.example.model.sort.CoinsSort
import com.example.network.options.toApiOrderParamOrNull
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * CoinsSortMappingTest
 *
 * Test Goal:
 * - Verify CoinsSort enum values map to correct API "order" strings.
 * - Ensure client-only sorts return null.
 *
 * Scenarios:
 * 1) MarketCapAsc → "market_cap_asc".
 * 2) MarketCapDesc → "market_cap_desc".
 * 3) VolumeAsc → "volume_asc".
 * 4) VolumeDesc → "volume_desc".
 * 5) IdAsc → "id_asc".
 * 6) IdDesc → "id_desc".
 * 7) PriceAsc/PriceDesc/Change24hAsc/Change24hDesc → null.
 */
@RunWith(Parameterized::class)
class CoinsSortMappingTest(
    private val input: CoinsSort,
    private val expected: String?
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: {0} -> {1}")
        fun data() = listOf(
            arrayOf(CoinsSort.MarketCapAsc,  "market_cap_asc"),
            arrayOf(CoinsSort.MarketCapDesc, "market_cap_desc"),
            arrayOf(CoinsSort.VolumeAsc,     "volume_asc"),
            arrayOf(CoinsSort.VolumeDesc,    "volume_desc"),
            arrayOf(CoinsSort.IdAsc,         "id_asc"),
            arrayOf(CoinsSort.IdDesc,        "id_desc"),
            // client-only sorts -> null
            arrayOf(CoinsSort.PriceAsc,      null),
            arrayOf(CoinsSort.PriceDesc,     null),
            arrayOf(CoinsSort.Change24hAsc,  null),
            arrayOf(CoinsSort.Change24hDesc, null),
        )
    }

    @Test fun `maps correctly`() {
        assertThat(input.toApiOrderParamOrNull()).isEqualTo(expected)
    }
}
