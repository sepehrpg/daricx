package com.example.model


/**
 * Domain model for Global DeFi Market Data (CoinGecko API).
 */
data class GlobalDeFiMarketData(
    /** Total DeFi Market Cap (in USD) */
    val defiMarketCap: String?,

    /** Ethereum Market Cap (in USD) */
    val ethMarketCap: String?,

    /** Ratio: DeFi Market Cap / ETH Market Cap */
    val defiToEthRatio: String?,

    /** 24h Trading Volume (in USD) */
    val tradingVolume24h: String?,

    /** DeFi dominance (%) vs total market */
    val defiDominance: String?,

    /** Top DeFi coin name */
    val topCoinName: String?,

    /** Top DeFi coin dominance in DeFi market */
    val topCoinDefiDominance: Double?
)
