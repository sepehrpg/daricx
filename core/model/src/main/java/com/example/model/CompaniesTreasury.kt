package com.example.model


/**
 * Domain model for Companies Public Treasury.
 */
data class CompaniesTreasury(
    /** Total BTC holdings across all companies */
    val totalHoldings: Double?,

    /** Total value of holdings in USD */
    val totalValueUsd: Double?,

    /** Market cap dominance (%) of corporate BTC holdings */
    val marketCapDominance: Double?,

    /** List of companies with BTC in treasury */
    val companies: List<Company>?
) {
    data class Company(
        /** Company name (e.g., MicroStrategy Inc.) */
        val name: String?,

        /** Stock symbol (e.g., NASDAQ:MSTR) */
        val symbol: String?,

        /** Country code (e.g., US, CA, JP) */
        val country: String?,

        /** Total BTC holdings by company */
        val totalHoldings: Double?,

        /** Entry value in USD when acquired */
        val totalEntryValueUsd: Double?,

        /** Current value in USD of holdings */
        val totalCurrentValueUsd: Double?,

        /** Percentage of BTC total supply held by this company */
        val percentageOfTotalSupply: Double?
    )
}
