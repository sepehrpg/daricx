package com.example.data.repository.categories


import com.example.common.result.AppResult
import com.example.model.Categories
import com.example.model.sort.CategoriesSort
import kotlinx.coroutines.flow.Flow

/**
 * Repository for fetching coins categories with market data.
 */
interface CategoriesRepository {

    /**
     * @param order Optional sort option (domain-level).
     * @return A Flow emitting the list of categories.
     */
    fun getCategories(order: CategoriesSort? = null): Flow<AppResult<List<Categories>>>
}
