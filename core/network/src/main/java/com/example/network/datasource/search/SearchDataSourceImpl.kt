package com.example.network.datasource.search


import com.example.network.api.ApiService
import com.example.network.api.searchKtor
import com.example.network.model.SearchDto
import io.ktor.client.HttpClient
import javax.inject.Inject



/**
 * Network-backed implementation of [SearchDataSource] using Ktor.
 */
class SearchDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : SearchDataSource {

    override suspend fun search(query: String): SearchDto {
        return httpClient.searchKtor(query)
        // DEPRECATED (Retrofit) api.search(query)
    }
}
