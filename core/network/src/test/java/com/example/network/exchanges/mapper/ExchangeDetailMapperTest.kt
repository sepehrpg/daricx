package com.example.network.exchanges.mapper

import com.example.network.model.exchanges.ExchangeDetailDto
import com.example.network.model.mappers.exchanges.toDomain
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import org.junit.Assert
import org.junit.Test

class ExchangeDetailMapperTest {

    // ---------- Fixtures / Helpers ----------

    private fun emptyDto() = ExchangeDetailDto(
        alertNotice = null,
        centralized = null,
        coins = null,
        country = null,
        description = null,
        facebookUrl = null,
        hasTradingIncentive = null,
        image = null,
        name = null,
        otherUrl1 = null,
        otherUrl2 = null,
        pairs = null,
        publicNotice = null,
        redditUrl = null,
        slackUrl = null,
        statusUpdates = null,
        telegramUrl = null,
        tickers = null,
        tradeVolume24hBtc = null,
        trustScore = null,
        trustScoreRank = null,
        twitterHandle = null,
        url = null,
        yearEstablished = null
    )

    private fun status(
        description: String = "desc",
        category: String = "general"
    ) = ExchangeDetailDto.StatusUpdate(
        category = category,
        createdAt = "2025-11-11T10:00:00Z",
        description = description,
        pin = false,
        project = ExchangeDetailDto.StatusUpdate.Project(
            id = "binance",
            image = ExchangeDetailDto.StatusUpdate.Project.Image(
                large = "L",
                small = "S",
                thumb = "T"
            ),
            name = "Binance",
            type = "Market"
        ),
        user = "Darc",
        userTitle = "Marketing"
    )

    private fun ticker(
        base: String = "ETH",
        target: String = "USDT",
        tokenInfo: JsonElement? = null,
        convUsd: Double? = 2353896772.0
    ) = ExchangeDetailDto.Ticker(
        base = base,
        bidAskSpreadPercentage = 0.010281,
        coinId = "ethereum",
        coinMcapUsd = 429218257846.6953,
        convertedLast = ExchangeDetailDto.Ticker.ConvertedLast(
            btc = 0.03388388,
            eth = 1.000597,
            usd = 3556.24
        ),
        convertedVolume = ExchangeDetailDto.Ticker.ConvertedVolume(
            btc = 22428.0,
            eth = 662301.0,
            usd = convUsd
        ),
        isAnomaly = false,
        isStale = false,
        last = 3556.8,
        lastFetchAt = "2025-11-11T10:03:02Z",
        lastTradedAt = "2025-11-11T10:03:02Z",
        market = ExchangeDetailDto.Ticker.Market(
            name = "Binance",
            identifier = "binance",
            hasTradingIncentive = false
        ),
        target = target,
        targetCoinId = "tether",
        timestamp = "2025-11-11T10:03:02Z",
        tokenInfoUrl = tokenInfo,
        tradeUrl = "https://www.binance.com/en/trade/ETH_USDT",
        trustScore = "green",
        volume = 661_905.6055
    )

    // ---------- Tests ----------

    @Test
    fun map_rootFields_ok() {
        val dto = emptyDto().copy(
            name = "Binance",
            yearEstablished = 2017,
            country = "Cayman Islands",
            description = "One of the world’s largest ...",
            url = "https://www.binance.com/",
            image = "https://coin-images.coingecko.com/markets/images/52/small/binance.jpg",
            facebookUrl = "https://www.facebook.com/binanceexchange",
            redditUrl = "https://www.reddit.com/r/binance/",
            telegramUrl = "",
            slackUrl = "",
            otherUrl1 = "https://medium.com/binanceexchange",
            otherUrl2 = "https://steemit.com/@binanceexchange",
            centralized = true,
            hasTradingIncentive = false,
            publicNotice = "",
            alertNotice = "",
            trustScore = 10,
            trustScoreRank = 1,
            coins = 440,
            pairs = 1609,
            tradeVolume24hBtc = 213_179.16488343093,
            twitterHandle = "binance",
            statusUpdates = listOf(status()),
            tickers = listOf(ticker())
        )

        val domain = dto.toDomain()

        Assert.assertEquals("Binance", domain.name)
        Assert.assertEquals(2017, domain.yearEstablished)
        Assert.assertEquals("Cayman Islands", domain.country)
        Assert.assertEquals("https://www.binance.com/", domain.url)
        Assert.assertEquals(10, domain.trustScore)
        Assert.assertEquals(1, domain.trustScoreRank)
        Assert.assertEquals(440, domain.coins)
        Assert.assertEquals(1609, domain.pairs)
        Assert.assertTrue(domain.centralized == true)
        Assert.assertTrue(domain.hasTradingIncentive == false)

        // nested status
        val s = requireNotNull(domain.statusUpdates).first()!!
        Assert.assertEquals("general", s.category)
        Assert.assertEquals("Binance", s.project?.name)
        Assert.assertEquals("L", s.project?.image?.large)

        // nested ticker
        val t = requireNotNull(domain.tickers).first()!!
        Assert.assertEquals("ETH", t.base)
        Assert.assertEquals("USDT", t.target)
        Assert.assertEquals("binance", t.market?.identifier)
        Assert.assertEquals("green", t.trustScore)

        Assert.assertEquals(2_353_896_772.0, t.convertedVolume?.usd?:0.0, 0.0)
    }

    @Test
    fun map_nullsPropagation_ok() {
        val dto = emptyDto()
        val domain = dto.toDomain()

        Assert.assertNull(domain.name)
        Assert.assertNull(domain.statusUpdates)
        Assert.assertNull(domain.tickers)
        Assert.assertNull(domain.yearEstablished)
        Assert.assertNull(domain.tradeVolume24hBtc)
    }

    @Test
    fun map_listsWithNullElements_ok() {
        val dto = emptyDto().copy(
            statusUpdates = listOf(null, status("X")),
            tickers = listOf(null, ticker(base = "BTC", target = "USDC"))
        )
        val domain = dto.toDomain()

        Assert.assertEquals(2, requireNotNull(domain.statusUpdates).size)
        Assert.assertNull(domain.statusUpdates!![0])
        Assert.assertEquals("X", domain.statusUpdates!![1]!!.description)

        Assert.assertEquals(2, requireNotNull(domain.tickers).size)
        Assert.assertNull(domain.tickers!![0])
        Assert.assertEquals("BTC", domain.tickers!![1]!!.base)
        Assert.assertEquals("USDC", domain.tickers!![1]!!.target)
    }

    @Test
    fun map_tokenInfoUrl_variants_ok() {
        // String
        val d1 = emptyDto().copy(tickers = listOf(ticker(tokenInfo = JsonPrimitive("https://x"))))
        val t1 = d1.toDomain().tickers!!.first()!!
        Assert.assertTrue(t1.tokenInfoUrl is String)
        Assert.assertEquals("https://x", t1.tokenInfoUrl)

        // Int → Long
        val d2 = emptyDto().copy(tickers = listOf(ticker(tokenInfo = JsonPrimitive(42))))
        val t2 = d2.toDomain().tickers!!.first()!!
        Assert.assertTrue(t2.tokenInfoUrl is Long)
        Assert.assertEquals(42L, t2.tokenInfoUrl)

        // Double
        val d3 = emptyDto().copy(tickers = listOf(ticker(tokenInfo = JsonPrimitive(42.25))))
        val t3 = d3.toDomain().tickers!!.first()!!
        Assert.assertTrue(t3.tokenInfoUrl is Double)
        Assert.assertEquals(42.25, t3.tokenInfoUrl as Double, 0.0)

        // Boolean
        val d4 = emptyDto().copy(tickers = listOf(ticker(tokenInfo = JsonPrimitive(true))))
        val t4 = d4.toDomain().tickers!!.first()!!
        Assert.assertTrue(t4.tokenInfoUrl is Boolean)
        Assert.assertEquals(true, t4.tokenInfoUrl)

        // Object → stringified JSON
        val obj = buildJsonObject { put("a", JsonPrimitive(1)) }
        val d5 = emptyDto().copy(tickers = listOf(ticker(tokenInfo = obj)))
        val t5 = d5.toDomain().tickers!!.first()!!
        Assert.assertTrue(t5.tokenInfoUrl is String)
        val s5 = t5.tokenInfoUrl as String
        Assert.assertTrue(s5.startsWith("{") && s5.contains("\"a\":1"))

        // Array → stringified JSON
        val arr = buildJsonArray { add(JsonPrimitive(1)); add(JsonPrimitive(2)) }
        val d6 = emptyDto().copy(tickers = listOf(ticker(tokenInfo = arr)))
        val t6 = d6.toDomain().tickers!!.first()!!
        Assert.assertTrue(t6.tokenInfoUrl is String)
        Assert.assertEquals("[1,2]", t6.tokenInfoUrl)
    }

    @Test
    fun map_convertedVolumeUsd_values_ok() {

        val d1 = emptyDto().copy(tickers = listOf(ticker(convUsd = 10.4)))
        val u1 = d1.toDomain().tickers!!.first()!!.convertedVolume!!.usd
        Assert.assertEquals(10.4, u1!!, 0.0)

        val d2 = emptyDto().copy(tickers = listOf(ticker(convUsd = 10.5)))
        val u2 = d2.toDomain().tickers!!.first()!!.convertedVolume!!.usd
        Assert.assertEquals(10.5, u2!!, 0.0)

        val d3 = emptyDto().copy(tickers = listOf(ticker(convUsd = 11.5)))
        val u3 = d3.toDomain().tickers!!.first()!!.convertedVolume!!.usd
        Assert.assertEquals(11.5, u3!!, 0.0)
    }

    @Test
    fun map_market_ok() {
        val dto = emptyDto().copy(
            tickers = listOf(
                ticker().copy(
                    market = ExchangeDetailDto.Ticker.Market(
                        name = "Binance",
                        identifier = "binance",
                        hasTradingIncentive = false
                    )
                )
            )
        )
        val t = dto.toDomain().tickers!!.first()!!
        Assert.assertEquals("Binance", t.market?.name)
        Assert.assertEquals("binance", t.market?.identifier)
        Assert.assertEquals(false, t.market?.hasTradingIncentive)
    }
}
