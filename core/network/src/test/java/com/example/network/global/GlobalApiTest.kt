package com.example.network.global


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
 * GlobalApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for GET /global.
 *
 * Scenarios:
 * 1) Sends GET to the exact path "/global" with no query params.
 * 2) Deserializes a valid JSON payload into GlobalCryptoMarketDataDto.
 * 3) Propagates non-2xx responses as HttpException (covered in DataSource test).
 */
class GlobalApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
    }

    @After fun tearDown() { server.shutdown() }

    @Test
    fun `GET hits correct path with no query`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonGlobal.globalResponse))

        api.getGlobal()
        val req = server.takeRequest()

        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/global")
        assertThat(req.requestUrl?.query).isNull()
    }

    @Test
    fun `deserializes payload`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonGlobal.globalResponse))

        val dto = api.getGlobal()
        val data = dto.data!!
        assertThat(data.activeCryptocurrencies).isEqualTo(12045)
        assertThat(data.marketCapChangePercentage24hUsd).isWithin(0.001).of(-0.56)
        assertThat(data.totalMarketCap?.get("usd")).isWithin(0.001).of(2_350_000_000_000.0)
        assertThat(data.marketCapPercentage?.get("btc")).isWithin(0.0001).of(52.1)
        assertThat(data.updatedAt).isEqualTo(1_725_148_800L)
    }
}
