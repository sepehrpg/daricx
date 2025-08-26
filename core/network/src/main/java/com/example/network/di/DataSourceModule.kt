package com.example.network.di


import com.example.network.datasource.RemoteCoinsDataSource
import com.example.network.datasource.RemoteCoinsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindRemoteCoinDataSource(
        remoteCoinsDataSource: RemoteCoinsDataSourceImpl
    ): RemoteCoinsDataSource
}