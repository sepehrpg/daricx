package com.example.network.defi


import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.example.network.datasource.defi.GlobalDeFiDataSource
import com.example.network.datasource.defi.GlobalDeFiDataSourceImpl
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
 * GlobalDeFiDataSourceImplTest
 *
 * Test Goal:
 * - Ensure DataSource calls the correct endpoint and propagates errors.
 *
 * Scenarios:
 * 1) Success 200 → path is correct and payload is returned (e.g., top_coin_name).
 * 2) HTTP 500 → throws HttpException.
 */
class GlobalDeFiDataSourceImplTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService
    private lateinit var ds: GlobalDeFiDataSource

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
        ds  = GlobalDeFiDataSourceImpl(api)
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `calls endpoint and returns payload`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonDeFi.defiResponse))

        val dto = ds.getGlobalDeFi()
        val req = server.takeRequest()
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/global/decentralized_finance_defi")
        assertThat(dto.data?.topCoinName).isEqualTo("Lido Staked Ether")
        assertThat(dto.data?.topCoinDefiDominance).isWithin(0.000001).of(30.589442518868)
    }

    @Test(expected = HttpException::class)
    fun `http 500 bubbles as HttpException`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("""{"error":"server"}"""))
        ds.getGlobalDeFi()
    }
}
