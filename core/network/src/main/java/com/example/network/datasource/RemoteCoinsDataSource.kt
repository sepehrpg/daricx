package com.example.network.datasource

import com.example.network.model.CoinMarketsDto

interface RemoteCoinsDataSource {

    suspend fun getCoinMarkets(vsCurrency: String,): CoinMarketsDto

}