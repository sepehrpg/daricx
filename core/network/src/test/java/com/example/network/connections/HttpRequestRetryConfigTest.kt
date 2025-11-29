package com.example.network.connections

import com.example.network.interceptor.ktor.configureDefaultRetries
import io.ktor.client.request.HttpResponseData
import com.google.common.truth.Truth.assertThat
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * Tests for [configureDefaultRetries].
 *
 * Scenarios:
 * - Retries on 5xx responses up to maxRetry and then succeeds.
 * - Stops after maxRetry when 5xx persists.
 * - Does not retry on 4xx responses.
 */
class HttpRequestRetryConfigTest {

    private fun clientWithRetry(
        maxRetry: Int,
        handler: suspend MockRequestHandleScope.(HttpRequestData, Int) -> HttpResponseData
    ): Pair<HttpClient, () -> Int> {
        var callCount = 0

        val client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    callCount++
                    handler(request, callCount)
                }
            }

            install(HttpRequestRetry) {
                configureDefaultRetries(
                    maxRetry = maxRetry,
                    initialDelayMillis = 1L, // keep tests fast
                )
            }
        }

        return client to { callCount }
    }

    @Test
    fun `retries on 5xx then succeeds`() = runTest {
        val (client, count) = clientWithRetry(maxRetry = 3) { _, attempt ->
            if (attempt == 1) {
                respond("error", HttpStatusCode.InternalServerError)
            } else {
                respond("ok", HttpStatusCode.OK)
            }
        }

        val response = client.get("https://example.com/api")
        assertThat(response.status).isEqualTo(HttpStatusCode.OK)
        // 1st = 500, 2nd = 200
        assertThat(count()).isEqualTo(2)

        client.close()
    }

    @Test
    fun `stops after maxRetry on persistent 5xx`() = runTest {
        val (client, count) = clientWithRetry(maxRetry = 3) { _, _ ->
            respond("error", HttpStatusCode.InternalServerError)
        }

        val response = client.get("https://example.com/api")
        assertThat(response.status).isEqualTo(HttpStatusCode.InternalServerError)
        // maxRetry = 3 -> total attempts = 4
        assertThat(count()).isEqualTo(4)

        client.close()
    }

    @Test
    fun `does not retry on 4xx`() = runTest {
        val (client, count) = clientWithRetry(maxRetry = 3) { _, _ ->
            respond("error", HttpStatusCode.TooManyRequests) // 429
        }

        val response = client.get("https://example.com/api")
        assertThat(response.status).isEqualTo(HttpStatusCode.TooManyRequests)
        // 4xx -> no retry
        assertThat(count()).isEqualTo(1)

        client.close()
    }
}
