package com.example.model.sort



/**
 * Domain-level sort for /coins/markets.
 * NOTE: Price/Change24h are client-only (not server-supported).
 */
enum class CoinsSort {
    MarketCapAsc,
    MarketCapDesc,
    VolumeAsc,
    VolumeDesc,
    IdAsc,
    IdDesc,
    // client-only:
    PriceAsc,
    PriceDesc,
    Change24hAsc,
    Change24hDesc
}

/**
 * Maps UI-level [SortOption] to domain-level [CoinsSort].
 * Returns null for client-only sorts (Price/Change24h).
 */
fun SortOption.toCoinsSortOrNull(): CoinsSort? = when (sortKey) {
    SortKey.RANK ->
        if (sortOrder == SortOrder.ASC) CoinsSort.MarketCapDesc else CoinsSort.MarketCapAsc
    SortKey.MARKET_CAP ->
        if (sortOrder == SortOrder.ASC) CoinsSort.MarketCapAsc else CoinsSort.MarketCapDesc
    SortKey.VOLUME ->
        if (sortOrder == SortOrder.ASC) CoinsSort.VolumeAsc else CoinsSort.VolumeDesc
    SortKey.ID ->
        if (sortOrder == SortOrder.ASC) CoinsSort.IdAsc else CoinsSort.IdDesc
    SortKey.PRICE,
    SortKey.CHANGE_24H -> null
}

fun SortOption.isServerSupported(): Boolean = toCoinsSortOrNull() != null





