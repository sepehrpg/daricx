package com.example.data.nfts.m1

import com.google.common.truth.Truth.assertThat
import com.example.model.sort.NftsSort
import com.example.network.options.toApiOrderParam
import org.junit.Test

/**
 * Sort Enum Mapping Tests.
 *
 * Test Goal:
 * - Ensure every [NftsSort] enum value maps to the correct API order parameter string.
 *
 * Scenarios Covered:
 * 1) One-to-one coverage for all enum values (regression safety for future additions).
 */
class NftsSortMappingTest {

    @Test
    fun `toApiOrderParam covers all enum values`() {
        val expectations = mapOf(
            NftsSort.H24VolumeUsdAsc      to "h24_volume_usd_asc",
            NftsSort.H24VolumeUsdDesc     to "h24_volume_usd_desc",
            NftsSort.H24VolumeNativeAsc   to "h24_volume_native_asc",
            NftsSort.H24VolumeNativeDesc  to "h24_volume_native_desc",
            NftsSort.FloorPriceNativeAsc  to "floor_price_native_asc",
            NftsSort.FloorPriceNativeDesc to "floor_price_native_desc",
            NftsSort.MarketCapNativeAsc   to "market_cap_native_asc",
            NftsSort.MarketCapNativeDesc  to "market_cap_native_desc",
            NftsSort.MarketCapUsdAsc      to "market_cap_usd_asc",
            NftsSort.MarketCapUsdDesc     to "market_cap_usd_desc",
        )

        expectations.forEach { (sort, expected) ->
            assertThat(sort.toApiOrderParam()).isEqualTo(expected)
        }
    }
}
