package com.example.network.search


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
 * SearchApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for GET /search endpoint.
 *
 * Scenarios:
 * 1) Sends GET to /search with query param "query".
 * 2) Payload is parsed correctly (checked in Serialization test).
 */
class SearchApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `GET search has query param`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonSearch.response))

        api.search("bitcoin")
        val req = server.takeRequest()

        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/search")
        assertThat(req.requestUrl?.queryParameter("query")).isEqualTo("bitcoin")
    }
}
