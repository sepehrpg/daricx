package com.example.data.repository.search


import com.example.model.Search

/**
 * Repository for /search endpoint.
 * Provides a unified domain-level result for coins, exchanges, categories, nfts, and icos.
 */
interface SearchRepository {
    /**
     * Performs a search query across coins, exchanges, categories, and NFTs.
     *
     * @param query Free-text search string (e.g. "ethereum")
     * @return [Search] domain object
     */
    suspend fun search(query: String): Search
}
