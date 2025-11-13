package com.example.network.companies


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
 * CompaniesApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for GET /companies/public_treasury/{coin_id} endpoint.
 *
 * Scenarios:
 * 1) GET with coin_id = "bitcoin" hits the correct path and has no query parameters.
 * 2) GET with coin_id = "ethereum" hits the correct path.
 * 3) Server returns 404 → propagated as HttpException.
 */
class CompaniesApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `GET for bitcoin hits correct path and no query`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCompanies.btcTreasuryResponse))

        api.getCompaniesTreasury("bitcoin")
        val req = server.takeRequest()

        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/companies/public_treasury/bitcoin")
        assertThat(req.requestUrl?.query).isNull()
    }

    @Test fun `GET for ethereum hits correct path`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCompanies.ethTreasuryResponse))

        api.getCompaniesTreasury("ethereum")
        val req = server.takeRequest()

        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/companies/public_treasury/ethereum")
    }

    @Test(expected = HttpException::class)
    fun `http 404 bubbles up as HttpException`() = runTest {
        server.enqueue(MockResponse().setResponseCode(404).setBody("""{"error":"not found"}"""))
        api.getCompaniesTreasury("unknown")
    }
}
