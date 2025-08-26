package com.example.network.di

import com.example.common.config.AppVersionName
import com.example.network.BuildConfig
import com.example.network.NetworkConfig
import com.example.network.NetworkConfig.TIMEOUT_SECONDS
import com.example.network.api.ApiService
import com.example.network.interceptor.AuthInterceptor
import com.example.network.interceptor.MetadataInterceptor
import com.example.network.interceptor.RetryInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true // Helpful for parsing potentially malformed JSON
    }


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideAuthInterceptor(/* Inject token dependencies here */): AuthInterceptor {
        // For example: val tokenProvider: TokenProvider
        return AuthInterceptor(/* tokenProvider */)
    }

    @Provides
    @Singleton
    fun provideMetadataInterceptor(
        networkJson: Json,
        @AppVersionName appVersionName: String // Inject the version name here
    ): MetadataInterceptor {
        return MetadataInterceptor(networkJson, appVersionName)
    }

    @Provides
    @Singleton
    fun provideRetryInterceptor(): RetryInterceptor = RetryInterceptor(maxRetry = 3, initialDelayMillis = 1000L)

    @Provides
    @Singleton
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
        metadataInterceptor: MetadataInterceptor,
        retryInterceptor: RetryInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        //.addInterceptor(authInterceptor)      //  Adds auth token
        //.addInterceptor(metadataInterceptor)  // Adds common metadata
        .addInterceptor(retryInterceptor)     //  Handles retries
        .addInterceptor(loggingInterceptor)   //  Logs the final request
        .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .build()


    @Singleton
    @Provides
    fun provideRetrofitInstance(
        networkJson: Json,
        okHttpClient: dagger.Lazy<OkHttpClient>
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .callFactory { request -> okHttpClient.get().newCall(request) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    @Provides
    @Singleton
    fun providerApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}



