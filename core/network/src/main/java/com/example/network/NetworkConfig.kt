package com.example.network

object NetworkConfig {

    const val BASE_URL = "https://api.coingecko.com/api/v3/"

    /**
     * Timeout duration (in milliseconds) used by:
     * - OkHttp connect/read/write timeouts (Retrofit stack)
     * - Ktor HttpTimeout plugin (Ktor stack)
     */
    const val TIMEOUT_MILLIS: Long = 20_000L

    /**
     * Default HTTP cache size in bytes (10 MB).
     *
     * Currently only used by the OkHttp-based client. If you add an HTTP cache
     * for Ktor later, you can reuse the same constant there as well.
     */
    const val CACHE_SIZE_BYTES: Long = 10 * 1024 * 1024 // 10 MB
}
