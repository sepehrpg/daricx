package com.example.network.di


import com.example.network.datasource.categories.CategoriesDataSource
import com.example.network.datasource.categories.CategoriesDataSourceImpl
import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.datasource.coins.CoinsDataSourceImpl
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.datasource.exchanges.ExchangesDataSourceImpl
import com.example.network.datasource.companies.CompaniesTreasuryDataSource
import com.example.network.datasource.companies.CompaniesTreasuryDataSourceImpl
import com.example.network.datasource.global.GlobalDataSource
import com.example.network.datasource.global.GlobalDataSourceImpl
import com.example.network.datasource.defi.GlobalDeFiDataSource
import com.example.network.datasource.defi.GlobalDeFiDataSourceImpl
import com.example.network.datasource.nfts.NftsDataSource
import com.example.network.datasource.nfts.NftsDataSourceImpl
import com.example.network.datasource.search.SearchDataSource
import com.example.network.datasource.search.SearchDataSourceImpl
import com.example.network.datasource.trending.TrendingDataSource
import com.example.network.datasource.trending.TrendingDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


/**
 * Binds data sources to their implementations.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindCoinsDataSource(impl: CoinsDataSourceImpl): CoinsDataSource

    @Binds
    abstract fun bindExchangesDataSource(impl: ExchangesDataSourceImpl): ExchangesDataSource

    @Binds
    abstract fun bindNftsDataSource(impl: NftsDataSourceImpl): NftsDataSource

    @Binds
    abstract fun bindCategoriesDataSource(categoriesDataSource: CategoriesDataSourceImpl): CategoriesDataSource

    @Binds
    abstract fun bindTrendingDataSource(impl: TrendingDataSourceImpl): TrendingDataSource

    @Binds
    abstract fun bindGlobalDataSource(impl: GlobalDataSourceImpl): GlobalDataSource

    @Binds
    abstract fun bindGlobalDeFiDataSource(impl: GlobalDeFiDataSourceImpl): GlobalDeFiDataSource

    @Binds
    abstract fun bindCompaniesTreasuryDataSource(impl: CompaniesTreasuryDataSourceImpl): CompaniesTreasuryDataSource

    @Binds abstract fun bindSearchDataSource(impl: SearchDataSourceImpl): SearchDataSource
}


