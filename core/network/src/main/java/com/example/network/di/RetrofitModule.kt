package com.example.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.common.config.AppVersionName
import com.example.network.BuildConfig
import com.example.network.NetworkConfig
import com.example.network.NetworkConfig.TIMEOUT_SECONDS
import com.example.network.api.ApiService
import com.example.network.interceptor.AuthInterceptor
import com.example.network.interceptor.BodyLoggingInterceptor
import com.example.network.interceptor.MetadataInterceptor
import com.example.network.interceptor.OkHttpEventLogger
import com.example.network.interceptor.RetryInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.EventListener
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitModule {

    @Provides @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }


    @Provides
    @Singleton
    fun provideBodyLoggingInterceptor(): BodyLoggingInterceptor = BodyLoggingInterceptor()


    @Provides
    @Singleton
    fun provideChucker(
        @ApplicationContext ctx: Context
    ): ChuckerInterceptor {
        val collector = ChuckerCollector(
            context = ctx,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        return ChuckerInterceptor.Builder(ctx)
            .collector(collector)
            .redactHeaders("Authorization", "X-Api-Key")
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): AuthInterceptor = AuthInterceptor()

    @Provides
    @Singleton
    fun provideMetadataInterceptor(
        networkJson: Json,
        @AppVersionName appVersionName: String
    ): MetadataInterceptor = MetadataInterceptor(networkJson, appVersionName)

    @Provides
    @Singleton
    fun provideRetryInterceptor(): RetryInterceptor =
        RetryInterceptor(maxRetry = 3, initialDelayMillis = 1_000L)

    @Provides
    @Singleton
    fun provideEventListenerFactory(): EventListener.Factory =
        EventListener.Factory { OkHttpEventLogger() }

    @Provides
    @Singleton
    fun provideOkHttp(
        chucker: ChuckerInterceptor,
        auth: AuthInterceptor,
        meta: MetadataInterceptor,
        retry: RetryInterceptor,
        bodyLogger: BodyLoggingInterceptor,
        eventListenerFactory: EventListener.Factory
    ): OkHttpClient {
        return OkHttpClient.Builder()
            // 1) request mutators
            .addInterceptor(auth)
            .addInterceptor(meta)
            // 2) retry
            .addInterceptor(retry)
            // 3) inspectors/loggers (only in debug)
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(chucker)          // on-device inspector UI
                    addInterceptor(bodyLogger)  // text logs to Logcat/Timber
                }
            }
            .eventListenerFactory(eventListenerFactory) // event listener factory class
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        networkJson: Json,
        okHttpClient: dagger.Lazy<OkHttpClient>
    ): Retrofit = Retrofit.Builder()
        .baseUrl(NetworkConfig.BASE_URL)
        .callFactory { request -> okHttpClient.get().newCall(request) }
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun providerApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
