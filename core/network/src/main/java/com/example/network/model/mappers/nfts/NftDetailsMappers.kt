package com.example.network.model.mappers.nfts


import com.example.model.nfts.NftDetails
import com.example.network.model.nfts.NftDetailsDto

/** Map root DTO → Domain */
fun NftDetailsDto.toDomain(): NftDetails =
    NftDetails(
        assetPlatformId = assetPlatformId,
        ath = ath?.toDomain(),
        athChangePercentage = athChangePercentage?.toDomain(),
        athDate = athDate?.toDomain(),
        bannerImage = bannerImage,
        contractAddress = contractAddress,
        description = description,
        explorers = explorers?.map { it?.toDomain() },
        floorPrice = floorPrice?.toDomain(),
        floorPrice14dPercentageChange = floorPrice14dPercentageChange?.toDomain(),
        floorPrice1yPercentageChange = floorPrice1yPercentageChange?.toDomain(),
        floorPrice24hPercentageChange = floorPrice24hPercentageChange?.toDomain(),
        floorPrice30dPercentageChange = floorPrice30dPercentageChange?.toDomain(),
        floorPrice60dPercentageChange = floorPrice60dPercentageChange?.toDomain(),
        floorPrice7dPercentageChange = floorPrice7dPercentageChange?.toDomain(),
        floorPriceInUsd24hPercentageChange = floorPriceInUsd24hPercentageChange,
        id = id,
        image = image?.toDomain(),
        links = links?.toDomain(),
        marketCap = marketCap?.toDomain(),
        marketCap24hPercentageChange = marketCap24hPercentageChange?.toDomain(),
        marketCapRank = marketCapRank,
        name = name,
        nativeCurrency = nativeCurrency,
        nativeCurrencySymbol = nativeCurrencySymbol,
        numberOfUniqueAddresses = numberOfUniqueAddresses,
        numberOfUniqueAddresses24hPercentageChange = numberOfUniqueAddresses24hPercentageChange,
        oneDayAverageSalePrice = oneDayAverageSalePrice,
        oneDayAverageSalePrice24hPercentageChange = oneDayAverageSalePrice24hPercentageChange,
        oneDaySales = oneDaySales,
        oneDaySales24hPercentageChange = oneDaySales24hPercentageChange,
        symbol = symbol,
        totalSupply = totalSupply,
        userFavoritesCount = userFavoritesCount,
        volume24h = volume24h?.toDomain(),
        volume24hPercentageChange = volume24hPercentageChange?.toDomain(),
        volumeInUsd24hPercentageChange = volumeInUsd24hPercentageChange,
        webSlug = webSlug
    )

// ---------------------- Nested mappers ----------------------

private fun NftDetailsDto.Ath.toDomain(): NftDetails.Ath =
    NftDetails.Ath(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.AthChangePercentage.toDomain(): NftDetails.AthChangePercentage =
    NftDetails.AthChangePercentage(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.AthDate.toDomain(): NftDetails.AthDate =
    NftDetails.AthDate(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.Explorer.toDomain(): NftDetails.Explorer =
    NftDetails.Explorer(
        link = link,
        name = name
    )

private fun NftDetailsDto.FloorPrice.toDomain(): NftDetails.FloorPrice =
    NftDetails.FloorPrice(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.FloorPrice14dPercentageChange.toDomain(): NftDetails.FloorPrice14dPercentageChange =
    NftDetails.FloorPrice14dPercentageChange(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.FloorPrice1yPercentageChange.toDomain(): NftDetails.FloorPrice1yPercentageChange =
    NftDetails.FloorPrice1yPercentageChange(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.FloorPrice24hPercentageChange.toDomain(): NftDetails.FloorPrice24hPercentageChange =
    NftDetails.FloorPrice24hPercentageChange(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.FloorPrice30dPercentageChange.toDomain(): NftDetails.FloorPrice30dPercentageChange =
    NftDetails.FloorPrice30dPercentageChange(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.FloorPrice60dPercentageChange.toDomain(): NftDetails.FloorPrice60dPercentageChange =
    NftDetails.FloorPrice60dPercentageChange(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.FloorPrice7dPercentageChange.toDomain(): NftDetails.FloorPrice7dPercentageChange =
    NftDetails.FloorPrice7dPercentageChange(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.Image.toDomain(): NftDetails.Image =
    NftDetails.Image(
        small = small,
        small2x = small2x
    )

private fun NftDetailsDto.Links.toDomain(): NftDetails.Links =
    NftDetails.Links(
        discord = discord,
        homepage = homepage,
        twitter = twitter
    )

private fun NftDetailsDto.MarketCap.toDomain(): NftDetails.MarketCap =
    NftDetails.MarketCap(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.MarketCap24hPercentageChange.toDomain(): NftDetails.MarketCap24hPercentageChange =
    NftDetails.MarketCap24hPercentageChange(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.Volume24h.toDomain(): NftDetails.Volume24h =
    NftDetails.Volume24h(
        nativeCurrency = nativeCurrency,
        usd = usd
    )

private fun NftDetailsDto.Volume24hPercentageChange.toDomain(): NftDetails.Volume24hPercentageChange =
    NftDetails.Volume24hPercentageChange(
        nativeCurrency = nativeCurrency,
        usd = usd
    )
