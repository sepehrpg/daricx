package com.example.network.di

import org.koin.core.annotation.Module

@Module(
    includes = [
        JsonModule::class,
        KtorClientModule::class,
        NetworkUtilsModule::class,
        NetworkDataSourceModule::class,
    ]
)
class NetworkModuleComposition