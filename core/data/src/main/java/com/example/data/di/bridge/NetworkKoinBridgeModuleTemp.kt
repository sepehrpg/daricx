package com.example.data.di.bridge

import com.example.network.datasource.categories.CategoriesDataSource
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.datasource.companies.CompaniesTreasuryDataSource
import com.example.network.datasource.defi.GlobalDeFiDataSource
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.datasource.global.GlobalDataSource
import com.example.network.datasource.nfts.NftsDataSource
import com.example.network.datasource.search.SearchDataSource
import com.example.network.datasource.trending.TrendingDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.java.KoinJavaComponent

/**
 * Bridge module: exposes Koin-provided network data sources to Hilt.
 *
 * Repositories and ViewModels are still created by Hilt,
 * but their dependencies (DataSources) are resolved from Koin.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkKoinBridgeModuleTemp {

    @Provides
    fun provideCategoriesDataSource(): CategoriesDataSource =
        KoinJavaComponent.get(CategoriesDataSource::class.java)

    @Provides
    fun provideCoinsDataSource(): CoinsDataSource =
        KoinJavaComponent.get(CoinsDataSource::class.java)

    @Provides
    fun provideExchangesDataSource(): ExchangesDataSource =
        KoinJavaComponent.get(ExchangesDataSource::class.java)

    @Provides
    fun provideGlobalDataSource(): GlobalDataSource =
        KoinJavaComponent.get(GlobalDataSource::class.java)

    @Provides
    fun provideNftsDataSource(): NftsDataSource =
        KoinJavaComponent.get(NftsDataSource::class.java)

    @Provides
    fun provideTrendingDataSource(): TrendingDataSource =
        KoinJavaComponent.get(TrendingDataSource::class.java)

    @Provides
    fun provideGlobalDeFiDataSource(): GlobalDeFiDataSource =
        KoinJavaComponent.get(GlobalDeFiDataSource::class.java)

    @Provides
    fun provideCompaniesTreasuryDataSource(): CompaniesTreasuryDataSource =
        KoinJavaComponent.get(CompaniesTreasuryDataSource::class.java)

    @Provides
    fun provideSearchDataSource(): SearchDataSource =
        KoinJavaComponent.get(SearchDataSource::class.java)
}