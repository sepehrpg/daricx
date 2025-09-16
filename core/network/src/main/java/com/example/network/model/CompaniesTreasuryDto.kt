package com.example.network.model


import com.example.model.CompaniesTreasury
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO for Companies Public Treasury (CoinGecko API).
 *
 * Example response:
 * {
 *   "total_holdings": 264136,
 *   "total_value_usd": 18403306939.1513,
 *   "market_cap_dominance": 1.34,
 *   "companies": [ ... ]
 * }
 */
@Serializable
data class CompaniesTreasuryDto(
    /** Total BTC holdings across all companies */
    @SerialName("total_holdings")
    val totalHoldings: Double?,

    /** Total value of holdings in USD */
    @SerialName("total_value_usd")
    val totalValueUsd: Double?,

    /** Market cap dominance (%) of corporate BTC holdings */
    @SerialName("market_cap_dominance")
    val marketCapDominance: Double?,

    /** List of companies with BTC in treasury */
    val companies: List<Company>?
) {
    @Serializable
    data class Company(
        /** Company name (e.g., MicroStrategy Inc.) */
        val name: String?,

        /** Stock symbol (e.g., NASDAQ:MSTR) */
        val symbol: String?,

        /** Country code (e.g., US, CA, JP) */
        val country: String?,

        /** Total BTC holdings by company */
        @SerialName("total_holdings")
        val totalHoldings: Double?,

        /** Entry value in USD when acquired */
        @SerialName("total_entry_value_usd")
        val totalEntryValueUsd: Double?,

        /** Current value in USD of holdings */
        @SerialName("total_current_value_usd")
        val totalCurrentValueUsd: Double?,

        /** Percentage of BTC total supply held by this company */
        @SerialName("percentage_of_total_supply")
        val percentageOfTotalSupply: Double?
    )
}



