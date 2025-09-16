package com.example.network.api

import com.example.network.model.SearchDto
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * GET /search
 * Search for coins, categories, exchanges and NFTs.
 */
interface Search {

    @GET("search")
    suspend fun search(
        @Query("query") query: String
    ): SearchDto
}