package com.example.network.api


import com.example.network.model.CategoriesListDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter


/**
 * Ktor extension for "GET /coins/categories".
 *
 * This replaces the Retrofit-based Categories interface for this endpoint.
 */
suspend fun HttpClient.getCoinCategoriesKtor(
    order: String? = null,
): CategoriesListDto {
    return get("coins/categories") {
        // "order" is optional, so only add the query parameter when non-null.
        if (order != null) {
            parameter("order", order)
        }
    }.body()
}
