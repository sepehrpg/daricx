package com.example.network.connections


import com.example.network.connections.TestUtils.jsonBody
import com.example.network.interceptor.okhttp.MetadataInterceptor
import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test


// TODO : need to improve
/**
 * Tests for [MetadataInterceptor].
 *
 * Test Goals:
 * - Ensure fixed headers ("X-Platform", "X-App-Version") are always added.
 * - For JSON POST/PUT/PATCH bodies, ensure `client_id` is injected, preserving original fields.
 * - For GET/DELETE or non-JSON content types, body must remain unchanged.
 * - Malformed JSON bodies must pass through untouched (no crash).
 *
 * Scenarios:
 * 1) POST application/json -> body receives `client_id`, headers exist.
 * 2) GET -> headers exist, body unchanged/empty.
 * 3) POST text/plain -> body unchanged, headers exist.
 * 4) POST malformed JSON -> pass-through unchanged, headers exist.
 */
class MetadataInterceptorTest {

    private lateinit var server: MockWebServer
    private lateinit var client: OkHttpClient

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        client = OkHttpClient.Builder()
            .addInterceptor(MetadataInterceptor(json, "1.2.3"))
            .build()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `POST json - injects client_id and keeps original fields`() {
        server.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        val req = Request.Builder()
            .url(server.url("/echo"))
            .post(jsonBody("""{"foo":"bar"}"""))
            .build()

        client.newCall(req).execute().use { resp ->
            assertThat(resp.code).isEqualTo(200)
        }

        val recorded = server.takeRequest()
        assertThat(recorded.getHeader("X-Platform")).isEqualTo("Android")
        assertThat(recorded.getHeader("X-App-Version")).isEqualTo("1.2.3")

        val sentBody = recorded.body.readUtf8()
        // Should contain original field and injected `client_id`
        assertThat(sentBody).contains("\"foo\":\"bar\"")
        assertThat(sentBody).contains("\"client_id\":\"your_client_id_here\"")
    }

    @Test
    fun `GET request - headers added, body untouched`() {
        server.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        val req = Request.Builder()
            .url(server.url("/get"))
            .get()
            .build()

        client.newCall(req).execute().close()

        val recorded = server.takeRequest()
        assertThat(recorded.getHeader("X-Platform")).isEqualTo("Android")
        assertThat(recorded.getHeader("X-App-Version")).isEqualTo("1.2.3")
        // No body for GET
        assertThat(recorded.body.readUtf8()).isEmpty()
    }

    @Test
    fun `non-JSON content-type - body not modified`() {
        server.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        val req = Request.Builder()
            .url(server.url("/text"))
            .post("hello".toRequestBody("text/plain".toMediaType()))
            .build()

        client.newCall(req).execute().close()

        val recorded = server.takeRequest()
        assertThat(recorded.getHeader("X-Platform")).isEqualTo("Android")
        assertThat(recorded.getHeader("X-App-Version")).isEqualTo("1.2.3")
        assertThat(recorded.body.readUtf8()).isEqualTo("hello")
    }

    @Test
    fun `malformed JSON - pass-through unchanged`() {
        server.enqueue(MockResponse().setResponseCode(200).setBody("{}"))

        val malformed = "{"
        val req = Request.Builder()
            .url(server.url("/bad"))
            .post(malformed.toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(req).execute().close()

        val recorded = server.takeRequest()
        assertThat(recorded.body.readUtf8()).isEqualTo(malformed)
    }
}
