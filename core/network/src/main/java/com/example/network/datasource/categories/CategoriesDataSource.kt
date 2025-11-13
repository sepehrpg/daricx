package com.example.network.datasource.categories

import com.example.model.sort.CategoriesSort
import com.example.network.model.CategoriesListDto

interface CategoriesDataSource {
    /**
     * @param order Optional domain-level sort.
     */
    suspend fun getCategories(order: CategoriesSort? = null): CategoriesListDto
}