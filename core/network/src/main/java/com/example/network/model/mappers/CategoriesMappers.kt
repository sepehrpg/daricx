package com.example.network.model.mappers

import com.example.model.Categories
import com.example.network.model.CategoriesDto
import com.example.network.model.CategoriesListDto

/** Mapper from DTO to domain model */
fun CategoriesDto.toDomain() = Categories(
    id = id,
    name = name,
    marketCap = marketCap,
    marketCapChange24h = marketCapChange24h,
    volume24h = volume24h,
    top3Coins = top3Coins,
    top3CoinsId = top3CoinsId,
    content = content,
    updatedAt = updatedAt
)

/** Mapper: List<DTO> -> List<Domain> */
fun CategoriesListDto.toDomain(): List<Categories> = map { it.toDomain() }