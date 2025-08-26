package com.example.network.api

import com.example.network.model.CoinMarketsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface Coins {




    /**
     * Fetches a list of coins with their market data.
     *
     * @param vsCurrency The target currency of market data (e.g., "usd", "eur").
     * @param ids A comma-separated string of coin ids to filter by.
     * @param names A comma-separated string of coin names to filter by.
     * @param symbols A comma-separated string of coin symbols to filter by.
     * @param includeTokens Specifies whether to include all matching tokens for symbol lookups.
     * @param category Filter by coin category.
     * @param order The sort order for the results. Default is market_cap_desc.
     * @param perPage The number of results per page (valid values: 1-250).
     * @param page The page number to retrieve.
     * @param sparkline Include sparkline 7-day data in the response.
     * @param priceChangePercentage Include price change percentage timeframes.
     * @param locale The language for the response data.
     * @param precision The number of decimal places for currency price values.
     * @return A [CoinMarketsDto] which is a list of coin market data.
     */
    @GET("coins/markets")
    suspend fun getCoinMarkets(
        @Query("vs_currency") vsCurrency: String,
        @Query("ids") ids: String? = null,
        @Query("names") names: String? = null,
        @Query("symbols") symbols: String? = null,
        @Query("include_tokens") includeTokens: String? = null,
        @Query("category") category: String? = null,
        @Query("order") order: String? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null,
        @Query("sparkline") sparkline: Boolean? = null,
        @Query("price_change_percentage") priceChangePercentage: String? = null,
        @Query("locale") locale: String? = null,
        @Query("precision") precision: String? = null
    ): CoinMarketsDto

}

