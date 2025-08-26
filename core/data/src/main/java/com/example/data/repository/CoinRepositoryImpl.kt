package com.example.data.repository

import com.example.model.CoinMarket
import com.example.network.datasource.RemoteCoinsDataSource
import com.example.network.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject



class CoinRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteCoinsDataSource
) : CoinRepository {

    /**
     * Fetches coinsMarkets from the network and emits them as a single-value Flow.
     * Includes error handling to prevent crashes.
     */
    override fun getCoinMarkets(vsCurrency:String): Flow<List<CoinMarket>> = flow {
        // 1. Fetch the raw DTO list from the network layer.
        val coinMarketsDto = remoteDataSource.getCoinMarkets(vsCurrency)

        // 2. Use the extension function to map the DTO list to a clean domain model list.
        val coinMarkets = coinMarketsDto.toDomain()

        // 3. Emit the final, clean list into the stream.
        emit(coinMarkets)
    }.catch { e ->
        // This block catches any exceptions from the network call or mapping.
        Timber.e("CoinRepositoryImpl", "Error fetching coin markets", e)
        // Emit an empty list as a fallback to prevent the app from crashing.
        emit(emptyList())
    }

}