package com.example.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response


/**
 * A dedicated interceptor for adding authentication headers.
 * Its only responsibility is authentication.
 */
class AuthInterceptor(/* private val tokenProvider: TokenProvider */) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // val token = tokenProvider.getToken() // Get the token from a trusted source
        val requestBuilder = chain.request().newBuilder()

        // if (token != null) {
        //     requestBuilder.addHeader("Authorization", "Bearer $token")
        // }

        return chain.proceed(requestBuilder.build())
    }
}
