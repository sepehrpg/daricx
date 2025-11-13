package com.example.network.nfts

import com.example.network.model.nfts.NftDetailsDto
import com.example.network.model.mappers.coins.toDomain
import com.example.network.model.mappers.nfts.toDomain
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class NftDetailsMappersTest {

    @Test
    fun `maps all non-null fields correctly`() {
        // Given
        val dto = fullDto()

        // When
        val domain = dto.toDomain()

        // Then (root)
        assertThat(domain.assetPlatformId).isEqualTo("eth")
        assertThat(domain.bannerImage).isEqualTo("https://img/banner.png")
        assertThat(domain.contractAddress).isEqualTo("0xabc")
        assertThat(domain.description).isEqualTo("desc")
        assertThat(domain.id).isEqualTo("cool-nft")
        assertThat(domain.marketCapRank).isEqualTo(7)
        assertThat(domain.name).isEqualTo("Cool NFT")
        assertThat(domain.nativeCurrency).isEqualTo("ETH")
        assertThat(domain.nativeCurrencySymbol).isEqualTo("Ξ")
        assertThat(domain.numberOfUniqueAddresses).isEqualTo(12345.0)
        assertThat(domain.numberOfUniqueAddresses24hPercentageChange).isEqualTo(12.5)
        assertThat(domain.oneDayAverageSalePrice).isEqualTo(0.75)
        assertThat(domain.oneDayAverageSalePrice24hPercentageChange).isEqualTo(1.2)
        assertThat(domain.oneDaySales).isEqualTo(123.0)
        assertThat(domain.oneDaySales24hPercentageChange).isEqualTo(2.3)
        assertThat(domain.symbol).isEqualTo("COOL")
        assertThat(domain.totalSupply).isEqualTo(7777.0)
        assertThat(domain.userFavoritesCount).isEqualTo(99)
        assertThat(domain.volumeInUsd24hPercentageChange).isEqualTo(8.8)
        assertThat(domain.webSlug).isEqualTo("cool-nft")

        // Nested: ath
        assertThat(domain.ath?.nativeCurrency).isEqualTo(12.34)
        assertThat(domain.ath?.usd).isEqualTo(100)

        // Nested: athChangePercentage
        assertThat(domain.athChangePercentage?.nativeCurrency).isEqualTo(1.5)
        assertThat(domain.athChangePercentage?.usd).isEqualTo(2.5)

        // Nested: athDate
        assertThat(domain.athDate?.nativeCurrency).isEqualTo("2020-01-01")
        assertThat(domain.athDate?.usd).isEqualTo("2020-01-02")

        // Nested: explorers (list)
        assertThat(domain.explorers).hasSize(1)
        assertThat(domain.explorers?.get(0)?.link).isEqualTo("https://scan")
        assertThat(domain.explorers?.get(0)?.name).isEqualTo("Etherscan")

        // Nested: floor price family
        assertThat(domain.floorPrice?.nativeCurrency).isEqualTo(0.5)
        assertThat(domain.floorPrice?.usd).isEqualTo(1)
        assertThat(domain.floorPrice14dPercentageChange?.nativeCurrency).isEqualTo(1.1)
        assertThat(domain.floorPrice14dPercentageChange?.usd).isEqualTo(2.2)
        assertThat(domain.floorPrice1yPercentageChange?.nativeCurrency).isEqualTo(1.9)
        assertThat(domain.floorPrice1yPercentageChange?.usd).isEqualTo(2.9)
        assertThat(domain.floorPrice24hPercentageChange?.nativeCurrency).isEqualTo(0.2)
        assertThat(domain.floorPrice24hPercentageChange?.usd).isEqualTo(0.3)
        assertThat(domain.floorPrice30dPercentageChange?.nativeCurrency).isEqualTo(0.7)
        assertThat(domain.floorPrice30dPercentageChange?.usd).isEqualTo(0.8)
        assertThat(domain.floorPrice60dPercentageChange?.nativeCurrency).isEqualTo(1.2)
        assertThat(domain.floorPrice60dPercentageChange?.usd).isEqualTo(1.3)
        assertThat(domain.floorPrice7dPercentageChange?.nativeCurrency).isEqualTo(0.4)
        assertThat(domain.floorPrice7dPercentageChange?.usd).isEqualTo(0.5)
        assertThat(domain.floorPriceInUsd24hPercentageChange).isEqualTo(9.99)

        // Nested: image
        assertThat(domain.image?.small).isEqualTo("https://img/small.png")
        assertThat(domain.image?.small2x).isEqualTo("https://img/small@2x.png")

        // Nested: links
        assertThat(domain.links?.discord).isEqualTo("discord.gg/x")
        assertThat(domain.links?.homepage).isEqualTo("https://site.com")
        assertThat(domain.links?.twitter).isEqualTo("@twitter")

        // Nested: market cap & changes
        assertThat(domain.marketCap?.nativeCurrency).isEqualTo(1000)
        assertThat(domain.marketCap?.usd).isEqualTo(2000)
        assertThat(domain.marketCap24hPercentageChange?.nativeCurrency).isEqualTo(3.3)
        assertThat(domain.marketCap24hPercentageChange?.usd).isEqualTo(4.4)

        // Nested: volume
        assertThat(domain.volume24h?.nativeCurrency).isEqualTo(22.0)
        assertThat(domain.volume24h?.usd).isEqualTo(100)
        assertThat(domain.volume24hPercentageChange?.nativeCurrency).isEqualTo(5.0)
        assertThat(domain.volume24hPercentageChange?.usd).isEqualTo(6.0)
    }

    @Test
    fun `null-safe mapping - every field null remains null`() {
        // Given: an all-null DTO
        val dto = NftDetailsDto(
            assetPlatformId = null,
            ath = null,
            athChangePercentage = null,
            athDate = null,
            bannerImage = null,
            contractAddress = null,
            description = null,
            explorers = null,
            floorPrice = null,
            floorPrice14dPercentageChange = null,
            floorPrice1yPercentageChange = null,
            floorPrice24hPercentageChange = null,
            floorPrice30dPercentageChange = null,
            floorPrice60dPercentageChange = null,
            floorPrice7dPercentageChange = null,
            floorPriceInUsd24hPercentageChange = null,
            id = null,
            image = null,
            links = null,
            marketCap = null,
            marketCap24hPercentageChange = null,
            marketCapRank = null,
            name = null,
            nativeCurrency = null,
            nativeCurrencySymbol = null,
            numberOfUniqueAddresses = null,
            numberOfUniqueAddresses24hPercentageChange = null,
            oneDayAverageSalePrice = null,
            oneDayAverageSalePrice24hPercentageChange = null,
            oneDaySales = null,
            oneDaySales24hPercentageChange = null,
            symbol = null,
            totalSupply = null,
            userFavoritesCount = null,
            volume24h = null,
            volume24hPercentageChange = null,
            volumeInUsd24hPercentageChange = null,
            webSlug = null
        )

        // When
        val domain = dto.toDomain()

        // Then: spot checks
        assertThat(domain.assetPlatformId).isNull()
        assertThat(domain.ath).isNull()
        assertThat(domain.explorers).isNull()
        assertThat(domain.image).isNull()
        assertThat(domain.links).isNull()
        assertThat(domain.marketCap).isNull()
        assertThat(domain.volume24h).isNull()
        assertThat(domain.name).isNull()
    }

    @Test
    fun `list mapping - preserves size and null items`() {
        // Given
        val dto = minimalNulls().copy(
            explorers = listOf(
                NftDetailsDto.Explorer(link = "L1", name = "N1"),
                null
            )
        )

        // When
        val domain = dto.toDomain()

        // Then
        assertThat(domain.explorers).hasSize(2)
        assertThat(domain.explorers?.get(0)?.link).isEqualTo("L1")
        assertThat(domain.explorers?.get(0)?.name).isEqualTo("N1")
        assertThat(domain.explorers?.get(1)).isNull()
    }

    @Test
    fun `partial nested nulls - maps available fields and keeps nulls`() {
        // Given
        val dto = minimalNulls().copy(
            image = NftDetailsDto.Image(
                small = "s.png",
                small2x = null
            ),
            links = NftDetailsDto.Links(
                discord = null,
                homepage = "https://home",
                twitter = null
            ),
            floorPrice = NftDetailsDto.FloorPrice(
                nativeCurrency = null,
                usd = 42
            )
        )

        // When
        val domain = dto.toDomain()

        // Then
        assertThat(domain.image?.small).isEqualTo("s.png")
        assertThat(domain.image?.small2x).isNull()
        assertThat(domain.links?.homepage).isEqualTo("https://home")
        assertThat(domain.links?.discord).isNull()
        assertThat(domain.floorPrice?.usd).isEqualTo(42)
        assertThat(domain.floorPrice?.nativeCurrency).isNull()
    }

    // ----------------- Helpers -----------------

    private fun fullDto() = NftDetailsDto(
        assetPlatformId = "eth",
        ath = NftDetailsDto.Ath(nativeCurrency = 12.34, usd = 100),
        athChangePercentage = NftDetailsDto.AthChangePercentage(nativeCurrency = 1.5, usd = 2.5),
        athDate = NftDetailsDto.AthDate(nativeCurrency = "2020-01-01", usd = "2020-01-02"),
        bannerImage = "https://img/banner.png",
        contractAddress = "0xabc",
        description = "desc",
        explorers = listOf(
            NftDetailsDto.Explorer(link = "https://scan", name = "Etherscan")
        ),
        floorPrice = NftDetailsDto.FloorPrice(nativeCurrency = 0.5, usd = 1),
        floorPrice14dPercentageChange = NftDetailsDto.FloorPrice14dPercentageChange(1.1, 2.2),
        floorPrice1yPercentageChange = NftDetailsDto.FloorPrice1yPercentageChange(1.9, 2.9),
        floorPrice24hPercentageChange = NftDetailsDto.FloorPrice24hPercentageChange(0.2, 0.3),
        floorPrice30dPercentageChange = NftDetailsDto.FloorPrice30dPercentageChange(0.7, 0.8),
        floorPrice60dPercentageChange = NftDetailsDto.FloorPrice60dPercentageChange(1.2, 1.3),
        floorPrice7dPercentageChange = NftDetailsDto.FloorPrice7dPercentageChange(0.4, 0.5),
        floorPriceInUsd24hPercentageChange = 9.99,
        id = "cool-nft",
        image = NftDetailsDto.Image(
            small = "https://img/small.png",
            small2x = "https://img/small@2x.png"
        ),
        links = NftDetailsDto.Links(
            discord = "discord.gg/x",
            homepage = "https://site.com",
            twitter = "@twitter"
        ),
        marketCap = NftDetailsDto.MarketCap(nativeCurrency = 1000, usd = 2000),
        marketCap24hPercentageChange = NftDetailsDto.MarketCap24hPercentageChange(3.3, 4.4),
        marketCapRank = 7,
        name = "Cool NFT",
        nativeCurrency = "ETH",
        nativeCurrencySymbol = "Ξ",
        numberOfUniqueAddresses = 12345.0,
        numberOfUniqueAddresses24hPercentageChange = 12.5,
        oneDayAverageSalePrice = 0.75,
        oneDayAverageSalePrice24hPercentageChange = 1.2,
        oneDaySales = 123.0,
        oneDaySales24hPercentageChange = 2.3,
        symbol = "COOL",
        totalSupply = 7777.0,
        userFavoritesCount = 99,
        volume24h = NftDetailsDto.Volume24h(nativeCurrency = 22.0, usd = 100),
        volume24hPercentageChange = NftDetailsDto.Volume24hPercentageChange(5.0, 6.0),
        volumeInUsd24hPercentageChange = 8.8,
        webSlug = "cool-nft"
    )

    private fun minimalNulls() = NftDetailsDto(
        assetPlatformId = null,
        ath = null,
        athChangePercentage = null,
        athDate = null,
        bannerImage = null,
        contractAddress = null,
        description = null,
        explorers = null,
        floorPrice = null,
        floorPrice14dPercentageChange = null,
        floorPrice1yPercentageChange = null,
        floorPrice24hPercentageChange = null,
        floorPrice30dPercentageChange = null,
        floorPrice60dPercentageChange = null,
        floorPrice7dPercentageChange = null,
        floorPriceInUsd24hPercentageChange = null,
        id = null,
        image = null,
        links = null,
        marketCap = null,
        marketCap24hPercentageChange = null,
        marketCapRank = null,
        name = null,
        nativeCurrency = null,
        nativeCurrencySymbol = null,
        numberOfUniqueAddresses = null,
        numberOfUniqueAddresses24hPercentageChange = null,
        oneDayAverageSalePrice = null,
        oneDayAverageSalePrice24hPercentageChange = null,
        oneDaySales = null,
        oneDaySales24hPercentageChange = null,
        symbol = null,
        totalSupply = null,
        userFavoritesCount = null,
        volume24h = null,
        volume24hPercentageChange = null,
        volumeInUsd24hPercentageChange = null,
        webSlug = null
    )
}
