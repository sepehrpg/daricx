package com.example.network.connections


import com.example.network.interceptor.okhttp.BodyLoggingInterceptor
import com.google.common.truth.Truth.assertThat
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import timber.log.Timber

/**
 * Tests for [BodyLoggingInterceptor].
 *
 * Test Goals:
 * - Ensure logging does not consume the response body (client can still read it).
 * - Ensure request body remains intact when reaching the server.
 *
 * Scenarios:
 * 1) GET -> response body readable after logging.
 * 2) POST JSON -> server receives exact body.
 */
@RunWith(RobolectricTestRunner::class)
class BodyLoggingInterceptorTest {

    private lateinit var server: MockWebServer
    private lateinit var client: OkHttpClient

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())
        client = OkHttpClient.Builder()
            .addInterceptor(BodyLoggingInterceptor())
            .build()
    }

    @After
    fun tearDown() {
        server.shutdown()
        Timber.uprootAll()
    }

    @Test
    fun `GET - response body is readable`() {
        val payload = """{"ok":true}"""
        server.enqueue(MockResponse().setResponseCode(200).setBody(payload))

        val resp = client.newCall(
            Request.Builder().url(server.url("/get")).get().build()
        ).execute()

        resp.use {
            assertThat(it.isSuccessful).isTrue()
            val text = it.body?.string()
            assertThat(text).isEqualTo(payload)
        }
    }

    @Test
    fun `POST JSON - request body preserved`() {
        server.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        val json = """{"a":1,"b":"c"}"""
        val req = Request.Builder()
            .url(server.url("/post"))
            .post(json.toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(req).execute().close()

        val recorded = server.takeRequest()
        assertThat(recorded.body.readUtf8()).isEqualTo(json)
    }
}
