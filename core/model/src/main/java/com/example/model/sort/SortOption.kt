package com.example.model.sort



data class SortOption(
    val sortKey: SortKey,
    val sortOrder: SortOrder
)

enum class SortKey {
    RANK,
    MARKET_CAP,
    VOLUME,
    ID,
    PRICE, // Not Supported Server Side
    CHANGE_24H // Not Supported Server Side
}
enum class SortOrder { ASC, DESC }