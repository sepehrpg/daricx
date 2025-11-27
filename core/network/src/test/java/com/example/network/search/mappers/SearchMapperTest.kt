package com.example.network.search.mappers

import com.example.model.Search
import com.example.network.model.SearchDto
import com.example.network.model.mappers.toDomain
import com.google.common.truth.Truth
import org.junit.Test

/**
 * SearchMapperTest
 *
 * Test Goal:
 * - Verify mapping from SearchDto -> Search domain model.
 *
 * Scenarios:
 * 1) Map populated DTO -> domain with nested entities.
 * 2) Map empty/null fields -> safe handling.
 */
class SearchMapperTest {

    @Test
    fun `maps populated dto`() {
        val dto = SearchDto(
            coins = listOf(
                SearchDto.Coin(
                    id = "btc",
                    name = "Bitcoin",
                    apiSymbol = "bitcoin",
                    symbol = "BTC",
                    marketCapRank = 1,
                    thumb = "thumb",
                    large = "large"
                )
            ),
            exchanges = listOf(
                SearchDto.Exchange(
                    id = "binance",
                    name = "Binance",
                    marketType = "spot",
                    thumb = "t",
                    large = "l"
                )
            ),
            icos = listOf("ico1"),
            categories = listOf(SearchDto.Category(id = "defi", name = "DeFi")),
            nfts = listOf(
                SearchDto.Nft(
                    id = "bayc",
                    name = "Bored Ape",
                    symbol = "BAYC",
                    thumb = "thumb"
                )
            )
        )

        val domain: Search = dto.toDomain()
        Truth.assertThat(domain.coins?.first()?.id).isEqualTo("btc")
        Truth.assertThat(domain.exchanges?.first()?.name).isEqualTo("Binance")
        Truth.assertThat(domain.categories?.first()?.name).isEqualTo("DeFi")
        Truth.assertThat(domain.nfts?.first()?.symbol).isEqualTo("BAYC")
    }

    @Test
    fun `maps null fields safely`() {
        val dto = SearchDto(
            coins = null, exchanges = null, icos = null,
            categories = null, nfts = null
        )
        val domain = dto.toDomain()
        Truth.assertThat(domain.coins).isNull()
        Truth.assertThat(domain.exchanges).isNull()
    }
}