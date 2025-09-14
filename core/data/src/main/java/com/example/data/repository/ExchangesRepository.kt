package com.example.data.repository

import androidx.paging.PagingData
import com.example.model.CoinMarket
import com.example.model.Exchange
import kotlinx.coroutines.flow.Flow


interface ExchangesRepository {
    fun getExchangesPaged(
        pageSize: Int,
        page: Int,
    ): Flow<PagingData<Exchange>>
}