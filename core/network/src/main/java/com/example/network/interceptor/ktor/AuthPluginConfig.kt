// com/example/network/ktor/AuthPlugin.kt
package com.example.network.ktor

import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders

/**
 * Configuration for [AuthPlugin].
 *
 * You can:
 * - change the header name used for the API key,
 * - provide a dynamic API key provider,
 * - optionally provide a bearer token provider.
 */
class AuthPluginConfig {
    /** Header name used for the CoinGecko demo API key. */
    var apiKeyHeaderName: String = "x-cg-demo-api-key"

    /** Function that returns the API key value. */
    var apiKeyProvider: () -> String = { "" }

    /**
     * Optional provider for a bearer token.
     * If it returns a non-blank value, the plugin will send:
     * Authorization: Bearer <token>
     */
    var bearerTokenProvider: (() -> String?)? = null
}

/**
 * Ktor equivalent of the OkHttp [AuthInterceptor].
 *
 * Responsibilities:
 * - Attach the CoinGecko demo API key to every outgoing request.
 * - Optionally attach a bearer token if provided by [AuthPluginConfig.bearerTokenProvider].
 */
val AuthPlugin = createClientPlugin(
    name = "AuthPlugin",
    createConfiguration = ::AuthPluginConfig,
) {
    val config = pluginConfig

    onRequest { request, _ ->
        // Attach API key header if available.
        val apiKey = config.apiKeyProvider()
        if (apiKey.isNotEmpty()) {
            request.headers {
                append(config.apiKeyHeaderName, apiKey)
            }
        }

        // Attach bearer token header if available.
        val token = config.bearerTokenProvider?.invoke()
        if (!token.isNullOrBlank()) {
            request.headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
    }
}
