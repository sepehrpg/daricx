package com.example.network.interceptor.ktor

import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/**
 * Configuration for [MetadataPlugin].
 *
 * Responsibilities:
 * - provide a shared [Json] instance used for parsing String bodies,
 * - customize platform and app version headers,
 * - provide an optional [clientIdProvider] that injects client_id into JSON bodies.
 */
class MetadataPluginConfig {
    /**
     * Shared Json instance used to parse/modify JSON bodies when needed.
     * Must be set from the DI module.
     */
    lateinit var json: Json

    /** Platform name attached via "X-Platform" header. */
    var platformName: String = "Android"

    /** App version attached via "X-App-Version" header. */
    var appVersionName: String = ""

    /**
     * If this provider returns a non-null value, the plugin attempts to inject
     * a "client_id" field into JSON request bodies.
     */
    var clientIdProvider: () -> String? = { null }
}

/**
 * Ktor equivalent of the OkHttp [MetadataInterceptor].
 *
 * For every outgoing request it:
 * 1. Adds fixed headers:
 *      - X-Platform: <platformName>
 *      - X-App-Version: <appVersionName>
 * 2. For non-GET/DELETE requests with JSON bodies:
 *      - injects "client_id" into the JSON body if [MetadataPluginConfig.clientIdProvider]
 *        returns a non-null value.
 */
val MetadataPlugin = createClientPlugin("MetadataPlugin", ::MetadataPluginConfig) {
    val config = pluginConfig

    onRequest { request, content ->
        // 1. Always attach metadata headers.
        request.headers.append("X-Platform", config.platformName)
        request.headers.append("X-App-Version", config.appVersionName)

        // If no client_id is provided, we only set headers and exit early.
        val clientId = config.clientIdProvider() ?: return@onRequest

        // 2. Only attempt to modify bodies for methods that can have a body.
        if (request.method == HttpMethod.Get || request.method == HttpMethod.Delete) {
            return@onRequest
        }

        when (content) {
            is JsonObject -> {
                // Body is already a JsonObject → inject client_id directly.
                val mutable = content.toMutableMap()
                mutable["client_id"] = JsonPrimitive(clientId)
                request.setBody(JsonObject(mutable))
            }

            is String -> {
                // Body is a raw JSON string → try to parse and inject client_id.
                runCatching {
                    val jsonObj = config.json.decodeFromString(
                        JsonObject.serializer(),
                        content,
                    )
                    val mutable = jsonObj.toMutableMap()
                    mutable["client_id"] = JsonPrimitive(clientId)
                    request.setBody(JsonObject(mutable))
                }
                // If parsing fails, we silently keep the original body unchanged.
            }

            else -> {
                // Non-JSON bodies are intentionally ignored.
                // This mirrors the behavior of the OkHttp interceptor which only
                // touched JSON request bodies.
            }
        }
    }
}
