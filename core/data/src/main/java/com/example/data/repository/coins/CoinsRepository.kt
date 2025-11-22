package com.example.data.repository.coins

import androidx.paging.PagingData
import com.example.common.result.AppResult
import com.example.model.coins.CoinDetails
import com.example.model.coins.CoinHistoricalChart
import com.example.model.coins.CoinHistoricalData
import com.example.model.coins.CoinOHLCChartCandle
import com.example.model.coins.CoinTickers
import com.example.model.coins.Coins
import com.example.model.coins.FavoriteCoin
import com.example.model.sort.CoinTickersOrder
import com.example.model.sort.CoinsSort
import com.example.model.sort.DexPairFormat
import kotlinx.coroutines.flow.Flow

interface CoinsRepository {
    fun getCoinMarketsPaged(
        vsCurrency: String,
        ids: String? = null,
        pageSize: Int,
        order: CoinsSort? = null,
        sparkline: Boolean? = true,
        priceChangePercentage: String? = null,
        pageTransform: ((List<Coins>) -> List<Coins>)? = null
    ): Flow<PagingData<Coins>>

    suspend fun getCoinDetail(
        id: String,
        localization: Boolean? = true,
        tickers: Boolean? = true,
        marketData: Boolean? = true,
        communityData: Boolean? = true,
        developerData: Boolean? = true,
        sparkline: Boolean? = false,
        dexPairFormat: DexPairFormat = DexPairFormat.CONTRACT_ADDRESS
    ): Flow<AppResult<CoinDetails>>


    fun getCoinTickersPaged(
        id: String,
        pageSize: Int = 100,
        exchangeIds: String? = null,
        includeExchangeLogo: Boolean? = true,
        depth: Boolean? = null,
        dexPairFormat: DexPairFormat? = null,
        order: CoinTickersOrder? = null,
        pageTransform: ((List<CoinTickers.Ticker>) -> List<CoinTickers.Ticker>)? = null
    ): Flow<PagingData<CoinTickers.Ticker>>

    suspend fun getCoinHistoricalDataById(
        id: String,
        date: String,
        localization: Boolean = true
    ): Flow<AppResult<CoinHistoricalData>>

    suspend fun getCoinHistoricalChart(
        id: String,
        vsCurrency: String,
        days: String,
        interval: String? = null,
        precision: String? = null
    ): Flow<AppResult<CoinHistoricalChart>>

    suspend fun getCoinHistoricalChartWithTimeRange(
        id: String,
        vsCurrency: String,
        from: Long,
        to: Long,
        precision: String? = null
    ): Flow<AppResult<CoinHistoricalChart>>

    suspend fun getCoinOHLCChartCandle(
        id: String,
        vsCurrency: String,
        days: String,
        precision: String? = null
    ): Flow<AppResult<CoinOHLCChartCandle>>




    suspend fun toggleFavorite(coin: FavoriteCoin)

    fun getFavoriteIds(): Flow<Set<String>>

}