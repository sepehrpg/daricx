package com.example.network.datasource.categories

import com.example.model.sort.CategoriesSort
import com.example.network.api.ApiService
import com.example.network.model.CategoriesListDto
import com.example.network.options.toApiOrderParam
import javax.inject.Inject

class CategoriesDataSourceImpl @Inject constructor(
    private val api: ApiService
) : CategoriesDataSource {

    override suspend fun getCategories(order: CategoriesSort?): CategoriesListDto {
        val orderParam = order?.toApiOrderParam()
        return api.getCoinCategories(order = orderParam)
    }
}