package com.example.model.sort



data class SortSpec(
    val column: SortColumn,
    val direction: SortDirection
)

enum class SortColumn {
    RANK,
    MARKET_CAP,
    VOLUME,
    ID,
    PRICE, // Not Supported Server Side
    CHANGE_24H // Not Supported Server Side
}
enum class SortDirection { ASC, DESC }