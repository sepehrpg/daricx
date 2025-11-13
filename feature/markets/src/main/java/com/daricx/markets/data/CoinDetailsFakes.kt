// File: com/daricx/markets/ui/data/CoinDetailsFakes.kt
package com.daricx.markets.data

import com.example.model.coins.CoinDetails


object CoinDetailsFakes {

    fun bitcoin(): CoinDetails = CoinDetails(
        id = "bitcoin",
        symbol = "btc",
        name = "Bitcoin",
        assetPlatformId = null,
        blockTimeInMinutes = 10,
        hashingAlgorithm = "SHA-256",
        genesisDate = "2009-01-03",
        countryOrigin = "",
        publicNotice = null,
        previewListing = false,
        webSlug = "bitcoin",
        lastUpdated = "2025-10-02T12:00:00Z",

        additionalNotices = listOf(),
        categories = listOf("Cryptocurrency", "Layer 1 (L1)"),
        statusUpdates = listOf(),

        image = CoinDetails.Image(
            thumb = "https://assets.coingecko.com/coins/images/1/thumb/bitcoin.png",
            small = "https://assets.coingecko.com/coins/images/1/small/bitcoin.png",
            large = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png"
        ),
        links = CoinDetails.Links(
            homepage = listOf("https://bitcoin.org", "https://bitcoincore.org", null),
            blockchainSite = listOf(
                "https://mempool.space/",
                "https://platform.arkhamintelligence.com/explorer/token/bitcoin",
                "https://blockchair.com/bitcoin/",
                "https://btc.com/",
                "https://btc.tokenview.io/",
                "https://www.oklink.com/btc",
                "https://3xpl.com/bitcoin"
            ),
            officialForumUrl = listOf("https://bitcointalk.org/", null, null),
            announcementUrl = listOf("https://blog.bitcoin.org", null),
            chatUrl = listOf("https://t.me/bitcoin", null),
            reposUrl = CoinDetails.Links.ReposUrl(
                github = listOf("https://github.com/bitcoin/bitcoin"),
                bitbucket = listOf()
            ),
            bitcointalkThreadIdentifier = null,
            telegramChannelIdentifier = "bitcoin",
            telegramChannelUserCount = "100000",
            twitterScreenName = "bitcoin",
            facebookUsername = "bitcoins",
            subredditUrl = "https://reddit.com/r/Bitcoin",
            snapshotUrl = null,
            whitepaper = "https://bitcoin.org/bitcoin.pdf"
        ),
        description = CoinDetails.Description(
            translations = mapOf(
                "en" to "Bitcoin is the first successful internet money based on peer-to-peer technology; whereby no central bank or authority is involved in the transaction and production of the Bitcoin currency. It was created by an anonymous individual/group under the name, Satoshi Nakamoto. The source code is available publicly as an open source project, anybody can look at it and be part of the developmental process.\\r\\n\\r\\nBitcoin is changing the way we see money as we speak. The idea was to produce a means of exchange, independent of any central authority, that could be transferred electronically in a secure, verifiable and immutable way. It is a decentralized peer-to-peer internet currency making mobile payment easy, very low transaction fees, protects your identity, and it works anywhere all the time with no central authority and banks.\\r\\n\\r\\nBitcoin is designed to have only 21 million BTC ever created, thus making it a deflationary currency. Bitcoin uses the SHA-256 hashing algorithm with an average transaction confirmation time of 10 minutes. Miners today are mining Bitcoin using ASIC chip dedicated to only mining Bitcoin, and the hash rate has shot up to peta hashes.\\r\\n\\r\\nBeing the first successful online cryptography currency, Bitcoin has inspired other alternative currencies such as Litecoin, Peercoin, Primecoin, and so on.\\r\\n\\r\\nThe cryptocurrency then took off with the innovation of the turing-complete smart contract by Ethereum which led to the development of other amazing projects such as EOS, Tron, and even crypto-collectibles such as CryptoKitties.",
                "fa" to "بیت‌کوین یک ارز دیجیتال غیرمتمرکز بدون بانک مرکزی است."
            )
        ),
        localization = CoinDetails.Localization(
            translations = mapOf("en" to "Bitcoin", "fa" to "بیت‌کوین")
        ),
        platforms = mapOf(
            "native" to "",
            "ethereum" to null
        ),
        detailPlatforms = mapOf(
            "ethereum" to null
        ),

        communityData = CoinDetails.CommunityData(
            facebookLikes = null,
            redditSubscribers = 17000000,
            redditAccountsActive48h = 120000,
            redditAveragePosts48h = 800.0,
            redditAverageComments48h = 12000.0,
            telegramChannelUserCount = "100000"
        ),
        developerData = CoinDetails.DeveloperData(
            forks = 36000,
            stars = 75000,
            subscribers = 4000,
            totalIssues = 10000,
            closedIssues = 9200,
            pullRequestsMerged = 8000,
            pullRequestContributors = 1200,
            commitCount4Weeks = 220,
            last4WeeksCommitActivitySeries = listOf("40", "55", "60", "65"),
            codeAdditions = 12000,
            codeDeletions = 6000
        ),

        marketCapRank = 1,
        watchlistPortfolioUsers = 5000000,
        sentimentVotesUpPercentage = 78.5,
        sentimentVotesDownPercentage = 21.5,

        tickers = listOf(
            ticker(
                base = "BTC", target = "USDT",
                marketId = "binance", marketName = "Binance",
                last = 98765.43, volume = 123456.0,
                trustScore = "green", spreadPct = 0.01,
                timestamp = "2025-10-02T12:00:00Z"
            ),
            ticker(
                base = "BTC", target = "USD",
                marketId = "coinbase", marketName = "Coinbase Exchange",
                last = 98810.50, volume = 45210.0,
                trustScore = "green", spreadPct = 0.02,
                timestamp = "2025-10-02T12:00:05Z"
            ),
            ticker(
                base = "BTC", target = "EUR",
                marketId = "kraken", marketName = "Kraken",
                last = 90210.12, volume = 18750.0,
                trustScore = "green", spreadPct = 0.03,
                timestamp = "2025-10-02T12:00:10Z"
            )
        ),

        marketData = CoinDetails.MarketData(
            currentPrice = mapOf("usd" to 98765.43, "eur" to 90210.12, "btc" to 1.0),
            ath = mapOf("usd" to 102000.0),
            athChangePercentage = mapOf("usd" to -3.5),
            athDate = mapOf("usd" to "2025-09-18T08:30:00Z"),
            atl = mapOf("usd" to 67.81),
            atlChangePercentage = mapOf("usd" to 145000.0),
            atlDate = mapOf("usd" to "2013-07-06T00:00:00Z"),

            marketCap = mapOf("usd" to 1_950_000_000_000.0),
            totalVolume = mapOf("usd" to 38_500_000_000.0),
            high24h = mapOf("usd" to 99500.0),
            low24h = mapOf("usd" to 97000.0),

            priceChange24h = -350.75,
            priceChange24hInCurrency = mapOf("usd" to -350.75),
            priceChangePercentage24h = -0.36,
            priceChangePercentage24hInCurrency = mapOf("usd" to -0.36),

            priceChangePercentage7d = 2.1,
            priceChangePercentage14d = 5.6,
            priceChangePercentage30d = 12.4,
            priceChangePercentage60d = 24.3,
            priceChangePercentage200d = 88.0,
            priceChangePercentage1y = 16.0213,

            fullyDilutedValuation = mapOf("usd" to 2_050_000_000_000.0),
            totalSupply = 21_000_000.0,
            circulatingSupply = 19_700_000.0,
            maxSupply = 21_000_000.0,
            maxSupplyInfinite = false,
            marketCapRank = 1,
            marketCapChange24h = -6_500_000_000.0,
            marketCapChange24hInCurrency = mapOf("usd" to -6_500_000_000.0),
            marketCapChangePercentage24hInCurrency = mapOf("usd" to -0.33),
            lastUpdated = "2025-10-02T12:00:00Z"
        )
    )

    fun generate(index: Int = 0): CoinDetails {
        val baseName = "Coin $index"
        val symbol = "C$index"
        val priceUsd = 10_0 + (index * 7_5) + (index % 3) * 0.37

        return CoinDetails(
            id = "coin-$index",
            symbol = symbol.lowercase(),
            name = baseName,
            assetPlatformId = if (index % 3 == 0) null else "ethereum",
            blockTimeInMinutes = if (index % 2 == 0) 1 else 2,
            hashingAlgorithm = if (index % 2 == 0) "SHA-3" else "PoS",
            genesisDate = "2021-01-0${(index % 9) + 1}",
            countryOrigin = "",
            publicNotice = null,
            previewListing = index % 5 == 0,
            webSlug = "coin-$index",
            lastUpdated = "2025-10-02T12:${(10 + index) % 60}:00Z",

            additionalNotices = listOf(),
            categories = listOf("Category ${(index % 4) + 1}"),
            statusUpdates = listOf(),

            image = CoinDetails.Image(
                thumb = "https://dummyimage.com/64x64/00${index}0/fff&text=${symbol}",
                small = "https://dummyimage.com/128x128/00${index}1/fff&text=${symbol}",
                large = "https://dummyimage.com/256x256/00${index}2/fff&text=${symbol}"
            ),
            links = CoinDetails.Links(
                homepage = listOf("https://example.com/$symbol"),
                blockchainSite = listOf(
                    if (index % 3 == 0) null else "https://etherscan.io/token/0x${index}abcd"
                ),
                officialForumUrl = listOf("https://forum.example.com/$symbol"),
                announcementUrl = listOf("https://blog.example.com/$symbol"),
                chatUrl = listOf("https://t.me/${symbol.lowercase()}"),
                reposUrl = CoinDetails.Links.ReposUrl(
                    github = listOf("https://github.com/example/$symbol"),
                    bitbucket = listOf()
                ),
                bitcointalkThreadIdentifier = null,
                telegramChannelIdentifier = symbol.lowercase(),
                telegramChannelUserCount = "${10_000 + index * 100}",
                twitterScreenName = symbol.lowercase(),
                facebookUsername = symbol.lowercase(),
                subredditUrl = "https://reddit.com/r/${symbol}",
                snapshotUrl = null,
                whitepaper = "https://example.com/$symbol/whitepaper.pdf"
            ),
            description = CoinDetails.Description(
                translations = mapOf(
                    "en" to "$baseName is a sample coin for UI testing.",
                    "fa" to "$baseName یک کوین نمونه برای تست رابط کاربری است."
                )
            ),
            localization = CoinDetails.Localization(
                translations = mapOf("en" to baseName, "fa" to baseName)
            ),
            platforms = if (index % 3 == 0) mapOf("native" to "") else mapOf("ethereum" to "0x${index}abcd"),
            detailPlatforms = if (index % 3 == 0) null else mapOf(
                "ethereum" to CoinDetails.DetailPlatform(
                    contractAddress = "0x${index}abcd",
                    decimalPlace = "18"
                )
            ),

            communityData = CoinDetails.CommunityData(
                facebookLikes = null,
                redditSubscribers = 1_000 + index * 50,
                redditAccountsActive48h = 50 + index,
                redditAveragePosts48h = 2.0 + (index % 3),
                redditAverageComments48h = 15.0 + (index % 7),
                telegramChannelUserCount = "${5_000 + index * 33}"
            ),
            developerData = CoinDetails.DeveloperData(
                forks = 10 + index,
                stars = 20 + index * 2,
                subscribers = 5 + index,
                totalIssues = 100 + index,
                closedIssues = 80 + index,
                pullRequestsMerged = 60 + index,
                pullRequestContributors = 10 + (index % 5),
                commitCount4Weeks = 12 + index,
                last4WeeksCommitActivitySeries = listOf("3", "4", "2", "${3 + (index % 3)}"),
                codeAdditions = 1000 + index * 10,
                codeDeletions = 500 + index * 5
            ),

            marketCapRank = 10 + index,
            watchlistPortfolioUsers = 1000 + index * 20,
            sentimentVotesUpPercentage = 60.0 + (index % 10),
            sentimentVotesDownPercentage = 40.0 - (index % 10),

            tickers = listOf(
                ticker(
                    base = symbol.uppercase(), target = "USDT",
                    marketId = "dex-$index", marketName = "DEX $index",
                    last = priceUsd.toDouble(), volume = 1_000.0 + index * 50,
                    trustScore = "green", spreadPct = 0.10, timestamp = "2025-10-02T12:00:00Z"
                )
            ),

            marketData = CoinDetails.MarketData(
                currentPrice = mapOf("usd" to priceUsd.toDouble(), "eur" to (priceUsd * 0.92)),
                ath = mapOf("usd" to (priceUsd * 1.5)),
                athChangePercentage = mapOf("usd" to -20.0),
                athDate = mapOf("usd" to "2025-05-01T00:00:00Z"),
                atl = mapOf("usd" to (priceUsd * 0.2)),
                atlChangePercentage = mapOf("usd" to 350.0),
                atlDate = mapOf("usd" to "2023-11-01T00:00:00Z"),

                marketCap = mapOf("usd" to (500_000_000L + index * 10_000_000.0)),
                totalVolume = mapOf("usd" to (25_000_000L + index * 1_000_000.0)),
                high24h = mapOf("usd" to (priceUsd * 1.02)),
                low24h = mapOf("usd" to (priceUsd * 0.98)),

                priceChange24h = if (index % 2 == 0) 1.23 else -0.85,
                priceChange24hInCurrency = mapOf("usd" to (if (index % 2 == 0) 1.23 else -0.85)),
                priceChangePercentage24h = if (index % 2 == 0) 0.9 else -0.7,
                priceChangePercentage24hInCurrency = mapOf("usd" to (if (index % 2 == 0) 0.9 else -0.7)),

                priceChangePercentage7d = (index % 7) + 0.5,
                priceChangePercentage14d = (index % 5) + 1.2,
                priceChangePercentage30d = (index % 9) + 3.4,
                priceChangePercentage60d = (index % 11) + 5.6,
                priceChangePercentage200d = (index % 13) + 8.8,
                priceChangePercentage1y = (index % 17) + 12.3,

                fullyDilutedValuation = mapOf("usd" to (900_000_000L + index * 10_000_000.0)),
                totalSupply = 1_000_000_000.0,
                circulatingSupply = 450_000_000.0 + index * 1_000_000.0,
                maxSupply = 1_000_000_000.0,
                maxSupplyInfinite = false,
                marketCapRank = 10 + index,
                marketCapChange24h = if (index % 2 == 0) 5_000_000.0 else -4_000_000.0,
                marketCapChange24hInCurrency = mapOf("usd" to (if (index % 2 == 0) 5_000_000.0 else -4_000_000.0)),
                marketCapChangePercentage24hInCurrency = mapOf("usd" to (if (index % 2 == 0) 1.1 else -0.9)),
                lastUpdated = "2025-10-02T12:${(10 + index) % 60}:00Z"
            )
        )
    }

    val samples: List<CoinDetails> = listOf(
        bitcoin(),
        generate(1),
        generate(2),
        generate(3)
    )

    // ---------------------------
    // Helpers
    // ---------------------------

    private fun ticker(
        base: String,
        target: String,
        marketId: String,
        marketName: String,
        last: Double,
        volume: Double,
        trustScore: String,
        spreadPct: Double,
        timestamp: String
    ): CoinDetails.Ticker = CoinDetails.Ticker(
        base = base,
        target = target,
        market = CoinDetails.Ticker.Market(
            identifier = marketId,
            name = marketName,
            hasTradingIncentive = false
        ),
        last = last,
        volume = volume,
        convertedLast = CoinDetails.Ticker.ConvertedLast(
            btc = if (target.equals("BTC", true)) last else last / 98765.43,
            eth = null,
            usd = if (target.equals("USD", true) || target.equals("USDT", true)) last else null
        ),
        convertedVolume = CoinDetails.Ticker.ConvertedVolume(
            btc = null,
            eth = null,
            usd = volume
        ),
        trustScore = trustScore,
        bidAskSpreadPercentage = spreadPct,
        isAnomaly = false,
        isStale = false,
        timestamp = timestamp,
        lastFetchAt = timestamp,
        lastTradedAt = timestamp,
        tokenInfoUrl = null,
        tradeUrl = "https://example.exchange/trade/$base-$target",
        tokenInfo = null,
        coinId = base.lowercase(),
        coinMcapUsd = null
    )
}
