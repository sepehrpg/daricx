package com.example.network.options

import com.example.model.sort.CategoriesSort


internal fun CategoriesSort.toApiOrderParam(): String = when (this) {
    CategoriesSort.MarketCapDesc         -> "market_cap_desc"
    CategoriesSort.MarketCapAsc          -> "market_cap_asc"
    CategoriesSort.NameDesc              -> "name_desc"
    CategoriesSort.NameAsc               -> "name_asc"
    CategoriesSort.MarketCapChange24hDesc-> "market_cap_change_24h_desc"
    CategoriesSort.MarketCapChange24hAsc -> "market_cap_change_24h_asc"
}