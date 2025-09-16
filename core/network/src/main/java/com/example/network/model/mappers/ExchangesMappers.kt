package com.example.network.model.mappers

import com.example.model.Exchanges
import com.example.network.model.ExchangesDto

/** Mapper to domain model */
fun ExchangesDto.toDomain() = Exchanges(
    country = country,
    description = description,
    hasTradingIncentive = hasTradingIncentive,
    id = id,
    image = image,
    name = name,
    tradeVolume24hBtc = tradeVolume24hBtc,
    trustScore = trustScore,
    trustScoreRank = trustScoreRank,
    url = url,
    yearEstablished = yearEstablished
)