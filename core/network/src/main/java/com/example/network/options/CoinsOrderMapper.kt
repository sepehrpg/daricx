package com.example.network.options

import com.example.model.sort.CoinsSort


/**
 * Maps domain [CoinsSort] to API query param for /coins/markets
 */
internal fun CoinsSort.toApiOrderParam(): String? = when (this) {
    CoinsSort.MarketCapAsc  -> "market_cap_asc"
    CoinsSort.MarketCapDesc -> "market_cap_desc"
    CoinsSort.VolumeAsc     -> "volume_asc"
    CoinsSort.VolumeDesc    -> "volume_desc"
    CoinsSort.IdAsc         -> "id_asc"
    CoinsSort.IdDesc        -> "id_desc"

    // Not supported server-side → return null, handled in Repository/UI
    CoinsSort.PriceAsc,
    CoinsSort.PriceDesc,
    CoinsSort.Change24hAsc,
    CoinsSort.Change24hDesc -> null
}