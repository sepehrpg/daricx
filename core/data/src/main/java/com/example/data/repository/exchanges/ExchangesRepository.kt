package com.example.data.repository.exchanges

import androidx.paging.PagingData
import com.example.common.result.AppResult
import com.example.model.exchanges.ExchangeDetail
import com.example.model.exchanges.ExchangeTickers
import com.example.model.exchanges.ExchangeVolumeChart
import com.example.model.exchanges.Exchanges
import com.example.model.sort.DexPairFormat
import com.example.model.sort.ExchangeTickersOrder
import kotlinx.coroutines.flow.Flow


interface ExchangesRepository {
    fun getExchangesPaged(
        pageSize: Int,
    ): Flow<PagingData<Exchanges>>

    fun getExchangeById(
        id: String,
        dexPairFormat: DexPairFormat,
    ): Flow<AppResult<ExchangeDetail>>


    fun getExchangeTickersPaged(
        id: String,
        pageSize: Int,
        coinIds: String? = null,
        includeExchangeLogo: Boolean? = true,
        depth: Boolean? = null,
        dexPairFormat: DexPairFormat? = null,
        order: ExchangeTickersOrder? = null,
    ): Flow<PagingData<ExchangeTickers.Ticker>>


    fun getExchangeVolumeChart(
        id: String,
        days: String,
    ): Flow<AppResult<ExchangeVolumeChart>>
}