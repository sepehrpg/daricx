package com.example.network.datasource.nfts


import com.example.model.sort.NftsSort
import com.example.network.api.ApiService
import com.example.network.model.nfts.NftDetailsDto
import com.example.network.model.nfts.NftsListDto
import com.example.network.options.toApiOrderParam
import javax.inject.Inject

class NftsDataSourceImpl @Inject constructor(
    private val api: ApiService
) : NftsDataSource {

    override suspend fun getNftsList(
        page: Int,
        perPage: Int,
        order: NftsSort?
    ): NftsListDto {
        return api.getNftsList(
            order = order?.toApiOrderParam(),
            perPage = perPage,
            page = page
        )
    }

    override suspend fun getNftById(id: String): NftDetailsDto {
        return api.getNftById(id = id)
    }
}
