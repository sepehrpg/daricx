package com.example.network.di


import com.example.network.datasource.coins.CoinsDataSource
import com.example.network.datasource.coins.CoinsDataSourceImpl
import com.example.network.datasource.exchanges.ExchangesDataSource
import com.example.network.datasource.exchanges.ExchangesDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindCoinsDataSource(
        coinsDataSource: CoinsDataSourceImpl
    ): CoinsDataSource


    @Binds
    abstract fun bindExchangesDataSource(
        exchangeDataSource: ExchangesDataSourceImp
    ): ExchangesDataSource
}