package com.example.data.repository.nfts


import androidx.paging.PagingData
import com.example.common.result.AppResult
import com.example.model.nfts.NftDetails
import com.example.model.nfts.Nfts
import com.example.model.sort.NftsSort
import com.example.network.model.nfts.NftDetailsDto
import kotlinx.coroutines.flow.Flow

/** Repository for NFTs list (/nfts/list). */
interface NftsRepository {
    fun getNftsPaged(
        pageSize: Int,
        order: NftsSort? = null,
        pageTransform: ((List<Nfts>) -> List<Nfts>)? = null
    ): Flow<PagingData<Nfts>>


    suspend fun getNftById(id: String): Flow<AppResult<NftDetails>>

}
