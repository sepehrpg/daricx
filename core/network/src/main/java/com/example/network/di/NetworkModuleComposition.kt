package com.example.network.di

import org.koin.core.annotation.Module
import org.koin.core.annotation.ComponentScan

@Module(
    includes = [
        JsonModule::class,
        KtorClientModule::class,
        NetworkUtilsModule::class,
        NetworkDataSourceModule::class,
    ]
)
class CompositionNetworkModule