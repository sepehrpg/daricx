package com.example.network.datasource.nfts


import com.example.model.sort.NftsSort
import com.example.network.model.nfts.NftDetailsDto
import com.example.network.model.nfts.NftsListDto

interface NftsDataSource {
    suspend fun getNftsList(
        page: Int,
        perPage: Int,
        order: NftsSort? = null
    ): NftsListDto

    suspend fun getNftById(
        id: String
    ): NftDetailsDto
}