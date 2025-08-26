package com.example.data.repository

import com.example.model.CoinMarket
import kotlinx.coroutines.flow.Flow


interface CoinRepository {
    fun getCoinMarkets(vsCurrency:String): Flow<List<CoinMarket>>
}