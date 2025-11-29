package com.example.network.api

import com.example.network.model.SearchDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Ktor extension for:
 * GET /search
 */
suspend fun HttpClient.searchKtor(
    query: String,
): SearchDto {
    return get("search") {
        parameter("query", query)
    }.body()
}