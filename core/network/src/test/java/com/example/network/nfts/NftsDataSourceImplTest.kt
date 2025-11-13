package com.example.network.nfts


import com.example.model.sort.NftsSort
import com.example.network.TestNetwork
import com.example.network.api.ApiService
import com.example.network.datasource.nfts.NftsDataSource
import com.example.network.datasource.nfts.NftsDataSourceImpl
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
 * NftsDataSourceImplTest
 *
 * Test Goal:
 * - Ensure DataSource forwards paging and maps NftsSort -> "order" query via toApiOrderParam().
 * - When order=null, omit the "order" query.
 * - Propagate HTTP errors as HttpException.
 *
 * Scenarios:
 * 1) order=MarketCapUsdDesc -> "market_cap_usd_desc" appears in query; paging params forwarded.
 * 2) order=null -> no "order" query param.
 * 3) HTTP 500 -> throws HttpException.
 */
class NftsDataSourceImplTest {

    private lateinit var server: MockWebServer
    private lateinit var api: ApiService
    private lateinit var ds: NftsDataSource

    @Before fun setUp() {
        server = MockWebServer().apply { start() }
        api = TestNetwork.retrofit(server.url("/").toString()).create()
        ds  = NftsDataSourceImpl(api)
    }

    @After fun tearDown() { server.shutdown() }

    @Test fun `maps MarketCapUsdDesc and forwards paging`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonNfts.listResponse))

        ds.getNftsList(page = 3, perPage = 100, order = NftsSort.MarketCapUsdDesc)

        val req = server.takeRequest()
        assertThat(req.requestUrl?.encodedPath).isEqualTo("/nfts/list")
        assertThat(req.requestUrl?.queryParameter("order")).isEqualTo("market_cap_usd_desc")
        assertThat(req.requestUrl?.queryParameter("page")).isEqualTo("3")
        assertThat(req.requestUrl?.queryParameter("per_page")).isEqualTo("100")
    }

    @Test fun `null order omits query param`() = runTest {
        server.enqueue(MockResponse().setBody(SampleJsonNfts.listResponse))

        ds.getNftsList(page = 1, perPage = 25, order = null)

        val req = server.takeRequest()
        assertThat(req.requestUrl?.queryParameter("order")).isNull()
        assertThat(req.requestUrl?.queryParameter("page")).isEqualTo("1")
        assertThat(req.requestUrl?.queryParameter("per_page")).isEqualTo("25")
    }

    @Test(expected = HttpException::class)
    fun `http 500 bubbles as HttpException`() = runTest {
        server.enqueue(MockResponse().setResponseCode(500).setBody("""{"error":"server"}"""))
        ds.getNftsList(page = 1, perPage = 10, order = NftsSort.H24VolumeUsdAsc)
    }
}
