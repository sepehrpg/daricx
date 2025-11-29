package com.example.network.connections

import com.example.network.ktor.AuthPlugin
import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * Tests for [AuthPlugin].
 *
 * Scenarios:
 * - Adds API key header on every request.
 * - Adds Authorization Bearer header when a token is provided.
 * - Skips Authorization header when token provider returns null or blank.
 */
class AuthPluginTest {

    @Test
    fun `adds api key header to every request`() = runTest {
        var captured: HttpRequestData? = null

        val client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    captured = request
                    respond("{}", HttpStatusCode.OK)
                }
            }

            install(AuthPlugin) {
                apiKeyHeaderName = "x-cg-demo-api-key"
                apiKeyProvider = { "TEST_KEY" }
                bearerTokenProvider = null
            }
        }

        client.get("https://example.com/test")

        val request = requireNotNull(captured)
        assertThat(request.headers["x-cg-demo-api-key"]).isEqualTo("TEST_KEY")
        assertThat(request.headers[HttpHeaders.Authorization]).isNull()

        client.close()
    }

    @Test
    fun `adds bearer token when provider returns value`() = runTest {
        var captured: HttpRequestData? = null

        val client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    captured = request
                    respond("{}", HttpStatusCode.OK)
                }
            }

            install(AuthPlugin) {
                apiKeyHeaderName = "x-cg-demo-api-key"
                apiKeyProvider = { "TEST_KEY" }
                bearerTokenProvider = { "jwt-token-123" }
            }
        }

        client.get("https://example.com/secure")

        val request = requireNotNull(captured)
        assertThat(request.headers["x-cg-demo-api-key"]).isEqualTo("TEST_KEY")
        assertThat(request.headers[HttpHeaders.Authorization])
            .isEqualTo("Bearer jwt-token-123")

        client.close()
    }

    @Test
    fun `does not add bearer header when provider returns null`() = runTest {
        var captured: HttpRequestData? = null

        val client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    captured = request
                    respond("{}", HttpStatusCode.OK)
                }
            }

            install(AuthPlugin) {
                apiKeyHeaderName = "x-cg-demo-api-key"
                apiKeyProvider = { "TEST_KEY" }
                bearerTokenProvider = { null }
            }
        }

        client.get("https://example.com/no-bearer")

        val request = requireNotNull(captured)
        assertThat(request.headers["x-cg-demo-api-key"]).isEqualTo("TEST_KEY")
        assertThat(request.headers[HttpHeaders.Authorization]).isNull()

        client.close()
    }
}
