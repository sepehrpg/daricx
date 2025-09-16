package com.example.network.datasource.trending

import com.example.network.api.ApiService
import com.example.network.model.TrendingDto
import javax.inject.Inject

/**
 * Network-backed implementation of [TrendingDataSource].
 */
class TrendingDataSourceImpl @Inject constructor(
    private val api: ApiService
) : TrendingDataSource {
    override suspend fun getTrending(): TrendingDto = api.getTrending()
}