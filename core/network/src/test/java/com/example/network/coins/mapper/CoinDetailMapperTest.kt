package com.example.network.coins.mapper

import com.example.model.coins.CoinDetails
import com.example.network.model.coins.CoinDetailsDto
import com.example.network.model.mappers.coins.toDomain
import kotlinx.serialization.json.JsonPrimitive
import org.junit.Assert
import org.junit.Test

/**
 * Unit test for verifying CoinDetailsDto → CoinDetails mapping.
 * Covers nested fields, map structures, and safe default handling.
 */
class CoinDetailMapperTest {

    @Test
    fun `map full CoinDto to domain correctly`() {
        val dto = CoinDetailsDto(
            id = "bitcoin",
            symbol = "btc",
            name = "Bitcoin",
            assetPlatformId = JsonPrimitive("bitcoin"),
            blockTimeInMinutes = 10,
            hashingAlgorithm = "SHA-256",
            genesisDate = "2009-01-03",
            countryOrigin = "Global",
            publicNotice = JsonPrimitive("No notice"),
            previewListing = false,
            webSlug = "bitcoin",
            lastUpdated = "2025-10-10T00:00:00Z",

            additionalNotices = listOf(JsonPrimitive("Test notice")),
            categories = listOf("cryptocurrency", "store-of-value"),
            statusUpdates = listOf(JsonPrimitive("ok")),

            // description: {"en": "...", "fa": "..."}
            description = mapOf(
                "en" to "Bitcoin description",
                "fa" to "توضیح بیت‌کوین"
            ),

            // localization: {"en": "Bitcoin", "fa": "بیت کوین"}
            localization = mapOf(
                "en" to "Bitcoin",
                "fa" to "بیت کوین"
            ),

            image = CoinDetailsDto.Image(
                thumb = "thumb_url",
                small = "small_url",
                large = "large_url"
            ),

            links = CoinDetailsDto.Links(
                homepage = listOf("https://bitcoin.org"),
                blockchainSite = listOf("https://blockchain.info"),
                officialForumUrl = listOf("https://bitcointalk.org"),
                announcementUrl = listOf(JsonPrimitive("https://news.com/announce")),
                chatUrl = listOf(JsonPrimitive("https://t.me/bitcoin")),
                reposUrl = CoinDetailsDto.Links.ReposUrl(
                    github = listOf("https://github.com/bitcoin/bitcoin"),
                    bitbucket = listOf(JsonPrimitive("https://bitbucket.org/bitcoin"))
                ),
                bitcointalkThreadIdentifier = JsonPrimitive("thread123"),
                telegramChannelIdentifier = "@bitcoin",
                twitterScreenName = "bitcoin",
                facebookUsername = "bitcoin.fb",
                subredditUrl = "https://reddit.com/r/bitcoin",
                snapshotUrl = JsonPrimitive("https://snapshot.com"),
                whitepaper = "https://bitcoin.org/bitcoin.pdf"
            ),

            // platforms: {"ethereum": "0x..."}
            platforms = mapOf(
                "ethereum" to "0xabc"
            ),

            // detail_platforms: {"ethereum": { contract_address, decimal_place }, ...}
            detailPlatforms = mapOf(
                "ethereum" to CoinDetailsDto.DetailPlatformDto(
                    contractAddress = "0xabc",
                    decimalPlace = JsonPrimitive(18)
                )
            ),

            communityData = CoinDetailsDto.CommunityData(
                facebookLikes = JsonPrimitive("10000"),
                redditAccountsActive48h = 200,
                redditAverageComments48h = 5.2,
                redditAveragePosts48h = 2.3,
                redditSubscribers = 500000,
                telegramChannelUserCount = JsonPrimitive("25000")
            ),

            developerData = CoinDetailsDto.DeveloperData(
                forks = 1200,
                stars = 34000,
                subscribers = 100,
                totalIssues = 5000,
                closedIssues = 4500,
                pullRequestsMerged = 3000,
                pullRequestContributors = 250,
                commitCount4Weeks = 40,
                last4WeeksCommitActivitySeries = listOf(
                    JsonPrimitive("day1"),
                    JsonPrimitive("day2")
                ),
                codeAdditionsDeletions4Weeks = CoinDetailsDto.DeveloperData.CodeAdditionsDeletions4Weeks(
                    additions = 120,
                    deletions = 80
                )
            ),

            marketCapRank = 1,
            watchlistPortfolioUsers = 1_000_000,
            sentimentVotesUpPercentage = 80.5,
            sentimentVotesDownPercentage = 19.5,

            tickers = listOf(
                CoinDetailsDto.Ticker(
                    base = "BTC",
                    target = "USD",
                    market = CoinDetailsDto.Ticker.Market(
                        identifier = "binance",
                        name = "Binance",
                        hasTradingIncentive = true
                    ),
                    last = 50000.0,
                    volume = 25000.0,
                    convertedLast = CoinDetailsDto.Ticker.ConvertedLast(
                        btc = 1.0,
                        eth = 15.0,
                        usd = 50000.0
                    ),
                    convertedVolume = CoinDetailsDto.Ticker.ConvertedVolume(
                        btc = 25000.0,
                        eth = 1500.0,
                        usd = 100_000_000.0
                    ),
                    trustScore = "green",
                    bidAskSpreadPercentage = 0.02,
                    isAnomaly = false,
                    isStale = false,
                    timestamp = "2025-10-10T00:00:00Z",
                    lastFetchAt = "2025-10-10T00:00:01Z",
                    lastTradedAt = "2025-10-10T00:00:02Z",
                    tokenInfoUrl = JsonPrimitive("https://info.com/token"),
                    tradeUrl = "https://binance.com/trade/BTC_USD",
                    coinId = "bitcoin",
                    coinMcapUsd = 1_000_000_000.0,
                    targetCoinId = null
                )
            ),

            marketData = CoinDetailsDto.MarketData(
                currentPrice = mapOf("usd" to 50000.0),
                ath = mapOf("usd" to 69000.0),
                athChangePercentage = mapOf("usd" to -27.5),
                athDate = mapOf("usd" to "2021-11-10"),
                atl = mapOf("usd" to 65.0),
                atlChangePercentage = mapOf("usd" to 76800.0),
                atlDate = mapOf("usd" to "2013-07-06"),
                marketCap = mapOf("usd" to 1_000_000_000_000.0),
                totalVolume = mapOf("usd" to 50_000_000_000.0),
                high24h = mapOf("usd" to 50500.0),
                low24h = mapOf("usd" to 49500.0),
                priceChange24h = 1000.0,
                priceChange24hInCurrency = mapOf("usd" to 1000.0),
                priceChangePercentage24h = 2.0,
                priceChangePercentage24hInCurrency = mapOf("usd" to 2.0),
                fullyDilutedValuation = mapOf("usd" to 1_200_000_000_000.0),
                totalSupply = 21_000_000.0,
                circulatingSupply = 19_000_000.0,
                maxSupply = 21_000_000.0,
                maxSupplyInfinite = false,
                marketCapRank = 1,
                marketCapChange24h = 2_000_000_000.0,
                marketCapChange24hInCurrency = mapOf("usd" to 2_000_000_000.0),
                marketCapChangePercentage24hInCurrency = mapOf("usd" to 0.2),
                lastUpdated = "2025-10-10T00:00:00Z"
            )
        )

        val coin: CoinDetails = dto.toDomain()

        // Core identifiers
        Assert.assertEquals("bitcoin", coin.id)
        Assert.assertEquals("btc", coin.symbol)
        Assert.assertEquals("Bitcoin", coin.name)
        Assert.assertEquals("bitcoin", coin.assetPlatformId)
        Assert.assertEquals("SHA-256", coin.hashingAlgorithm)
        Assert.assertEquals(10, coin.blockTimeInMinutes)
        Assert.assertEquals("2009-01-03", coin.genesisDate)
        Assert.assertEquals("Global", coin.countryOrigin)

        // Image mapping
        Assert.assertEquals("large_url", coin.image?.large)

        // Links mapping
        Assert.assertEquals("https://bitcoin.org", coin.links?.homepage?.first())
        Assert.assertEquals(
            "https://github.com/bitcoin/bitcoin",
            coin.links?.reposUrl?.github?.first()
        )

        // CommunityData
        Assert.assertEquals("10000", coin.communityData?.facebookLikes)
        Assert.assertEquals(500000, coin.communityData?.redditSubscribers)

        // DeveloperData
        Assert.assertEquals(1200, coin.developerData?.forks)
        Assert.assertEquals(120, coin.developerData?.codeAdditions)
        Assert.assertEquals(80, coin.developerData?.codeDeletions)

        // MarketData basic checks
        val md = coin.marketData!!
        Assert.assertEquals(50000.0, md.currentPrice?.get("usd"))
        Assert.assertEquals(69000.0, md.ath?.get("usd"))
        Assert.assertEquals(50500.0, md.high24h?.get("usd"))
        Assert.assertEquals(49500.0, md.low24h?.get("usd"))
        Assert.assertEquals(1_000_000_000_000.0, md.marketCap?.get("usd"))
        Assert.assertEquals(50_000_000_000.0, md.totalVolume?.get("usd"))

        // Ticker checks
        val ticker = coin.tickers?.first()!!
        Assert.assertEquals("BTC", ticker.base)
        Assert.assertEquals("USD", ticker.target)
        Assert.assertEquals(50000.0, ticker.last)
        Assert.assertEquals("binance", ticker.market?.identifier)
        Assert.assertEquals(1.0, ticker.convertedLast?.btc)
        Assert.assertEquals(15.0, ticker.convertedLast?.eth)
        Assert.assertEquals(50000.0, ticker.convertedLast?.usd)
        Assert.assertEquals(25000.0, ticker.convertedVolume?.btc)
        Assert.assertEquals(1500.0, ticker.convertedVolume?.eth?.toDouble())

        // Miscellaneous
        Assert.assertEquals(1, coin.marketCapRank)
        Assert.assertEquals(80.5, coin.sentimentVotesUpPercentage)
        Assert.assertEquals(19.5, coin.sentimentVotesDownPercentage)
    }
}