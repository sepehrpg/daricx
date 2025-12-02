package com.example.data.repository.coins


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.result.AppResult
import com.example.data.di.qualifier.AppDispatcher
import com.example.data.di.qualifier.Dispatcher
import com.example.data.paging.CoinTickersPagingSource
import com.example.data.paging.CoinsPagingSource
import com.example.data.repository.categories.CategoriesRepository
import com.example.database.dao.FavoriteCoinDao
import com.example.database.model.toFavoriteEntity
import com.example.model.coins.CoinDetails
import com.example.model.coins.CoinHistoricalChart
import com.example.model.coins.CoinHistoricalData
import com.example.model.coins.CoinOHLCChartCandle
import com.example.model.coins.CoinTickers
import com.example.model.coins.Coins
import com.example.model.coins.FavoriteCoin
import com.example.model.sort.CoinTickersOrder
import com.example.model.sort.CoinsSort
import com.example.model.sort.DexPairFormat
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.errors.toAppError
import com.example.network.model.mappers.coins.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Single
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

@Single(binds = [CoinsRepository::class])
class CoinsRepositoryImpl (
    private val remoteDataSource: CoinsDataSource,
    private val favoriteCoinDao: FavoriteCoinDao,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher,
) : CoinsRepository {

    override fun getCoinMarketsPaged(
        vsCurrency: String,
        ids: String?,
        pageSize: Int,
        order: CoinsSort?,
        sparkline: Boolean?,
        priceChangePercentage: String?,
        pageTransform: ((List<Coins>) -> List<Coins>)?
    ): Flow<PagingData<Coins>> {
        val config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
        return Pager(
            config = config,
            pagingSourceFactory = {
                CoinsPagingSource(
                    remote = remoteDataSource,
                    ids = ids,
                    vsCurrency = vsCurrency,
                    perPage = pageSize,
                    order = order,
                    sparkline = sparkline,
                    priceChangePercentage = priceChangePercentage,
                    pageTransform = pageTransform
                )
            }
        ).flow.flowOn(ioDispatcher)
    }

    override suspend fun getCoinDetail(
        id: String,
        localization: Boolean?,
        tickers: Boolean?,
        marketData: Boolean?,
        communityData: Boolean?,
        developerData: Boolean?,
        sparkline: Boolean?,
        dexPairFormat: DexPairFormat
    ): Flow<AppResult<CoinDetails>>  = flow {
        emit(AppResult.Loading)
        val dto = remoteDataSource.getCoinDetail(
            id = id,
            localization = localization,
            tickers = tickers,
            marketData = marketData,
            communityData = communityData,
            developerData = developerData,
            sparkline = sparkline,
            dexPairFormat = dexPairFormat
        )

        emit(AppResult.Success(dto.toDomain()))
    }
        .catch { e ->
            if (e is CancellationException) throw e
            Timber.e(e.toString())
            emit(AppResult.Error(e.toAppError()))
        }
        .flowOn(ioDispatcher)


    override fun getCoinTickersPaged(
        id: String,
        pageSize: Int,
        exchangeIds: String?,
        includeExchangeLogo: Boolean?,
        depth: Boolean?,
        dexPairFormat: DexPairFormat?,
        order: CoinTickersOrder?,
        pageTransform: ((List<CoinTickers.Ticker>) -> List<CoinTickers.Ticker>)?
    ): Flow<PagingData<CoinTickers.Ticker>> {
        val config = PagingConfig(
            pageSize = pageSize,
            initialLoadSize = pageSize,
            prefetchDistance = 1,
            enablePlaceholders = false
        )
        return Pager(
            config = config,
            pagingSourceFactory = {
                CoinTickersPagingSource(
                    remote = remoteDataSource,
                    id = id,
                    exchangeIds = exchangeIds,
                    includeExchangeLogo = includeExchangeLogo,
                    depth = depth,
                    dexPairFormat = dexPairFormat,
                    order = order,
                    pageTransform = pageTransform
                )
            }
        ).flow.flowOn(ioDispatcher)
    }

    override suspend fun getCoinHistoricalDataById(
        id: String,
        date: String,
        localization: Boolean
    ): Flow<AppResult<CoinHistoricalData>> = flow {
        emit(AppResult.Loading)
        val dto = remoteDataSource.getCoinHistoricalDataById(
            id = id,
            date = date,
            localization = localization
        )
        emit(AppResult.Success(dto.toDomain()))
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(AppResult.Error(e.toAppError()))
    }.flowOn(ioDispatcher)

    override suspend fun getCoinHistoricalChart(
        id: String,
        vsCurrency: String,
        days: String,
        interval: String?,
        precision: String?
    ): Flow<AppResult<CoinHistoricalChart>> = flow {
        emit(AppResult.Loading)
        val dto = remoteDataSource.getCoinHistoricalChart(
            id = id,
            vsCurrency = vsCurrency,
            days = days,
            interval = interval,
            precision = precision
        )
        emit(AppResult.Success(dto.toDomain()))
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(AppResult.Error(e.toAppError()))
    }.flowOn(ioDispatcher)

    override suspend fun getCoinHistoricalChartWithTimeRange(
        id: String,
        vsCurrency: String,
        from: Long,
        to: Long,
        precision: String?
    ): Flow<AppResult<CoinHistoricalChart>> = flow {
        emit(AppResult.Loading)
        val dto = remoteDataSource.getCoinHistoricalChartWithTimeRange(
            id = id,
            vsCurrency = vsCurrency,
            from = from,
            to = to,
            precision = precision
        )
        emit(AppResult.Success(dto.toDomain()))
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(AppResult.Error(e.toAppError()))
    }.flowOn(ioDispatcher)

    override suspend fun getCoinOHLCChartCandle(
        id: String,
        vsCurrency: String,
        days: String,
        precision: String?
    ): Flow<AppResult<CoinOHLCChartCandle>> = flow {
        emit(AppResult.Loading)
        val dto = remoteDataSource.getCoinOHLCChartCandle(
            id = id,
            vsCurrency = vsCurrency,
            days = days,
            precision = precision
        )
        emit(AppResult.Success(dto.toDomain()))
    }.catch { e ->
        if (e is CancellationException) throw e
        emit(AppResult.Error(e.toAppError()))
    }.flowOn(ioDispatcher)



    override fun getFavoriteIds(): Flow<Set<String>> =
        favoriteCoinDao
            .getFavoriteIds()
            .map { it.toSet() }
            .flowOn(ioDispatcher)

    override suspend fun toggleFavorite(coin: FavoriteCoin) {
        // I think don't need to use IO thread but I try to keep the repository side-effect free (for ex:  if use operation in feature)
        withContext(ioDispatcher) {
            val isFavorite = favoriteCoinDao.isFavorite(coin.id)
            if (isFavorite) {
                favoriteCoinDao.deleteFavoriteById(coin.id)
            } else {
                favoriteCoinDao.upsertFavorite(coin.toFavoriteEntity())
            }
        }
    }


}
