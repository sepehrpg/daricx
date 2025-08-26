package com.example.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


/**
 * An interceptor for smart retries on server errors.
 */
class RetryInterceptor(
    private val maxRetry: Int,
    private val initialDelayMillis: Long
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)
        var tryCount = 0
        var currentDelay = initialDelayMillis

        while (!response.isSuccessful && response.code >= 500 && tryCount < maxRetry) {
            tryCount++
            response.close()
            try {
                Thread.sleep(currentDelay)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
                throw IOException(e)
            }
            currentDelay *= 2
            response = chain.proceed(request)
        }
        return response
    }
}
