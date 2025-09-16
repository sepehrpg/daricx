package com.example.model.sort


/**
 * Domain-level sort for Coins Markets API.
 */
enum class CoinsSort {
    MarketCapAsc,
    MarketCapDesc,
    VolumeAsc,
    VolumeDesc,
    IdAsc,
    IdDesc,
    /** client-side  */
    PriceAsc,
    PriceDesc,
    Change24hAsc,
    Change24hDesc
}


/**
 * Maps UI-level [SortSpec] to domain-level [CoinsSort].
 * Returns null if server does not support this sort (e.g., Price, Change24h).
 */
fun SortSpec.toCoinsSortOrNull(): CoinsSort? = when (column) {
    SortColumn.RANK ->
        if (direction == SortDirection.ASC) CoinsSort.IdAsc else CoinsSort.IdDesc

    SortColumn.MARKET_CAP ->
        if (direction == SortDirection.ASC) CoinsSort.MarketCapAsc else CoinsSort.MarketCapDesc

    SortColumn.VOLUME ->
        if (direction == SortDirection.ASC) CoinsSort.VolumeAsc else CoinsSort.VolumeDesc

    SortColumn.ID ->
        if (direction == SortDirection.ASC) CoinsSort.IdAsc else CoinsSort.IdDesc

    SortColumn.PRICE,
    SortColumn.CHANGE_24H -> null
}


fun SortSpec.isServerSupported(): Boolean = toCoinsSortOrNull() != null




