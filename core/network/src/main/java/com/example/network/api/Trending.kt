package com.example.network.api

import com.example.network.model.TrendingDto
import retrofit2.http.GET

/**
 * GET /search/trending
 * Returns trending categories, coins, and NFTs.
 * No query parameters are required by this endpoint.
 */
interface Trending {

    @GET("search/trending")
    suspend fun getTrending(): TrendingDto
}