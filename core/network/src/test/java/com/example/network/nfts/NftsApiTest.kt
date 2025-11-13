package com.example.network.nfts


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
 * NftsApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for GET /nfts/list.
 *
 * Scenarios:
 * 1) GET with no params -> hits "/nfts/list" and has no query string.
 * 2) GET with order+page+per_page -> includes all query parameters.
 * 3) Payload can be deserialized (covered by Serialization test).
 */
class NftsApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
    }

    @After fun tearDown() { server.shutdown() }

    @Test
    fun `GET no params  path only`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonNfts.listResponse))

        api.getNftsList()
        val req = server.takeRequest()

        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/nfts/list")
        assertThat(req.requestUrl?.query).isNull()
    }

    @Test
    fun `geT with params  has query`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonNfts.listResponse))

        api.getNftsList(
            order = "market_cap_usd_desc",
            perPage = 50,
            page = 2
        )
        val req = server.takeRequest()

        assertThat(req.requestUrl?.encodedPath).isEqualTo("/nfts/list")
        assertThat(req.requestUrl?.queryParameter("order")).isEqualTo("market_cap_usd_desc")
        assertThat(req.requestUrl?.queryParameter("per_page")).isEqualTo("50")
        assertThat(req.requestUrl?.queryParameter("page")).isEqualTo("2")
    }
}
