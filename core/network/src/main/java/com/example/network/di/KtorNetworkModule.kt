package com.example.network.di

import com.example.common.config.AppVersionName
import com.example.network.BuildConfig
import com.example.network.NetworkConfig
import com.example.network.interceptor.configureDefaultRetries
import com.example.network.ktor.AuthPlugin
import com.example.network.interceptor.MetadataPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

/**
 * Ktor equivalent of [RetrofitModule].
 *
 * Responsibilities:
 * - Provide a single configured [Json] instance.
 * - Provide a single configured [HttpClient] based on the OkHttp engine.
 *
 * This client replaces:
 * - OkHttpClient + Interceptors (Auth, Metadata, Retry, BodyLogging)
 * - Chucker (via KtorMonitorLogging)
 */
@Module
@InstallIn(SingletonComponent::class)
internal object KtorNetworkModule {


    /**
     * Provides a singleton [HttpClient] configured to behave as close as possible
     * to the previous Retrofit + OkHttp setup.
     *
     * Mapping from old stack to Ktor:
     * - Retrofit converterFactory(Json) → ContentNegotiation + json()
     * - OkHttp baseUrl → DefaultRequest.url(...)
     * - AuthInterceptor → AuthPlugin
     * - MetadataInterceptor → MetadataPlugin
     * - RetryInterceptor → HttpRequestRetry + configureDefaultRetries()
     * - BodyLoggingInterceptor → Logging (LogLevel.BODY)
     * - Chucker → KtorMonitorLogging
     */
    @Provides
    @Singleton
    fun provideKtorHttpClient(
        networkJson: Json,
        @AppVersionName appVersionName: String,
    ): HttpClient = HttpClient(OkHttp) {

        // 1) JSON serialization (equivalent to Retrofit's converterFactory(Json))
        install(ContentNegotiation) {
            json(networkJson)
        }

        // 2) Base URL + default headers (equivalent to Retrofit baseUrl + default headers)
        install(DefaultRequest) {
            url {
                // Treat all relative request paths as being under BASE_URL
                takeFrom(URLBuilder(NetworkConfig.BASE_URL))
            }
            header(HttpHeaders.Accept, ContentType.Application.Json)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        // 3) Timeouts (equivalent to OkHttp connect/read/write timeout)
        install(HttpTimeout) {
            val timeoutMillis = NetworkConfig.TIMEOUT_MILLIS
            requestTimeoutMillis = timeoutMillis
            connectTimeoutMillis = timeoutMillis
            socketTimeoutMillis = timeoutMillis
        }

        // 4) Retry behavior (equivalent to RetryInterceptor with exponential backoff)
        install(HttpRequestRetry) {
            configureDefaultRetries(
                maxRetry = 3,
                initialDelayMillis = 1_000L,
            )
        }

        // 5) Authentication headers (equivalent to AuthInterceptor)
        install(AuthPlugin) {
            apiKeyProvider = { "CG-BRmwWG1EPbaHsW9UZbscBd7P" }
            // Example for bearer token, later:
            // bearerTokenProvider = { tokenProvider.getTokenOrNull() }
        }

        // 6) Metadata headers + client_id in JSON body (equivalent to MetadataInterceptor)
        install(MetadataPlugin) {
            json = networkJson
            platformName = "Android"
            this.appVersionName = appVersionName
            clientIdProvider = { "your_client_id_here" }
        }

        // 7) Logging + KtorMonitor (equivalent to BodyLoggingInterceptor + Chucker)
        if (BuildConfig.DEBUG) {
            // Log request/response line + headers + body to Logcat (Timber backend)
            install(Logging) {
                level = LogLevel.BODY
            }


            /**
             * need minSdk>=26
             */
            // Visual inspector similar to Chucker but for Ktor
            /*install(KtorMonitorLogging) {
                // Redact sensitive headers before logging them
                sanitizeHeader { header ->
                    header.equals("Authorization", ignoreCase = true) ||
                            header.equals("x-cg-demo-api-key", ignoreCase = true)
                }

                // Show ongoing network activity as a notification on Android
                showNotification = true

                // Keep logs in memory for 1 hour
                retentionPeriod = RetentionPeriod.OneHour

                // Truncate very large bodies (default is usually enough)
                maxContentLength = ContentLength.Default
            }*/
        }
    }
}
