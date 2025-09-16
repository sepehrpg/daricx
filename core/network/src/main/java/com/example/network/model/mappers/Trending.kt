package com.example.network.model.mappers

import com.example.model.Trending
import com.example.network.model.TrendingDto

fun TrendingDto.toDomain() = Trending(
    categories = categories?.map { it.toDomain() },
    coins = coins?.map { it.toDomain() },
    nfts = nfts?.map { it.toDomain() }
)

// ------------------ Categories ------------------
fun TrendingDto.Category.toDomain() = Trending.Category(
    coinsCount = coinsCount,
    data = data?.toDomain(),
    id = id,
    marketCap1hChange = marketCap1hChange,
    name = name,
    slug = slug
)

fun TrendingDto.Category.Data.toDomain() = Trending.Category.Data(
    marketCap = marketCap,
    marketCapBtc = marketCapBtc,
    marketCapChangePercentage24h = marketCapChangePercentage24h,
    sparkline = sparkline,
    totalVolume = totalVolume,
    totalVolumeBtc = totalVolumeBtc
)

// ------------------ Coins ------------------
fun TrendingDto.Coin.toDomain() = Trending.Coin(
    item = item?.toDomain()
)

fun TrendingDto.Coin.Item.toDomain() = Trending.Coin.Item(
    coinId = coinId,
    data = data?.toDomain(),
    id = id,
    large = large,
    marketCapRank = marketCapRank,
    name = name,
    priceBtc = priceBtc,
    score = score,
    slug = slug,
    small = small,
    symbol = symbol,
    thumb = thumb
)

fun TrendingDto.Coin.Item.Data.toDomain() = Trending.Coin.Item.Data(
    content = content?.toDomain(),
    marketCap = marketCap,
    marketCapBtc = marketCapBtc,
    price = price,
    priceBtc = priceBtc,
    priceChangePercentage24h = priceChangePercentage24h,
    sparkline = sparkline,
    totalVolume = totalVolume,
    totalVolumeBtc = totalVolumeBtc
)

fun TrendingDto.Coin.Item.Data.Content.toDomain() = Trending.Coin.Item.Data.Content(
    description = description,
    title = title
)

// ------------------ NFTs ------------------
fun TrendingDto.Nft.toDomain() = Trending.Nft(
    data = data?.toDomain(),
    floorPrice24hPercentageChange = floorPrice24hPercentageChange,
    floorPriceInNativeCurrency = floorPriceInNativeCurrency,
    id = id,
    name = name,
    nativeCurrencySymbol = nativeCurrencySymbol,
    nftContractId = nftContractId,
    symbol = symbol,
    thumb = thumb
)

fun TrendingDto.Nft.Data.toDomain() = Trending.Nft.Data(
    content = content?.toDomain(),
    floorPrice = floorPrice,
    floorPriceInUsd24hPercentageChange = floorPriceInUsd24hPercentageChange,
    h24AverageSalePrice = h24AverageSalePrice,
    h24Volume = h24Volume,
    sparkline = sparkline
)

fun TrendingDto.Nft.Data.Content.toDomain() = Trending.Nft.Data.Content(
    description = description,
    title = title
)