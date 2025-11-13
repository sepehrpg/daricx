package com.example.network.api


import com.example.network.model.nfts.NftDetailsDto
import com.example.network.model.nfts.NftsListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Nfts {


    /**
     * NFTs List (ID Map)
     * https://api.coingecko.com/api/v3/nfts/list
     */
    @GET("nfts/list")
    suspend fun getNftsList(
        @Query("order") order: String? = null,
        @Query("per_page") perPage: Int? = null, // 1..250
        @Query("page") page: Int? = null
    ): NftsListDto

    //https://api.coingecko.com/api/v3/nfts/{id}
    @GET("nfts/{id}")
    suspend fun getNftById(
        @Path("id") id: String,
    ): NftDetailsDto



}
