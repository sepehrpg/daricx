package com.example.network.interceptor

import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/**
 * Ktor equivalent of the OkHttp MetadataInterceptor.
 *
 * Scenarios:
 * - Always add X-Platform and X-App-Version headers.
 * - For non-GET/DELETE requests, try to inject client_id into JSON bodies.
 */
class MetadataPluginConfig {
    lateinit var json: Json
    var platformName: String = "Android"
    var appVersionName: String = ""
    var clientIdProvider: () -> String? = { null }
}

val MetadataPlugin = createClientPlugin("MetadataPlugin", ::MetadataPluginConfig) {
    val config = pluginConfig

    onRequest { request, _ ->
        // 1) Always add headers.
        request.headers.append("X-Platform", config.platformName)
        request.headers.append("X-App-Version", config.appVersionName)

        // If no client_id, nothing more to do.
        val clientId = config.clientIdProvider() ?: return@onRequest

        // Do not touch bodies for GET/DELETE.
        if (request.method == HttpMethod.Get || request.method == HttpMethod.Delete) {
            return@onRequest
        }

        // 2) Inspect current body and inject client_id if it's JSON.
        when (val body = request.body) {
            is JsonObject -> {
                val mutable = body.toMutableMap()
                mutable["client_id"] = JsonPrimitive(clientId)
                request.setBody(JsonObject(mutable))
            }

            is String -> {
                // Try to parse as JSON string and inject client_id.
                runCatching {
                    val obj = config.json.decodeFromString(
                        JsonObject.serializer(),
                        body,
                    )
                    val mutable = obj.toMutableMap()
                    mutable["client_id"] = JsonPrimitive(clientId)
                    request.setBody(JsonObject(mutable))
                }
                // On parse error we keep original body as-is.
            }

            else -> {
                // Non-JSON bodies (DTOs, byte arrays, etc.) are not modified.
            }
        }
    }
}
