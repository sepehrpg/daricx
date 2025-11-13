package com.example.network.companies


import com.example.model.option.TreasuryAsset
import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.example.network.datasource.companies.CompaniesTreasuryDataSource
import com.example.network.datasource.companies.CompaniesTreasuryDataSourceImpl
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
 * CompaniesTreasuryDataSourceImplTest
 *
 * Test Goal:
 * - Ensure DataSource maps TreasuryAsset enum → coin_id path correctly.
 * - Ensure errors from API propagate as HttpException.
 *
 * Scenarios:
 * 1) TreasuryAsset.Bitcoin → "/companies/public_treasury/bitcoin".
 * 2) TreasuryAsset.Ethereum → "/companies/public_treasury/ethereum".
 * 3) HTTP 500 response propagates as HttpException.
 */
class CompaniesTreasuryDataSourceImplTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService
    private lateinit var ds: CompaniesTreasuryDataSource

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
        ds  = CompaniesTreasuryDataSourceImpl(api)
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `Bitcoin maps to path coin_id bitcoin`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCompanies.btcTreasuryResponse))

        ds.getCompaniesTreasury(TreasuryAsset.Bitcoin)
        val req = server.takeRequest()
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/companies/public_treasury/bitcoin")
    }

    @Test fun `Ethereum maps to path coin_id ethereum`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonCompanies.ethTreasuryResponse))

        ds.getCompaniesTreasury(TreasuryAsset.Ethereum)
        val req = server.takeRequest()
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/companies/public_treasury/ethereum")
    }

    @Test(expected = HttpException::class)
    fun `http 500 bubbles as HttpException`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("""{"error":"server"}"""))
        ds.getCompaniesTreasury(TreasuryAsset.Bitcoin)
    }
}
