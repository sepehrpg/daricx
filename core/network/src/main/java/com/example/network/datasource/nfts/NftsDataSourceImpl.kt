package com.example.network.datasource.nfts


import com.example.model.sort.NftsSort
import com.example.network.api.ApiService
import com.example.network.api.getNftByIdKtor
import com.example.network.api.getNftsListKtor
import com.example.network.model.nfts.NftDetailsDto
import com.example.network.model.nfts.NftsListDto
import com.example.network.options.toApiOrderParam
import io.ktor.client.HttpClient
import javax.inject.Inject

class NftsDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
) : NftsDataSource {

    override suspend fun getNftsList(
        page: Int,
        perPage: Int,
        order: NftsSort?,
    ): NftsListDto {
        val orderParam = order?.toApiOrderParam()
        return httpClient.getNftsListKtor(
            order = orderParam,
            perPage = perPage,
            page = page,
        )
        // DEPRECATED (Retrofit) api.getNftsList(...)
    }

    override suspend fun getNftById(id: String): NftDetailsDto {
        return httpClient.getNftByIdKtor(id)
        // DEPRECATED (Retrofit) api.getNftById(id)
    }
}
