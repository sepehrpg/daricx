package com.example.network.model


import com.example.model.GlobalDeFiMarketData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


/**
 * DTO for Global DeFi Market Data (CoinGecko Global DeFi API).
 *
 * Example response:
 * {
 *   "data": {
 *     "defi_market_cap": "105273842288.229620442228701667",
 *     "eth_market_cap": "406184911478.5772415794509920285",
 *     "defi_to_eth_ratio": "25.91771366026773...",
 *     "trading_volume_24h": "5046503746.288261...",
 *     "defi_dominance": "3.86765030846147...",
 *     "top_coin_name": "Lido Staked Ether",
 *     "top_coin_defi_dominance": 30.589442518868
 *   }
 * }
 */
@Serializable
data class GlobalDeFiMarketDataDto(
    val data: Data?
) {
    @Serializable
    data class Data(
        /** Total DeFi Market Cap (numeric string, in USD) */
        @SerialName("defi_market_cap")
        val defiMarketCap: String?,

        /** Ethereum Market Cap (numeric string, in USD) */
        @SerialName("eth_market_cap")
        val ethMarketCap: String?,

        /** Ratio: DeFi Market Cap / ETH Market Cap (numeric string) */
        @SerialName("defi_to_eth_ratio")
        val defiToEthRatio: String?,

        /** 24h Trading Volume (numeric string, in USD) */
        @SerialName("trading_volume_24h")
        val tradingVolume24h: String?,

        /** DeFi Dominance vs Total Crypto Market (numeric string, in %) */
        @SerialName("defi_dominance")
        val defiDominance: String?,

        /** Top coin name in DeFi sector (e.g., "Lido Staked Ether") */
        @SerialName("top_coin_name")
        val topCoinName: String?,

        /** Dominance of top DeFi coin in DeFi market (already a Double) */
        @SerialName("top_coin_defi_dominance")
        val topCoinDefiDominance: Double?
    )
}


