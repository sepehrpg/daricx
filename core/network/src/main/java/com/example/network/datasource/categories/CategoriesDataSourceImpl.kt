package com.example.network.datasource.categories

import com.example.network.api.ApiService
import com.example.network.model.CategoriesListDto
import javax.inject.Inject

class CategoriesDataSourceImpl @Inject constructor(
    private val api: ApiService
) : CategoriesDataSource {

    override suspend fun getCategories(order: String?): CategoriesListDto {
        return api.getCoinCategories(order = order)
    }
}