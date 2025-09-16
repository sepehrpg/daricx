package com.example.network.datasource.search

import com.example.network.model.SearchDto


/**
 * Abstraction for /search.
 */
interface SearchDataSource {
    suspend fun search(query: String): SearchDto
}