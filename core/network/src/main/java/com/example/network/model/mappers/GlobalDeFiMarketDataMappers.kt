package com.example.network.model.mappers

import com.example.model.GlobalDeFiMarketData
import com.example.network.model.GlobalDeFiMarketDataDto


/** Mapper from DTO to domain model */
fun GlobalDeFiMarketDataDto.toDomain() = data?.toDomain()

fun GlobalDeFiMarketDataDto.Data.toDomain() = GlobalDeFiMarketData(
    defiMarketCap = defiMarketCap,
    ethMarketCap = ethMarketCap,
    defiToEthRatio = defiToEthRatio,
    tradingVolume24h = tradingVolume24h,
    defiDominance = defiDominance,
    topCoinName = topCoinName,
    topCoinDefiDominance = topCoinDefiDominance
)