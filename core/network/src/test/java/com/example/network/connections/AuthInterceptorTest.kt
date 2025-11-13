package com.example.network.connections

import com.example.network.interceptor.AuthInterceptor


import com.google.common.truth.Truth.assertThat
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * MUST BE CHANGE **********************************
 *
 * Tests for [AuthInterceptor].
 *
 * Test Goals:
 * - With current implementation (no token provider), it should be a no-op (no Authorization header added).
 *
 * Scenarios:
 * 1) GET -> no "Authorization" header present.
 */
class AuthInterceptorTest {

    private lateinit var server: MockWebServer
    private lateinit var client: OkHttpClient

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        client = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()
    }

    @After fun tearDown() { server.shutdown() }

    @Test
    fun `no authorization header added`() {
        server.enqueue(MockResponse().setResponseCode(200))

        val req = Request.Builder().url(server.url("/")).get().build()
        client.newCall(req).execute().close()

        val recorded = server.takeRequest()
        assertThat(recorded.getHeader("Authorization")).isNull()
    }
}
