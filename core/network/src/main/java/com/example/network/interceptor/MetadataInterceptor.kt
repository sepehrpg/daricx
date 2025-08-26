package com.example.network.interceptor

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import okhttp3.Interceptor
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.Buffer
import javax.inject.Inject


/**
 * A dedicated interceptor for adding common metadata to every request.
 * It adds data to both headers and the request body (for JSON requests).
 */
class MetadataInterceptor @Inject constructor(
    private val json: Json,
    private val appVersionName: String // Receive the injected version name
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        // 1. Add fixed headers
        val requestWithHeaders = originalRequest.newBuilder()
            .header("X-Platform", "Android")
            .header("X-App-Version", appVersionName) // Use the injected value
            .build()
        val originalBody = requestWithHeaders.body
        val method = requestWithHeaders.method
        // 2. Modify the body only for requests that can have a body and are JSON
        if (originalBody == null || method == "GET" || method == "DELETE" || originalBody.contentType()?.subtype != "json") {
            return chain.proceed(requestWithHeaders)
        }
        try {
            val buffer = Buffer()
            originalBody.writeTo(buffer)
            val originalBodyString = buffer.readUtf8()
            if (originalBodyString.isEmpty()) return chain.proceed(requestWithHeaders)
            val jsonObject = json.decodeFromString<JsonObject>(originalBodyString).toMutableMap()
            // Add your fixed key-value pairs to the body
            jsonObject["client_id"] = JsonPrimitive("your_client_id_here")
            val newBodyString = json.encodeToString(JsonObject(jsonObject))
            val newBody = newBodyString.toRequestBody(originalBody.contentType())
            val newRequest = requestWithHeaders.newBuilder().method(method, newBody).build()
            return chain.proceed(newRequest)
        } catch (e: Exception) {
            // If parsing fails, proceed with the original request to avoid breaking the app
            return chain.proceed(requestWithHeaders)
        }
    }
}
