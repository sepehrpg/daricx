package com.example.network.connections


import com.example.network.interceptor.OkHttpEventLogger
import com.google.common.truth.Truth.assertThat
import okhttp3.EventListener
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import timber.log.Timber

/**
 * Tests for [OkHttpEventLogger].
 *
 * Test Goals:
 * - Using the event listener factory should not crash and should complete a call.
 *
 * Scenario:
 * 1) Simple GET -> completes without exceptions.
 */
@RunWith(RobolectricTestRunner::class)
class OkHttpEventLoggerTest {

    private lateinit var server: MockWebServer

    @Before
    fun setup() {
        server = MockWebServer().apply { start() }
        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())
    }

    @After
    fun teardown() {
        server.shutdown()
        Timber.uprootAll()
    }

    @Test
    fun `factory wiring does not crash`() {
        server.enqueue(MockResponse().setResponseCode(200).setBody("{}"))
        val client = OkHttpClient.Builder()
            .eventListenerFactory(EventListener.Factory { OkHttpEventLogger() })
            .build()

        val resp = client.newCall(Request.Builder().url(server.url("/")).get().build()).execute()
        assertThat(resp.code).isEqualTo(200)
    }
}
