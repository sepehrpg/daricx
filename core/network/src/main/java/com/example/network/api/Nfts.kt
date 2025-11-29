package com.example.network.api


import com.example.network.model.nfts.NftDetailsDto
import com.example.network.model.nfts.NftsListDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter


/**
 * Ktor extension for:
 * GET /nfts/list
 */
suspend fun HttpClient.getNftsListKtor(
    order: String? = null,
    perPage: Int? = null,  // 1..250
    page: Int? = null,
): NftsListDto {
    return get("nfts/list") {
        if (order != null) {
            parameter("order", order)
        }
        if (perPage != null) {
            parameter("per_page", perPage)
        }
        if (page != null) {
            parameter("page", page)
        }
    }.body()
}

/**
 * Ktor extension for:
 * GET /nfts/{id}
 */
suspend fun HttpClient.getNftByIdKtor(
    id: String,
): NftDetailsDto {
    return get("nfts/$id").body()
}