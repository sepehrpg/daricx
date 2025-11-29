package com.example.network.interceptor

import io.ktor.client.plugins.HttpRequestRetryConfig

/**
 * Ktor equivalent of the OkHttp [RetryInterceptor].
 *
 * It:
 * - retries on 5xx server errors a limited number of times,
 * - uses exponential backoff between retries,
 * - also retries on network exceptions (timeouts, IO issues, etc.).
 */
fun HttpRequestRetryConfig.configureDefaultRetries(
    maxRetry: Int = 3,
    initialDelayMillis: Long = 1_000L,
) {
    // Retry on all 5xx server errors.
    retryOnServerErrors(maxRetries = maxRetry)

    // Exponential delay: 1s, 2s, 4s, ... up to maxDelayMs.
    exponentialDelay(
        baseDelayMs = initialDelayMillis,
        maxDelayMs = initialDelayMillis * (1L shl maxRetry),
    )

    // Also retry on exceptions (e.g. timeouts, connection resets).
    retryOnExceptionIf { _, _ ->
        true
    }
}
