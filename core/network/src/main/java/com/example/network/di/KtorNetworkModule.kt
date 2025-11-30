package com.example.network.di

import com.example.common.config.AppVersionName
import com.example.network.BuildConfig
import com.example.network.NetworkConfig
import com.example.network.interceptor.configureDefaultRetries
import com.example.network.ktor.AuthPlugin
import com.example.network.interceptor.MetadataPlugin

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
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single


@Module
class KtorClientModule {

    @Single
    fun provideKtorHttpClient(
        json: Json,
        //@Named("appVersionName") appVersionName: String,
       appVersionName: String = "1.0.0",
    ): HttpClient = HttpClient(OkHttp) {

        install(ContentNegotiation) {
            json(json)
        }

        install(DefaultRequest) {
            url {
                takeFrom(URLBuilder(NetworkConfig.BASE_URL))
            }
            header(HttpHeaders.Accept, ContentType.Application.Json)
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        install(HttpTimeout) {
            val timeoutMillis = NetworkConfig.TIMEOUT_MILLIS
            requestTimeoutMillis = timeoutMillis
            connectTimeoutMillis = timeoutMillis
            socketTimeoutMillis = timeoutMillis
        }

        install(HttpRequestRetry) {
            configureDefaultRetries(
                maxRetry = 3,
                initialDelayMillis = 1_000L,
            )
        }

        install(AuthPlugin) {
            apiKeyProvider = { "CG-BRmwWG1EPbaHsW9UZbscBd7P" }
        }

        install(MetadataPlugin) {
            this.json = json
            platformName = "Android"
            this.appVersionName = appVersionName
            clientIdProvider = { "your_client_id_here" }
        }

        if (BuildConfig.DEBUG) {
            install(Logging) {
                level = LogLevel.BODY
            }
        }
    }
}
