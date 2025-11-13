package com.example.network.options

import com.example.model.sort.CoinsSort


// if use this override fun toString(): String = value in enum class don't need to this mapper
/**
 * Maps domain coins sort to /coins/markets 'order' query param.  [CoinsSort]
 * Returns null for client-only sorts (Price/Change24h).
 */
fun CoinsSort.toApiOrderParamOrNull(): String? = when (this) {
    CoinsSort.MarketCapAsc  -> "market_cap_asc"
    CoinsSort.MarketCapDesc -> "market_cap_desc"
    CoinsSort.VolumeAsc     -> "volume_asc"
    CoinsSort.VolumeDesc    -> "volume_desc"
    CoinsSort.IdAsc         -> "id_asc"
    CoinsSort.IdDesc        -> "id_desc"
    CoinsSort.PriceAsc,
    CoinsSort.PriceDesc,
    CoinsSort.Change24hAsc,
    CoinsSort.Change24hDesc -> null
}