package com.example.data.di

import com.example.data.repository.categories.CategoriesRepository
import com.example.data.repository.categories.CategoriesRepositoryImpl
import com.example.data.repository.coins.CoinsRepository
import com.example.data.repository.coins.CoinsRepositoryImpl
import com.example.data.repository.companies.CompaniesRepository
import com.example.data.repository.companies.CompaniesRepositoryImpl
import com.example.data.repository.defi.DeFiRepository
import com.example.data.repository.defi.DeFiRepositoryImpl
import com.example.data.repository.exchanges.ExchangesRepository
import com.example.data.repository.exchanges.ExchangesRepositoryImpl
import com.example.data.repository.global.GlobalRepository
import com.example.data.repository.global.GlobalRepositoryImpl
import com.example.data.repository.nfts.NftsRepository
import com.example.data.repository.nfts.NftsRepositoryImpl
import com.example.data.repository.search.SearchRepository
import com.example.data.repository.search.SearchRepositoryImpl
import com.example.data.repository.settings.SettingsRepository
import com.example.data.repository.settings.SettingsRepositoryImpl
import com.example.data.repository.trending.TrendingRepository
import com.example.data.repository.trending.TrendingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCoinRepository(
        coinRepositoryImpl: CoinsRepositoryImpl
    ): CoinsRepository

    @Binds
    abstract fun bindExchangesRepository(
        exchangesRepositoryImpl: ExchangesRepositoryImpl
    ): ExchangesRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository

    @Binds
    abstract fun bindCategoriesRepository(
        impl: CategoriesRepositoryImpl
    ): CategoriesRepository

    @Binds
    abstract fun bindCompaniesRepository(
        impl: CompaniesRepositoryImpl
    ): CompaniesRepository

    @Binds
    abstract fun bindDeFiRepository(
        impl: DeFiRepositoryImpl
    ): DeFiRepository

    @Binds abstract fun bindGlobalRepository(impl: GlobalRepositoryImpl): GlobalRepository

    @Binds abstract fun bindNftsRepository(impl: NftsRepositoryImpl): NftsRepository

    @Binds
    abstract fun bindSearchRepository(
        impl: SearchRepositoryImpl
    ): SearchRepository


    @Binds
    abstract fun bindTrendingRepository(
        impl: TrendingRepositoryImpl
    ): TrendingRepository

}
