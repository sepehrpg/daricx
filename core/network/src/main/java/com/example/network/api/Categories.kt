package com.example.network.api


import com.example.network.model.CategoriesListDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * GET /coins/categories
 */
interface Categories {

    @GET("coins/categories")
    suspend fun getCoinCategories(
        /**
         * market_cap_desc|market_cap_asc|name_desc|name_asc|market_cap_change_24h_desc|market_cap_change_24h_asc
         */
        @Query("order") order: String? = null
    ): CategoriesListDto
}
