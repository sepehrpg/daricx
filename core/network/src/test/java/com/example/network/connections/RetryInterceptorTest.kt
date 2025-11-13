package com.example.network.connections


import com.example.network.interceptor.RetryInterceptor
import com.google.common.truth.Truth.assertThat
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Tests for [RetryInterceptor].
 *
 * Test Goals:
 * - Retry on 5xx with exponential backoff.
 * - Stop after max retries when 5xx persists.
 * - Do NOT retry on 4xx.
 *
 * Scenarios:
 * 1) 500 then 200 -> total 2 requests, final 200.
 * 2) 500 repeated beyond maxRetry -> total = maxRetry + 1, final 500.
 * 3) 429 (4xx) -> no retry, single request.
 */
class RetryInterceptorTest {

    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    private fun client(maxRetry: Int) = OkHttpClient.Builder()
        .addInterceptor(RetryInterceptor(maxRetry = maxRetry, initialDelayMillis = 1L))
        .build()

    @Test
    fun `retries on 5xx then succeeds`() {
        server.enqueue(MockResponse().setResponseCode(500))
        server.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        val resp = client(maxRetry = 3).newCall(
            Request.Builder().url(server.url("/api")).get().build()
        ).execute()

        assertThat(resp.code).isEqualTo(200)
        assertThat(server.requestCount).isEqualTo(2)
    }

    @Test
    fun `stops after maxRetry on persistent 5xx`() {
        // maxRetry = 3 -> total attempts = 4
        repeat(4) { server.enqueue(MockResponse().setResponseCode(500)) }

        val resp = client(maxRetry = 3).newCall(
            Request.Builder().url(server.url("/api")).get().build()
        ).execute()

        assertThat(resp.code).isEqualTo(500)
        assertThat(server.requestCount).isEqualTo(4)
    }

    @Test
    fun `does not retry on 4xx`() {
        server.enqueue(MockResponse().setResponseCode(429)) // or 400/404

        val resp = client(maxRetry = 3).newCall(
            Request.Builder().url(server.url("/api")).get().build()
        ).execute()

        assertThat(resp.code).isEqualTo(429)
        assertThat(server.requestCount).isEqualTo(1)
    }
}
