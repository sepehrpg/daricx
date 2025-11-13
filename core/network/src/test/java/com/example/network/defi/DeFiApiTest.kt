package com.example.network.defi


import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.create

/**
 * DeFiApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for GET /global/decentralized_finance_defi.
 *
 * Scenarios:
 * 1) Sends GET to the exact path with no query parameters.
 * 2) Server returns 404 → propagated as HttpException.
 */
class DeFiApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `GET hits correct path without query`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonDeFi.defiResponse))

        api.getGlobalDeFi()
        val req = server.takeRequest()

        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/global/decentralized_finance_defi")
        assertThat(req.requestUrl?.query).isNull()
    }

    @Test(expected = HttpException::class)
    fun `http 404 bubbles up as HttpException`() = runTest {
        server.enqueue(MockResponse().setResponseCode(404).setBody("""{"error":"not found"}"""))
        api.getGlobalDeFi()
    }
}
