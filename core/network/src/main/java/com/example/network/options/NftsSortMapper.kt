package com.example.network.options

import com.example.model.sort.NftsSort

/**
 * Maps domain sort to CoinGecko query param for /nfts/list
 */
internal fun NftsSort.toApiOrderParam(): String = when (this) {
    NftsSort.H24VolumeUsdAsc      -> "h24_volume_usd_asc"
    NftsSort.H24VolumeUsdDesc     -> "h24_volume_usd_desc"
    NftsSort.H24VolumeNativeAsc   -> "h24_volume_native_asc"
    NftsSort.H24VolumeNativeDesc  -> "h24_volume_native_desc"
    NftsSort.FloorPriceNativeAsc  -> "floor_price_native_asc"
    NftsSort.FloorPriceNativeDesc -> "floor_price_native_desc"
    NftsSort.MarketCapNativeAsc   -> "market_cap_native_asc"
    NftsSort.MarketCapNativeDesc  -> "market_cap_native_desc"
    NftsSort.MarketCapUsdAsc      -> "market_cap_usd_asc"
    NftsSort.MarketCapUsdDesc     -> "market_cap_usd_desc"
}
