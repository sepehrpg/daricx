package com.example.data.repository.nfts


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.result.AppResult
import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.data.paging.NftsPagingSource
import com.example.model.nfts.NftDetails
import com.example.model.nfts.Nfts
import com.example.model.sort.NftsSort
import com.example.network.datasource.nfts.NftsDataSource
import com.example.network.errors.toAppError
import com.example.network.model.mappers.nfts.toDomain
import com.example.network.model.nfts.NftDetailsDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/** Network-backed implementation of [NftsRepository]. */
class NftsRepositoryImpl @Inject constructor(
    private val remoteDataSource: NftsDataSource,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : NftsRepository {

    override fun getNftsPaged(
        pageSize: Int,
        order: NftsSort?,
        pageTransform: ((List<Nfts>) -> List<Nfts>)?
    ): Flow<PagingData<Nfts>> {
        val config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
        return Pager(
            config = config,
            pagingSourceFactory = {
                NftsPagingSource(
                    remote = remoteDataSource,
                    perPage = pageSize,
                    order = order,
                    pageTransform = pageTransform
                )
            }
        ).flow.flowOn(ioDispatcher)
    }

    override suspend fun getNftById(id: String): Flow<AppResult<NftDetails>> =
        flow {
            emit(AppResult.Loading)
            val dto = remoteDataSource.getNftById(id)
            emit(AppResult.Success(dto.toDomain()))
        }.catch { e ->
            emit(AppResult.Error(e.toAppError()))
        }.flowOn(ioDispatcher)
}
