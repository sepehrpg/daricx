package com.example.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockEngineConfig
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json



internal object TestNetwork {

    val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    /**
     * Create a Ktor HttpClient backed by MockEngine.
     *
     * [handler] lets each test decide how to respond for a given request.
     */
    fun ktorTestClient(
        json: Json = this.json,
        handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData
    ): HttpClient =
        HttpClient(MockEngine) {
            install(ContentNegotiation) {
                json(json)
            }

            engine {
                addHandler { request ->
                    handler(request)
                }
            }
        }
}



/**
 * Convenience extension: return 200 OK JSON from MockEngine.
 */
internal fun MockRequestHandleScope.jsonOkResponse(body: String): HttpResponseData =
    respond(
        body,
        HttpStatusCode.OK,
        headersOf(
            HttpHeaders.ContentType,
            ContentType.Application.Json.toString()
        )
    )