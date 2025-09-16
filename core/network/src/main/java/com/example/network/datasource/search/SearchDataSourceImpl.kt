package com.example.network.datasource.search


import com.example.network.api.ApiService
import com.example.network.model.SearchDto
import javax.inject.Inject

/**
 * Network-backed implementation of [SearchDataSource].
 */
class SearchDataSourceImpl @Inject constructor(
    private val api: ApiService
) : SearchDataSource {

    override suspend fun search(query: String): SearchDto {
        return api.search(query)
    }
}
