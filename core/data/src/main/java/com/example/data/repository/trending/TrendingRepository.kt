package com.example.data.repository.trending


import com.example.common.result.AppResult
import com.example.model.Trending
import kotlinx.coroutines.flow.Flow

/**
 * Exposes trending coins, categories, and NFTs in domain model.
 */
interface TrendingRepository {
    /**
     * @return [Trending] domain model
     */
    fun getTrending(): Flow<AppResult<Trending>>
}
