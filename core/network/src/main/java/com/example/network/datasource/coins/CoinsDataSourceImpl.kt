package com.example.network.datasource.coins

import com.example.model.sort.CoinTickersOrder
import com.example.model.sort.CoinsSort
import com.example.model.sort.DexPairFormat
import com.example.network.api.ApiService
import com.example.network.model.coins.CoinDetailsDto
import com.example.network.model.coins.CoinHistoricalChartDto
import com.example.network.model.coins.CoinHistoricalDataDto
import com.example.network.model.coins.CoinOHLCChartCandleDto
import com.example.network.model.coins.CoinTickersDto
import com.example.network.model.coins.CoinsListDto
import com.example.network.options.toApiOrderParam
import com.example.network.options.toApiOrderParamOrNull
import com.example.network.options.toDomain
import javax.inject.Inject


class CoinsDataSourceImpl @Inject constructor(
    private val coinsApi: ApiService
) : CoinsDataSource {

    override suspend fun getCoinMarkets(
        vsCurrency: String,
        page: Int,
        perPage: Int,
        ids: String?,
        order: CoinsSort?,
        sparkline: Boolean?,
        priceChangePercentage: String?,
        locale: String?,
        precision: String?
    ): CoinsListDto {
        return coinsApi.getCoinMarkets(
            vsCurrency = vsCurrency,
            page = page,
            ids = ids,
            perPage = perPage,
            order = order?.toApiOrderParamOrNull (),
            sparkline = sparkline,
            priceChangePercentage = priceChangePercentage,
            locale = locale,
            precision = precision
        )
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
    ): CoinDetailsDto {
        return coinsApi.getCoinDetail(
            id = id,
            localization = localization,
            tickers = tickers,
            marketData = marketData,
            communityData = communityData,
            developerData = developerData,
            sparkline = sparkline,
            dexPairFormat = dexPairFormat.toDomain()
        )
    }


    override suspend fun getCoinTickersById(
        id: String,
        exchangeIds: String?,
        includeExchangeLogo: Boolean?,
        depth: Boolean?,
        dexPairFormat: DexPairFormat?,
        page: Int?,
        order: CoinTickersOrder?
    ): CoinTickersDto {
        return coinsApi.getCoinTickersById(
            ids = id,
            exchangeIds = exchangeIds,
            includeExchangeLogo = includeExchangeLogo,
            depth = depth,
            dexPairFormat = dexPairFormat?.toDomain(),
            page = page,
            order = order?.toApiOrderParam()
        )
    }

    override suspend fun getCoinHistoricalDataById(
        id: String,
        date: String,
        localization: Boolean
    ): CoinHistoricalDataDto {
        return coinsApi.coinHistoricalDataByID(
            id = id,
            date = date,
            localization = localization
        )
    }

    override suspend fun getCoinHistoricalChart(
        id: String,
        vsCurrency: String,
        days: String,
        interval: String?,
        precision: String?
    ): CoinHistoricalChartDto {
        return coinsApi.coinHistoricalChart(
            id = id,
            vsCurrency = vsCurrency,
            days = days,
            interval = interval,
            precision = precision
        )
    }

    override suspend fun getCoinHistoricalChartWithTimeRange(
        id: String,
        vsCurrency: String,
        from: Long,
        to: Long,
        precision: String?
    ): CoinHistoricalChartDto {
        return coinsApi.coinHistoricalChartWithTimeRange(
            id = id,
            vsCurrency = vsCurrency,
            from = from,
            to = to,
            precision = precision
        )
    }

    override suspend fun getCoinOHLCChartCandle(
        id: String,
        vsCurrency: String,
        days: String,
        precision: String?
    ): CoinOHLCChartCandleDto {
        return coinsApi.coinOHLCChartCandle(
            id = id,
            vsCurrency = vsCurrency,
            days = days,
            precision = precision
        )
    }
}