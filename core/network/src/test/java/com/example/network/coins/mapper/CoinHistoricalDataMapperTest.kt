// File: src/test/java/com/example/network/mapper/CoinHistoricalDataDtoMapperTest.kt
package com.example.network.coins.mapper

import com.example.network.model.coins.CoinHistoricalDataDto
import com.example.network.model.mappers.coins.toDomain
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


class CoinHistoricalDataDtoMapperTest {

    @Test
    fun `dto_to_domain_maps_all_fields`() {
        val dto = CoinHistoricalDataDto(
            communityData = CoinHistoricalDataDto.CommunityData(
                facebookLikes = JsonPrimitive(1234),
                redditAccountsActive48h = JsonPrimitive("56.5"),
                redditAverageComments48h = 7.0,
                redditAveragePosts48h = 2.0,
                redditSubscribers = JsonNull
            ),
            developerData = CoinHistoricalDataDto.DeveloperData(
                closedIssues = 10,
                codeAdditionsDeletions4Weeks =
                    CoinHistoricalDataDto.DeveloperData.CodeAdditionsDeletions4Weeks(100, 40),
                commitCount4Weeks = 20,
                forks = 5,
                pullRequestContributors = 3,
                pullRequestsMerged = 8,
                stars = 99,
                subscribers = 12,
                totalIssues = 50
            ),
            id = "bitcoin",
            image = CoinHistoricalDataDto.Image("small.png", "thumb.png"),
            localization = CoinHistoricalDataDto.Localization(
                translations = mapOf("en" to "Bitcoin", "fa" to "بیت‌کوین")
            ),
            marketData = CoinHistoricalDataDto.MarketData(
                currentPrice = CoinHistoricalDataDto.MarketData.CurrentPrice(
                    mapOf("usd" to 68000.0, "eur" to 62000.5)
                ),
                marketCap = CoinHistoricalDataDto.MarketData.MarketCap(
                    mapOf("usd" to 1_300_000_000_000.0)
                ),
                totalVolume = CoinHistoricalDataDto.MarketData.TotalVolume(
                    mapOf("usd" to 25_000_000_000.0)
                )
            ),
            name = "Bitcoin",
            publicInterestStats = CoinHistoricalDataDto.PublicInterestStats(
                alexaRank = JsonPrimitive(200),
                bingMatches = JsonPrimitive(true)
            ),
            symbol = "btc"
        )

        val domain = dto.toDomain()

        // CommunityData
        assertEquals(1234L, domain.communityData?.facebookLikes)
        assertEquals(56.5, domain.communityData?.redditAccountsActive48h)
        assertEquals(7.0, domain.communityData?.redditAverageComments48h)
        assertEquals(2.0, domain.communityData?.redditAveragePosts48h)
        assertNull(domain.communityData?.redditSubscribers)

        // DeveloperData
        assertEquals(10, domain.developerData?.closedIssues)
        assertEquals(100, domain.developerData?.codeAdditionsDeletions4Weeks?.additions)
        assertEquals(40, domain.developerData?.codeAdditionsDeletions4Weeks?.deletions)
        assertEquals(20, domain.developerData?.commitCount4Weeks)
        assertEquals(5, domain.developerData?.forks)
        assertEquals(3, domain.developerData?.pullRequestContributors)
        assertEquals(8, domain.developerData?.pullRequestsMerged)
        assertEquals(99, domain.developerData?.stars)
        assertEquals(12, domain.developerData?.subscribers)
        assertEquals(50, domain.developerData?.totalIssues)

        assertEquals("bitcoin", domain.id)
        assertEquals("small.png", domain.image?.small)
        assertEquals("thumb.png", domain.image?.thumb)
        assertEquals(mapOf("en" to "Bitcoin", "fa" to "بیت‌کوین"), domain.localization?.translations)
        assertEquals(68000.0, domain.marketData?.currentPrice?.currentPrice?.get("usd"))
        assertEquals(1_300_000_000_000.0, domain.marketData?.marketCap?.marketCap?.get("usd"))
        assertEquals(25_000_000_000.0, domain.marketData?.totalVolume?.totalVolume?.get("usd"))
        assertEquals("Bitcoin", domain.name)
        assertEquals(200L, domain.publicInterestStats?.alexaRank)
        assertEquals(true, domain.publicInterestStats?.bingMatches)
        assertEquals("btc", domain.symbol)
    }

    @Test
    fun `dto_to_domain_handles_nulls_safely`() {
        val dto = CoinHistoricalDataDto(
            communityData = null,
            developerData = null,
            id = null,
            image = null,
            localization = null,
            marketData = null,
            name = null,
            publicInterestStats = null,
            symbol = null
        )

        val domain = dto.toDomain()
        assertNull(domain.communityData)
        assertNull(domain.developerData)
        assertNull(domain.id)
        assertNull(domain.image)
        assertNull(domain.localization)
        assertNull(domain.marketData)
        assertNull(domain.name)
        assertNull(domain.publicInterestStats)
        assertNull(domain.symbol)
    }
}
