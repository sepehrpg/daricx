package com.example.network.datasource.categories

import com.example.model.sort.CategoriesSort
import com.example.network.api.getCoinCategoriesKtor
import com.example.network.model.CategoriesListDto
import com.example.network.options.toApiOrderParam
import io.ktor.client.HttpClient
import org.koin.core.annotation.Single

@Single(binds = [CategoriesDataSource::class])
class CategoriesDataSourceImpl (
    private val httpClient: HttpClient,
) : CategoriesDataSource {

    override suspend fun getCategories(order: CategoriesSort?): CategoriesListDto {
        val orderParam = order?.toApiOrderParam()
        return httpClient.getCoinCategoriesKtor(orderParam)
        // DEPRECATED (Retrofit) return api.getCoinCategories(order = orderParam)
    }
}