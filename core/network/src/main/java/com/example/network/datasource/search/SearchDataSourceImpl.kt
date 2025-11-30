package com.example.network.datasource.search


import com.example.network.api.searchKtor
import com.example.network.datasource.nfts.NftsDataSource
import com.example.network.model.SearchDto
import io.ktor.client.HttpClient
import org.koin.core.annotation.Single



/**
 * Network-backed implementation of [SearchDataSource] using Ktor.
 */
@Single(binds = [SearchDataSource::class])
class SearchDataSourceImpl (
    private val httpClient: HttpClient,
) : SearchDataSource {

    override suspend fun search(query: String): SearchDto {
        return httpClient.searchKtor(query)
        // DEPRECATED (Retrofit) api.search(query)
    }
}
