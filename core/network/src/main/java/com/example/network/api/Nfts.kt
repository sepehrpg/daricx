package com.example.network.api


import com.example.network.model.NftsListDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * NFTs List (ID Map)
 * https://api.coingecko.com/api/v3/nfts/list
 */
interface Nfts {
    @GET("nfts/list")
    suspend fun getNftsList(
        @Query("order") order: String? = null,
        @Query("per_page") perPage: Int? = null, // 1..250
        @Query("page") page: Int? = null
    ): NftsListDto
}
