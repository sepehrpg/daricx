package com.example.network.datasource.coins

import com.example.model.sort.CoinTickersOrder
import com.example.model.sort.CoinsSort
import com.example.model.sort.DexPairFormat
import com.example.network.api.getCoinDetailKtor
import com.example.network.api.getCoinHistoricalChartKtor
import com.example.network.api.getCoinHistoricalChartWithTimeRangeKtor
import com.example.network.api.getCoinHistoricalDataByIdKtor
import com.example.network.api.getCoinMarketsKtor
import com.example.network.api.getCoinOHLCChartCandleKtor
import com.example.network.api.getCoinTickersByIdKtor
import com.example.network.model.coins.CoinDetailsDto
import com.example.network.model.coins.CoinHistoricalChartDto
import com.example.network.model.coins.CoinHistoricalDataDto
import com.example.network.model.coins.CoinOHLCChartCandleDto
import com.example.network.model.coins.CoinTickersDto
import com.example.network.model.coins.CoinsListDto
import com.example.network.options.toApiOrderParam
import com.example.network.options.toApiOrderParamOrNull
import com.example.network.options.toDomain
import io.ktor.client.HttpClient
import javax.inject.Inject

/**
 * Ktor-based implementation of [CoinsDataSource].
 *
 * This replaces Retrofit calls with HttpClient + Ktor extensions,
 * while keeping the same public API for the data layer.
 */
class CoinsDataSourceImpl @Inject constructor(
    private val httpClient: HttpClient,
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
        return httpClient.getCoinMarketsKtor(
            vsCurrency = vsCurrency,
            ids = ids,
            // names, symbols, includeTokens, category are currently not used in your DS API:
            names = null,
            symbols = null,
            includeTokens = null,
            category = null,
            order = order?.toApiOrderParamOrNull(),
            perPage = perPage,
            page = page,
            sparkline = sparkline,
            priceChangePercentage = priceChangePercentage,
            locale = locale,
            precision = precision
        )

        // DEPRECATED (Retrofit)
        // return coinsApi.getCoinMarkets(...)
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
        return httpClient.getCoinDetailKtor(
            id = id,
            localization = localization,
            tickers = tickers,
            marketData = marketData,
            communityData = communityData,
            developerData = developerData,
            sparkline = sparkline,
            dexPairFormat = dexPairFormat.toDomain(), // map DexPairFormat → API string
        )

        // DEPRECATED (Retrofit)
        // return coinsApi.getCoinDetail(...)
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
        return httpClient.getCoinTickersByIdKtor(
            id = id,
            exchangeIds = exchangeIds,
            includeExchangeLogo = includeExchangeLogo,
            depth = depth,
            dexPairFormat = dexPairFormat?.toDomain(),
            page = page,
            order = order?.toApiOrderParam(),
        )

        // DEPRECATED (Retrofit)
        // return coinsApi.getCoinTickersById(...)
    }

    override suspend fun getCoinHistoricalDataById(
        id: String,
        date: String,
        localization: Boolean
    ): CoinHistoricalDataDto {
        return httpClient.getCoinHistoricalDataByIdKtor(
            id = id,
            date = date,
            localization = localization,
        )

        // DEPRECATED (Retrofit)
        // return coinsApi.coinHistoricalDataByID(...)
    }

    override suspend fun getCoinHistoricalChart(
        id: String,
        vsCurrency: String,
        days: String,
        interval: String?,
        precision: String?
    ): CoinHistoricalChartDto {
        return httpClient.getCoinHistoricalChartKtor(
            id = id,
            vsCurrency = vsCurrency,
            days = days,
            interval = interval,
            precision = precision,
        )

        // DEPRECATED (Retrofit)
        // return coinsApi.coinHistoricalChart(...)
    }

    override suspend fun getCoinHistoricalChartWithTimeRange(
        id: String,
        vsCurrency: String,
        from: Long,
        to: Long,
        precision: String?
    ): CoinHistoricalChartDto {
        return httpClient.getCoinHistoricalChartWithTimeRangeKtor(
            id = id,
            vsCurrency = vsCurrency,
            from = from,
            to = to,
            precision = precision,
        )

        // DEPRECATED (Retrofit)
        // return coinsApi.coinHistoricalChartWithTimeRange(...)
    }

    override suspend fun getCoinOHLCChartCandle(
        id: String,
        vsCurrency: String,
        days: String,
        precision: String?
    ): CoinOHLCChartCandleDto {
        return httpClient.getCoinOHLCChartCandleKtor(
            id = id,
            vsCurrency = vsCurrency,
            days = days,
            precision = precision,
        )

        // DEPRECATED (Retrofit)
        // return coinsApi.coinOHLCChartCandle(...)
    }
}
