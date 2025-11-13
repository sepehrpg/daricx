package com.daricx.markets.data
import com.example.model.exchanges.ExchangeDetail


/**
 * Provides fake data for [ExchangeDetail] domain model.
 * Useful for UI previews, tests, and offline prototyping.
 */
object ExchangeDetailsFakes {

    /**
     * Fake-but-realistic sample based on a real CoinGecko response for Binance.
     * Notes:
     * - convertedVolume.eth => Int
     * - convertedVolume.usd => Long
     * - timestamps kept from the real sample (+00:00)
     */
    fun binanceFromReal(): ExchangeDetail = ExchangeDetail(
        name = "Binance",
        yearEstablished = 2017,
        country = "Cayman Islands",
        description = "One of the world’s largest cryptocurrency exchanges by trading volume, offering a wide range of services including spot, futures, and staking options." +
                "One of the world’s largest cryptocurrency exchanges by trading volume, offering a wide range of services including spot, futures, and staking options."+
                "One of the world’s largest cryptocurrency exchanges by trading volume, offering a wide range of services including spot, futures, and staking options.",
        url = "https://www.binance.com/",
        image = "https://assets.coingecko.com/markets/images/52/small/binance.jpg?1706864274",
        facebookUrl = "https://www.facebook.com/binanceexchange",
        redditUrl = "https://www.reddit.com/r/binance/",
        telegramUrl = "",
        slackUrl = "",
        otherUrl1 = "https://medium.com/binanceexchange",
        otherUrl2 = "https://steemit.com/@binanceexchange",
        twitterHandle = "binance",
        hasTradingIncentive = false,
        centralized = true,
        publicNotice = "",
        alertNotice = "",
        trustScore = 9,
        trustScoreRank = 6,
        tradeVolume24hBtc = 207_319.133772613,
        coins = 384,
        pairs = 1281,
        statusUpdates = emptyList(), // real payload snippet didn't include status updates
        tickers = listOf(
            ExchangeDetail.Ticker(
                base = "BTC",
                target = "USDT",
                market = ExchangeDetail.Ticker.Market(
                    name = "Binance",
                    identifier = "binance",
                    hasTradingIncentive = false
                ),
                last = 69_476.0,
                volume = 20_242.03975,
                convertedLast = ExchangeDetail.Ticker.ConvertedLast(
                    btc = 1.000205,
                    eth = 20.291404,
                    usd = 69_498.0
                ),
                convertedVolume = ExchangeDetail.Ticker.ConvertedVolume(
                    btc = 20_249.0,
                    eth = 410_802.0,
                    usd = 1_406_996_874.0
                ),
                trustScore = "green",
                bidAskSpreadPercentage = 0.010014,
                timestamp = "2024-04-08T04:02:01+00:00",
                lastTradedAt = "2024-04-08T04:02:01+00:00",
                lastFetchAt = "2024-04-08T04:03:00+00:00",
                isAnomaly = false,
                isStale = false,
                tradeUrl = "https://www.binance.com/en/trade/BTC_USDT?ref=37754157",
                tokenInfoUrl = null,
                coinId = "bitcoin",
                targetCoinId = "tether",
                coinMcapUsd = null
            )
        )
    )


    // -----------------------------------
    // Featured sample: Binance
    // -----------------------------------
    fun binance(): ExchangeDetail = ExchangeDetail(
        alertNotice = "Scheduled maintenance on 2025-11-20 03:00–04:00 UTC.",
        centralized = true,
        coins = 420,
        country = "Cayman Islands",
        description = "Binance is a centralized exchange with deep liquidity and a wide range of markets.",
        facebookUrl = "https://www.facebook.com/binance",
        hasTradingIncentive = false,
        image = "https://assets.coingecko.com/markets/images/52/small/binance.jpg",
        name = "Binance",
        otherUrl1 = "https://research.binance.com/",
        otherUrl2 = "https://academy.binance.com/",
        pairs = 1200,
        publicNotice = null,
        redditUrl = "https://www.reddit.com/r/binance/",
        slackUrl = null,
        statusUpdates = listOf(
            statusUpdate(
                category = "general",
                createdAt = "2025-10-02T12:30:00Z",
                description = "New futures contracts listed for SOL and AVAX.",
                pin = true,
                projectId = "binance",
                projectName = "Binance",
                projectType = "exchange",
                projectThumb = "https://dummyimage.com/64x64/090909/ffffff.png&text=B",
                projectSmall = "https://dummyimage.com/96x96/111111/ffffff.png&text=B",
                projectLarge = "https://dummyimage.com/128x128/222222/ffffff.png&text=B",
                user = "binance_official",
                userTitle = "Admin"
            )
        ),
        telegramUrl = "https://t.me/binanceexchange",
        tickers = listOf(
            ticker(
                base = "BTC",
                target = "USDT",
                marketId = "binance",
                marketName = "Binance",
                last = 98765.43,
                volume = 123456.0,
                trustScore = "green",
                spreadPct = 0.01,
                timestamp = "2025-10-02T12:00:00Z"
            ),
            ticker(
                base = "ETH",
                target = "USDT",
                marketId = "binance",
                marketName = "Binance",
                last = 3456.78,
                volume = 234567.0,
                trustScore = "green",
                spreadPct = 0.02,
                timestamp = "2025-10-02T12:00:05Z"
            ),
            ticker(
                base = "SOL",
                target = "USDT",
                marketId = "binance",
                marketName = "Binance",
                last = 178.12,
                volume = 456789.0,
                trustScore = "green",
                spreadPct = 0.03,
                timestamp = "2025-10-02T12:00:10Z"
            )
        ),
        tradeVolume24hBtc = 125000.45,
        trustScore = 10,
        trustScoreRank = 1,
        twitterHandle = "binance",
        url = "https://www.binance.com",
        yearEstablished = 2017
    )

    // -----------------------------------
    // Generator
    // -----------------------------------
    fun generate(index: Int = 0): ExchangeDetail {
        val name = "Exchange $index"
        val id = "ex$index"
        val centralized = index % 2 == 0
        val tsBase = "2025-10-02T12:${(10 + index) % 60}:00Z"

        return ExchangeDetail(
            alertNotice = if (index % 4 == 0) "Partial outage on margin markets." else null,
            centralized = centralized,
            coins = 100 + index * 7,
            country = listOf("USA", "Singapore", "UAE", "UK", "Japan")[index % 5],
            description = "$name is a sample exchange used for UI testing and previews.",
            facebookUrl = "https://facebook.com/$id",
            hasTradingIncentive = index % 3 == 0,
            image = "https://dummyimage.com/128x128/00${index}aa/ffffff.png&text=EX$index",
            name = name,
            otherUrl1 = "https://status.$id.example.com",
            otherUrl2 = "https://docs.$id.example.com",
            pairs = 200 + index * 15,
            publicNotice = if (index % 5 == 0) "Read our new listing policy." else null,
            redditUrl = "https://reddit.com/r/$id",
            slackUrl = if (index % 6 == 0) "https://$id.slack.com" else null,
            statusUpdates = listOf(
                statusUpdate(
                    category = "listing",
                    createdAt = tsBase,
                    description = "Listed COIN$index/USDT and COIN$index/BTC.",
                    pin = index % 2 == 0,
                    projectId = id,
                    projectName = name,
                    projectType = "exchange",
                    projectThumb = "https://dummyimage.com/64x64/008${index}/fff.png&text=E$index",
                    projectSmall = "https://dummyimage.com/96x96/007${index}/fff.png&text=E$index",
                    projectLarge = "https://dummyimage.com/128x128/006${index}/fff.png&text=E$index",
                    user = "${id}_ops",
                    userTitle = "Ops"
                )
            ),
            telegramUrl = "https://t.me/$id",
            tickers = listOf(
                ticker(
                    base = "COIN$index",
                    target = "USDT",
                    marketId = id,
                    marketName = name,
                    last = 10.0 + index * 2.5,
                    volume = 1_000.0 + index * 200,
                    trustScore = if (index % 3 == 0) "yellow" else "green",
                    spreadPct = 0.08,
                    timestamp = tsBase
                ),
                ticker(
                    base = "COIN$index",
                    target = "BTC",
                    marketId = id,
                    marketName = name,
                    last = (10.0 + index * 2.5) / BTC_USD,
                    volume = 500.0 + index * 50,
                    trustScore = "green",
                    spreadPct = 0.12,
                    timestamp = tsBase
                )
            ),
            tradeVolume24hBtc = 1000.0 + index * 25.5,
            trustScore = 8 - (index % 3),
            trustScoreRank = 50 - index,
            twitterHandle = id,
            url = "https://www.$id.example.com",
            yearEstablished = 2015 + (index % 9)
        )
    }

    val samples: List<ExchangeDetail> = listOf(
        binance(),
        generate(1),
        generate(2),
        generate(3)
    )

    // -----------------------------------
    // Helpers
    // -----------------------------------

    private const val BTC_USD = 98765.43
    private const val ETH_USD = 3456.78

    private fun statusUpdate(
        category: String,
        createdAt: String,
        description: String,
        pin: Boolean,
        projectId: String,
        projectName: String,
        projectType: String,
        projectThumb: String,
        projectSmall: String,
        projectLarge: String,
        user: String,
        userTitle: String
    ): ExchangeDetail.StatusUpdate =
        ExchangeDetail.StatusUpdate(
            category = category,
            createdAt = createdAt,
            description = description,
            pin = pin,
            project = ExchangeDetail.StatusUpdate.Project(
                id = projectId,
                name = projectName,
                type = projectType,
                image = ExchangeDetail.StatusUpdate.Project.Image(
                    thumb = projectThumb,
                    small = projectSmall,
                    large = projectLarge
                )
            ),
            user = user,
            userTitle = userTitle
        )

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
    ): ExchangeDetail.Ticker {
        // Compute simple converted values for preview purposes
        val usdPrice =
            when {
                target.equals("USDT", true) || target.equals("USD", true) -> last
                target.equals("BTC", true) -> last * BTC_USD
                target.equals("ETH", true) -> last * ETH_USD
                else -> null
            }

        val btcPrice =
            when {
                target.equals("BTC", true) -> last
                target.equals("USDT", true) || target.equals("USD", true) -> last / BTC_USD
                else -> null
            }

        val ethPrice =
            when {
                target.equals("ETH", true) -> last
                target.equals("USDT", true) || target.equals("USD", true) -> last / ETH_USD
                else -> null
            }

        // NOTE: ConvertedVolume.eth is Int? and usd is Long? in the model.
        // We intentionally cast to match the domain types and avoid decimal issues in tests.
        val convertedUsdVol: Double? = usdPrice?.let { (volume * it) }
        val convertedEthVol: Double? = ethPrice?.let { (volume * it) } ?: 0.0

        return ExchangeDetail.Ticker(
            base = base,
            target = target,
            market = ExchangeDetail.Ticker.Market(
                identifier = marketId,
                name = marketName,
                hasTradingIncentive = false
            ),
            last = last,
            volume = volume,
            convertedLast = ExchangeDetail.Ticker.ConvertedLast(
                btc = btcPrice,
                eth = ethPrice,
                usd = usdPrice
            ),
            convertedVolume = ExchangeDetail.Ticker.ConvertedVolume(
                btc = btcPrice?.let { (volume * it / BTC_USD) }?.toDouble(),
                eth = convertedEthVol,
                usd = convertedUsdVol
            ),
            trustScore = trustScore,
            bidAskSpreadPercentage = spreadPct,
            isAnomaly = false,
            isStale = false,
            timestamp = timestamp,
            lastFetchAt = timestamp,
            lastTradedAt = timestamp,
            tokenInfoUrl = null,
            tradeUrl = "https://trade.example.com/$marketId/$base-$target",
            coinId = base.lowercase(),
            coinMcapUsd = null,
            targetCoinId = null
        )
    }
}
