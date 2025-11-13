package com.example.network.model.mappers

import com.example.model.Search
import com.example.network.model.SearchDto

/** Maps /search DTO to domain. */
fun SearchDto.toDomain() = Search(
    coins = coins?.map { it.toDomain() },
    exchanges = exchanges?.map { it.toDomain() },
    icos = icos,
    categories = categories?.map { it.toDomain() },
    nfts = nfts?.map { it.toDomain() }
)

fun SearchDto.Coin.toDomain() = Search.Coin(
    id = id,
    name = name,
    apiSymbol = apiSymbol,
    symbol = symbol,
    marketCapRank = marketCapRank,
    thumb = thumb,
    large = large
)

fun SearchDto.Exchange.toDomain() = Search.Exchange(
    id = id,
    name = name,
    marketType = marketType,
    thumb = thumb,
    large = large
)

fun SearchDto.Category.toDomain() = Search.Category(
    id = id,
    name = name
)

fun SearchDto.Nft.toDomain() = Search.Nft(
    id = id,
    name = name,
    symbol = symbol,
    thumb = thumb
)
