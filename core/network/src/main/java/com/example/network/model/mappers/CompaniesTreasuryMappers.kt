package com.example.network.model.mappers

import com.example.model.CompaniesTreasury
import com.example.network.model.CompaniesTreasuryDto


/** Mapper from DTO to domain model */
fun CompaniesTreasuryDto.toDomain() = CompaniesTreasury(
    totalHoldings = totalHoldings,
    totalValueUsd = totalValueUsd,
    marketCapDominance = marketCapDominance,
    companies = companies?.map { it.toDomain() }
)
fun CompaniesTreasuryDto.Company.toDomain() = CompaniesTreasury.Company(
    name = name,
    symbol = symbol,
    country = country,
    totalHoldings = totalHoldings,
    totalEntryValueUsd = totalEntryValueUsd,
    totalCurrentValueUsd = totalCurrentValueUsd,
    percentageOfTotalSupply = percentageOfTotalSupply
)