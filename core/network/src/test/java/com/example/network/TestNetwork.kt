package com.example.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Utility object for creating Retrofit instances in unit tests.
 *
 * This is deliberately minimal compared to the production [RetrofitModule]:
 * - No Hilt, no Android Context
 * - No OkHttp interceptors (Auth, Retry, Chucker, etc.)
 * - Only Json serialization and baseUrl from MockWebServer
 *
 * Usage:
 * ```
 * val retrofit = TestNetwork.retrofit(mockWebServer.url("/").toString())
 * val api = retrofit.create(ApiService::class.java)
 * ```
 */
internal object TestNetwork {

    /** Shared Json config used in tests */
    val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    /**
     * Builds a Retrofit instance pointing to [baseUrl].
     * Typically [baseUrl] is provided by [okhttp3.mockwebserver.MockWebServer].
     */
    @OptIn(ExperimentalSerializationApi::class)
    fun retrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
}
