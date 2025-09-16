package com.example.network.datasource.trending
import com.example.network.model.TrendingDto


/**
 * Abstraction for /search/trending data access.
 */
interface TrendingDataSource {
    suspend fun getTrending(): TrendingDto
}