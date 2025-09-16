package com.example.network.datasource.categories

import com.example.network.model.CategoriesListDto

interface CategoriesDataSource {
    suspend fun getCategories(order: String? = null): CategoriesListDto
}