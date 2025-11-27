package com.example.network.defi

import com.example.network.TestNetwork
import com.example.network.api.DeFi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.create

/**
 * DeFiApiTest
 *
 * Test Goal:
 * - Validate HTTP contract for DeFi endpoint.
 * - Verify basic deserialization for GlobalDeFiMarketDataDto.
 */
class DeFiApiTest {

    private lateinit var server: MockWebServer
    private lateinit var api: DeFi

    @Before
    fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork
            .retrofit(server.url("/").toString())
            .create()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    // -------------------------------------------------------------------------
    // GET /global/decentralized_finance_defi
    // -------------------------------------------------------------------------

    @Test
    fun `getGlobalDeFi uses GET and correct path`() = runTest {
        // Arrange
        server.enqueue(
            MockResponse().setBody(SampleJsonDeFi.defiResponse)
        )

        // Act
        api.getGlobalDeFi()

        // Assert request
        val req = server.takeRequest()
        assertThat(req.method).isEqualTo("GET")
        assertThat(req.requestUrl?.encodedPath)
            .isEqualTo("/global/decentralized_finance_defi")

        // No query params expected
        assertThat(req.requestUrl?.querySize).isEqualTo(0)
    }

    @Test
    fun `deserializes global defi json response`() = runTest {
        // Arrange
        server.enqueue(
            MockResponse().setBody(SampleJsonDeFi.defiResponse)
        )

        // Act
        val dto = api.getGlobalDeFi()


        val data = requireNotNull(dto.data)

        // String-based numeric fields
        assertThat(data.defiMarketCap)
            .isEqualTo("105273842288.229620442228701667")
        assertThat(data.ethMarketCap)
            .isEqualTo("406184911478.5772415794509920285")
        assertThat(data.defiToEthRatio)
            .isEqualTo("25.91771366026773")
        assertThat(data.tradingVolume24h)
            .isEqualTo("5046503746.288261")
        assertThat(data.defiDominance)
            .isEqualTo("3.86765030846147")

        // Plain fields
        assertThat(data.topCoinName)
            .isEqualTo("Lido Staked Ether")

        // top_coin_defi_dominance is numeric in JSON → likely Double
        assertThat(data.topCoinDefiDominance)
            .isWithin(0.000001)
            .of(30.589442518868)

        // Unknown fields ("unknown_field_child", "unknown_top") should be ignored
        // If Json { ignoreUnknownKeys = true } is set in TestNetwork, reaching here
        // without exception is enough to prove it works.
    }
}
