package com.example.network.coins.mapper

import com.example.network.model.coins.CoinTickersDto
import com.example.network.model.mappers.coins.toDomain
import junit.framework.TestCase
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import org.junit.Test

class CoinTickersMapperTest {

    @Test
    fun `map full dto to domain - happy path`() {
        val dto = CoinTickersDto(
            name = "Bitcoin",
            tickers = listOf(
                CoinTickersDto.Ticker(
                    base = "BTC",
                    bidAskSpreadPercentage = 0.010014,
                    coinId = "bitcoin",
                    coinMcapUsd = 1_234_567_890.0,
                    convertedLast = CoinTickersDto.Ticker.ConvertedLast(
                        btc = 1.000205,
                        eth = 20.291404,
                        usd = 69498
                    ),
                    convertedVolume = CoinTickersDto.Ticker.ConvertedVolume(
                        btc = 20249.0,
                        eth = 410802.0,
                        usd = 1_406_996_874.0
                    ),
                    isAnomaly = false,
                    isStale = false,
                    last = 69476.0,
                    lastFetchAt = "2024-04-08T04:03:00+00:00",
                    lastTradedAt = "2024-04-08T04:02:01+00:00",
                    market = CoinTickersDto.Ticker.Market(
                        hasTradingIncentive = false,
                        identifier = "binance",
                        name = "Binance"
                    ),
                    target = "USDT",
                    targetCoinId = "tether",
                    timestamp = "2024-04-08T04:02:01+00:00",
                    tokenInfoUrl = JsonPrimitive("https://token.info"),
                    tradeUrl = "https://trade.link",
                    trustScore = "green",
                    volume = 20242.03975
                ),
                null
            )
        )

        val domain = dto.toDomain()

        TestCase.assertEquals("Bitcoin", domain.name)
        TestCase.assertEquals(2, domain.tickers?.size)

        val t0 = domain.tickers?.get(0)!!
        TestCase.assertEquals("BTC", t0.base)
        TestCase.assertEquals(0.010014, t0.bidAskSpreadPercentage)
        TestCase.assertEquals("bitcoin", t0.coinId)
        TestCase.assertEquals(1_234_567_890.0, t0.coinMcapUsd)
        TestCase.assertEquals(69498, t0.convertedLast?.usd)
        TestCase.assertEquals(1_406_996_874.0, t0.convertedVolume?.usd)
        TestCase.assertEquals(false, t0.isAnomaly)
        TestCase.assertEquals(false, t0.isStale)
        TestCase.assertEquals(69476.0, t0.last)
        TestCase.assertEquals("2024-04-08T04:03:00+00:00", t0.lastFetchAt)
        TestCase.assertEquals("2024-04-08T04:02:01+00:00", t0.lastTradedAt)
        TestCase.assertEquals("Binance", t0.market?.name)
        TestCase.assertEquals("USDT", t0.target)
        TestCase.assertEquals("tether", t0.targetCoinId)
        TestCase.assertEquals("2024-04-08T04:02:01+00:00", t0.timestamp)
        TestCase.assertEquals(
            "https://token.info",
            t0.tokenInfoUrl
        ) // JsonPrimitive string → String
        TestCase.assertEquals("https://trade.link", t0.tradeUrl)
        TestCase.assertEquals("green", t0.trustScore)
        TestCase.assertEquals(20242.03975, t0.volume)

        // null item preserved
        TestCase.assertNull(domain.tickers?.get(1))
    }

    @Test
    fun `map null-safe - nulls propagate`() {
        val dto = CoinTickersDto(
            name = null,
            tickers = null
        )

        val domain = dto.toDomain()
        TestCase.assertNull(domain.name)
        TestCase.assertNull(domain.tickers)
    }

    @Test
    fun `tokenInfoUrl - primitive variants`() {
        fun mapWithToken(el: JsonElement?): Any? {
            val dto = CoinTickersDto(
                name = "X",
                tickers = listOf(
                    CoinTickersDto.Ticker(
                        base = "AAA",
                        bidAskSpreadPercentage = null,
                        coinId = null,
                        coinMcapUsd = null,
                        convertedLast = null,
                        convertedVolume = null,
                        isAnomaly = null,
                        isStale = null,
                        last = null,
                        lastFetchAt = null,
                        lastTradedAt = null,
                        market = null,
                        target = null,
                        targetCoinId = null,
                        timestamp = null,
                        tokenInfoUrl = el,
                        tradeUrl = null,
                        trustScore = null,
                        volume = null
                    )
                )
            )
            return dto.toDomain().tickers?.first()?.tokenInfoUrl
        }

        // String
        TestCase.assertEquals("abc", mapWithToken(JsonPrimitive("abc")))
        // Boolean
        TestCase.assertEquals(true, mapWithToken(JsonPrimitive(true)))
        // Integer → Long
        TestCase.assertEquals(42L, mapWithToken(JsonPrimitive(42)) as Long)
        // Double
        TestCase.assertEquals(42.5, mapWithToken(JsonPrimitive(42.5)))
        // Null
        TestCase.assertNull(mapWithToken(JsonNull))
    }

    @Test
    fun `tokenInfoUrl - object and array become stringified JSON`() {
        fun mapWithToken(el: JsonElement): String {
            val dto = CoinTickersDto(
                name = "X",
                tickers = listOf(
                    CoinTickersDto.Ticker(
                        base = "AAA",
                        bidAskSpreadPercentage = null,
                        coinId = null,
                        coinMcapUsd = null,
                        convertedLast = null,
                        convertedVolume = null,
                        isAnomaly = null,
                        isStale = null,
                        last = null,
                        lastFetchAt = null,
                        lastTradedAt = null,
                        market = null,
                        target = null,
                        targetCoinId = null,
                        timestamp = null,
                        tokenInfoUrl = el,
                        tradeUrl = null,
                        trustScore = null,
                        volume = null
                    )
                )
            )

            val domain = dto.toDomain()
            val tokenInfo = domain.tickers?.first()?.tokenInfoUrl
            return tokenInfo as String
        }

        val obj = buildJsonObject { put("k", JsonPrimitive(1)) }
        val arr = buildJsonArray { add(JsonPrimitive(1)); add(JsonPrimitive(2)) }

        val objStr: String = mapWithToken(obj)
        val arrStr: String = mapWithToken(arr)

        TestCase.assertTrue(objStr.contains("\"k\"") && objStr.contains("1"))
        TestCase.assertTrue(arrStr.startsWith("[") && arrStr.contains("1"))
    }
}