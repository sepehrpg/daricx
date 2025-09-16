package com.example.network.model.mappers

import com.example.model.GlobalCryptoMarketData
import com.example.network.model.GlobalCryptoMarketDataDto


/** Mapper from DTO to domain model */
fun GlobalCryptoMarketDataDto.toDomain() = data?.toDomain()

fun GlobalCryptoMarketDataDto.Data.toDomain() = GlobalCryptoMarketData(
    activeCryptocurrencies = activeCryptocurrencies,
    endedIcos = endedIcos,
    marketCapChangePercentage24hUsd = marketCapChangePercentage24hUsd,
    marketCapPercentage = marketCapPercentage,
    markets = markets,
    ongoingIcos = ongoingIcos,
    totalMarketCap = totalMarketCap,
    totalVolume = totalVolume,
    upcomingIcos = upcomingIcos,
    updatedAt = updatedAt
)