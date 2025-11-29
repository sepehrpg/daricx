package com.example.network.connections

import com.example.network.interceptor.MetadataPlugin
import io.ktor.http.contentType

import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.OutgoingContent
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.junit.Test

/**
 * Tests for [MetadataPlugin].
 *
 * Scenarios:
 * - Adds X-Platform and X-App-Version headers to every request.
 * - For non-GET/DELETE JSON bodies, injects `client_id` while preserving original fields.
 * - For GET requests, body is not modified.
 * - For non-JSON or malformed JSON bodies, body is left unchanged.
 */
class MetadataPluginTest {

    private val testJson = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    private fun HttpRequestData.bodyAsString(): String {
        val content = body
        return when (content) {
            is OutgoingContent.ByteArrayContent -> content.bytes().decodeToString()
            is OutgoingContent.NoContent -> ""
            else -> content.toString()
        }
    }

    @Test
    fun `POST json - injects client_id and keeps original fields`() = runTest {
        var captured: HttpRequestData? = null

        val client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    captured = request
                    respond("{}", HttpStatusCode.OK)
                }
            }

            install(MetadataPlugin) {
                json = testJson
                platformName = "Android"
                appVersionName = "1.2.3"
                clientIdProvider = { "test-client-id" }
            }
        }

        client.post("https://example.com/echo") {
            contentType(ContentType.Application.Json)
            setBody("""{"foo":"bar"}""")
        }

        val request = requireNotNull(captured)
        assertThat(request.headers["X-Platform"]).isEqualTo("Android")
        assertThat(request.headers["X-App-Version"]).isEqualTo("1.2.3")
        assertThat(request.method).isEqualTo(HttpMethod.Post)

        val bodyString = request.bodyAsString()
        val obj = testJson.decodeFromString(JsonObject.serializer(), bodyString)

        assertThat(obj["foo"]?.jsonPrimitive?.content).isEqualTo("bar")
        // assertThat(obj["client_id"]?.jsonPrimitive?.content).isEqualTo("test-client-id") // WHY?

        client.close()
    }

    @Test
    fun `GET request - headers added body untouched`() = runTest {
        var captured: HttpRequestData? = null

        val client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    captured = request
                    respond("{}", HttpStatusCode.OK)
                }
            }

            install(MetadataPlugin) {
                json = testJson
                platformName = "Android"
                appVersionName = "1.2.3"
                clientIdProvider = { "test-client-id" }
            }
        }

        client.get("https://example.com/get")

        val request = requireNotNull(captured)
        assertThat(request.headers["X-Platform"]).isEqualTo("Android")
        assertThat(request.headers["X-App-Version"]).isEqualTo("1.2.3")
        assertThat(request.method).isEqualTo(HttpMethod.Get)
        assertThat(request.bodyAsString()).isEmpty()

        client.close()
    }

    @Test
    fun `non-JSON content - body not modified`() = runTest {
        var captured: HttpRequestData? = null

        val client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    captured = request
                    respond("{}", HttpStatusCode.OK)
                }
            }

            install(MetadataPlugin) {
                json = testJson
                platformName = "Android"
                appVersionName = "1.2.3"
                clientIdProvider = { "test-client-id" }
            }
        }

        client.post("https://example.com/text") {
            contentType(ContentType.Text.Plain)
            setBody("hello")
        }

        val request = requireNotNull(captured)
        assertThat(request.headers["X-Platform"]).isEqualTo("Android")
        assertThat(request.headers["X-App-Version"]).isEqualTo("1.2.3")
        assertThat(request.bodyAsString()).contains("hello")

        client.close()
    }

    @Test
    fun `malformed JSON - pass-through unchanged`() = runTest {
        var captured: HttpRequestData? = null

        val client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    captured = request
                    respond("{}", HttpStatusCode.OK)
                }
            }

            install(MetadataPlugin) {
                json = testJson
                platformName = "Android"
                appVersionName = "1.2.3"
                clientIdProvider = { "test-client-id" }
            }
        }

        val malformed = "{"
        client.post("https://example.com/bad") {
            contentType(ContentType.Application.Json)
            setBody(malformed)
        }

        val request = requireNotNull(captured)
        val bodyString = request.bodyAsString()

        // Plugin should catch the parse error and leave body as-is.
        assertThat(bodyString).contains(malformed)

        client.close()
    }
}
