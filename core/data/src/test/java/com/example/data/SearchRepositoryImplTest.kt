package com.example.data


import com.example.data.repository.search.SearchRepository
import com.example.data.repository.search.SearchRepositoryImpl
import com.example.model.Search
import com.example.network.datasource.search.SearchDataSource
import com.example.network.model.SearchDto
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

/**
 * Test suite for [SearchRepositoryImpl].
 *
 * 🎯 Goals:
 * - Ensure repository delegates query to [SearchDataSource].
 * - Verify DTO → domain mapping into [Search].
 * - Validate empty / null DTO fields still map safely.
 * - Propagate exceptions from DataSource.
 *
 * 🧪 Scenarios:
 * 1) search returns populated DTO → mapped domain object.
 * 2) search returns empty DTO → domain object with empty lists.
 * 3) search throws IOException → exception propagates.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SearchRepositoryImplTest {

    private lateinit var repository: SearchRepository
    private lateinit var remote: SearchDataSource
    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        remote = mockk(relaxed = true)
        repository = SearchRepositoryImpl(remote, dispatcher)
    }

    private fun sampleDto(): SearchDto = SearchDto(
        coins = listOf(
            SearchDto.Coin(
                id = "eth",
                name = "Ethereum",
                apiSymbol = "ethereum",
                symbol = "ETH",
                marketCapRank = 2,
                thumb = "eth-thumb.png",
                large = "eth-large.png"
            )
        ),
        exchanges = listOf(
            SearchDto.Exchange(
                id = "binance",
                name = "Binance",
                marketType = "spot",
                thumb = "bin-thumb.png",
                large = "bin-large.png"
            )
        ),
        icos = listOf("ico1", "ico2"),
        categories = listOf(
            SearchDto.Category(
                id = "defi",
                name = "DeFi"
            )
        ),
        nfts = listOf(
            SearchDto.Nft(
                id = "cryptopunks",
                name = "CryptoPunks",
                symbol = "PUNK",
                thumb = "punk-thumb.png"
            )
        )
    )

    @Test
    fun `search returns mapped domain result`() = runTest(dispatcher) {
        // Given
        coEvery { remote.search("eth") } returns sampleDto()

        // When
        val result: Search = repository.search("eth")

        // Then
        assertEquals(1, result.coins?.size)
        assertEquals("Ethereum", result.coins?.first()?.name)
        assertEquals("binance", result.exchanges?.first()?.id)
        assertEquals("defi", result.categories?.first()?.id)
        assertEquals("ico1", result.icos?.first())
        assertEquals("cryptopunks", result.nfts?.first()?.id)
    }

    @Test
    fun `search returns empty dto → empty domain lists`() = runTest(dispatcher) {
        // Given
        coEvery { remote.search("empty") } returns SearchDto(
            coins = emptyList(),
            exchanges = emptyList(),
            icos = emptyList(),
            categories = emptyList(),
            nfts = emptyList()
        )

        // When
        val result = repository.search("empty")

        // Then
        assertTrue(result.coins?.isEmpty() == true)
        assertTrue(result.exchanges?.isEmpty() == true)
        assertTrue(result.icos?.isEmpty() == true)
        assertTrue(result.categories?.isEmpty() == true)
        assertTrue(result.nfts?.isEmpty() == true)
    }

    @Test(expected = IOException::class)
    fun `search propagates exception`() = runTest(dispatcher) {
        // Given
        coEvery { remote.search("fail") } throws IOException("boom")

        // When
        repository.search("fail") // should throw
    }
}
