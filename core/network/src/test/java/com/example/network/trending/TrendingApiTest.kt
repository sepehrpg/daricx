package com.example.network.trending

import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.create

/**
 * TrendingApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for GET /search/trending.
 *
 * Scenarios:
 * 1) Sends GET request to correct path with no query params.
 * 2) Valid payload deserializes into TrendingDto (checked separately).
 */
class TrendingApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `GET trending hits correct path`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonTrending.response))

        api.getTrending()
        val req = server.takeRequest()

        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/search/trending")
        assertThat(req.requestUrl?.query).isNull()
    }
}
