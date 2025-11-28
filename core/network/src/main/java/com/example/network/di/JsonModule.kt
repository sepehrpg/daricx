package com.example.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object JsonModule {

    /**
     * Shared JSON configuration for the whole network layer.
     *
     * Mirrors the behavior of the Json instance used by Retrofit's (or ktor)
     * kotlinx-serialization converter.
     */
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }
}