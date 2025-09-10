package com.daricx.markets.utils

import com.example.model.MarketSortColumn
import com.example.model.SortDirection
import com.example.model.SortSpec


/** Maps UI sort to  /coins/markets 'order' param (server-supported only). */
fun mapSortSpecToServerOrderOrNull(spec: SortSpec): String? = when (spec.column) {
    MarketSortColumn.MARKET_CAP ->
        if (spec.direction == SortDirection.ASC) "market_cap_asc" else "market_cap_desc"

    // Rank = inverse of market cap sort (rank 1 = largest market cap)
    MarketSortColumn.RANK ->
        if (spec.direction == SortDirection.ASC) "market_cap_desc" else "market_cap_asc"

    MarketSortColumn.VOLUME ->
        if (spec.direction == SortDirection.ASC) "volume_asc" else "volume_desc"

    MarketSortColumn.ID ->
        if (spec.direction == SortDirection.ASC) "id_asc" else "id_desc"

    // Not supported by server for /coins/markets:
    MarketSortColumn.PRICE,
    MarketSortColumn.CHANGE_24H -> null
}

fun isServerSortSupported(spec: SortSpec): Boolean =
    mapSortSpecToServerOrderOrNull(spec) != null