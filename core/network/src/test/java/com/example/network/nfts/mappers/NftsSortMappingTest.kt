package com.example.network.nfts.mappers


import com.example.model.sort.NftsSort
import com.example.network.options.toApiOrderParam
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * NftsSortMappingTest
 *
 * Test Goal:
 * - Verify NftsSort -> API "order" string mapping.
 *
 * Scenarios:
 * - One parameterized case per enum value.
 */
@RunWith(Parameterized::class)
class NftsSortMappingTest(
    private val input: NftsSort,
    private val expected: String
) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: {0} -> {1}")
        fun data() = listOf(
            arrayOf(NftsSort.H24VolumeUsdAsc,      "h24_volume_usd_asc"),
            arrayOf(NftsSort.H24VolumeUsdDesc,     "h24_volume_usd_desc"),
            arrayOf(NftsSort.H24VolumeNativeAsc,   "h24_volume_native_asc"),
            arrayOf(NftsSort.H24VolumeNativeDesc,  "h24_volume_native_desc"),
            arrayOf(NftsSort.FloorPriceNativeAsc,  "floor_price_native_asc"),
            arrayOf(NftsSort.FloorPriceNativeDesc, "floor_price_native_desc"),
            arrayOf(NftsSort.MarketCapNativeAsc,   "market_cap_native_asc"),
            arrayOf(NftsSort.MarketCapNativeDesc,  "market_cap_native_desc"),
            arrayOf(NftsSort.MarketCapUsdAsc,      "market_cap_usd_asc"),
            arrayOf(NftsSort.MarketCapUsdDesc,     "market_cap_usd_desc"),
        )
    }

    @Test fun `maps enum to correct api string`() {
        assertThat(input.toApiOrderParam()).isEqualTo(expected)
    }
}
