package com.example.data.repository

import androidx.paging.PagingData
import com.example.model.CoinMarket
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoinMarketsPaged(
        vsCurrency: String,
        pageSize: Int,
        order: String? = null,
        sparkline: Boolean? = true,
        priceChangePercentage: String? = null,
        pageTransform: ((List<CoinMarket>) -> List<CoinMarket>)? = null
    ): Flow<PagingData<CoinMarket>>
}