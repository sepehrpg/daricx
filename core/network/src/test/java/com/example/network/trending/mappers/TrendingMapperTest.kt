package com.example.network.trending.mappers

import com.example.model.Trending
import com.example.network.model.TrendingDto
import com.example.network.model.mappers.toDomain
import com.google.common.truth.Truth
import org.junit.Test

/**
 * TrendingMapperTest
 *
 * Test Goal:
 * - Verify DTO -> Domain mapping for categories, coins, nfts.
 *
 * Scenarios:
 * 1) Populated DTO maps to domain with nested objects.
 * 2) Null fields map safely without crash.
 */
class TrendingMapperTest {

    @Test
    fun `maps populated dto`() {
        val dto = TrendingDto(
            categories = listOf(
                TrendingDto.Category(
                    coinsCount = 5,
                    data = TrendingDto.Category.Data(
                        marketCap = 1000.0,
                        marketCapBtc = 50.0,
                        marketCapChangePercentage24h = mapOf("usd" to 2.5),
                        sparkline = "sparkline",
                        totalVolume = 500.0,
                        totalVolumeBtc = 25.0
                    ),
                    id = 1,
                    marketCap1hChange = 0.5,
                    name = "DeFi",
                    slug = "defi"
                )
            ),
            coins = listOf(
                TrendingDto.Coin(
                    item = TrendingDto.Coin.Item(
                        coinId = 1,
                        data = TrendingDto.Coin.Item.Data(
                            content = TrendingDto.Coin.Item.Data.Content(
                                description = "desc",
                                title = "title"
                            ),
                            marketCap = "1000",
                            marketCapBtc = "50",
                            price = 50000.0,
                            priceBtc = "1.0",
                            priceChangePercentage24h = mapOf("usd" to 1.2),
                            sparkline = "spark",
                            totalVolume = "100",
                            totalVolumeBtc = "5"
                        ),
                        id = "btc",
                        large = "large.png",
                        marketCapRank = 1,
                        name = "Bitcoin",
                        priceBtc = 1.0,
                        score = 0,
                        slug = "bitcoin",
                        small = "small.png",
                        symbol = "BTC",
                        thumb = "thumb.png"
                    )
                )
            ),
            nfts = listOf(
                TrendingDto.Nft(
                    data = TrendingDto.Nft.Data(
                        content = TrendingDto.Nft.Data.Content("desc", "title"),
                        floorPrice = "10",
                        floorPriceInUsd24hPercentageChange = "2.0",
                        h24AverageSalePrice = "5",
                        h24Volume = "100",
                        sparkline = "sparkline"
                    ),
                    floorPrice24hPercentageChange = 1.5,
                    floorPriceInNativeCurrency = 0.01,
                    id = "bayc",
                    name = "Bored Ape Yacht Club",
                    nativeCurrencySymbol = "ETH",
                    nftContractId = 123,
                    symbol = "BAYC",
                    thumb = "thumb.png"
                )
            )
        )

        val domain: Trending? = dto.toDomain()
        Truth.assertThat(domain!!.categories!!.first().name).isEqualTo("DeFi")
        Truth.assertThat(domain.coins!!.first().item?.name).isEqualTo("Bitcoin")
        Truth.assertThat(domain.nfts!!.first().symbol).isEqualTo("BAYC")
    }

    @Test
    fun `maps null fields safely`() {
        val dto = TrendingDto(categories = null, coins = null, nfts = null)
        val domain = dto.toDomain()
        Truth.assertThat(domain?.categories).isNull()
        Truth.assertThat(domain?.coins).isNull()
        Truth.assertThat(domain?.nfts).isNull()
    }
}