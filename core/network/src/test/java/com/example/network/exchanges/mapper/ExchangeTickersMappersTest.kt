package com.example.network.exchanges.mapper

import com.example.model.exchanges.ExchangeTickers
import com.example.network.model.exchanges.ExchangeTickersDto
import com.example.network.model.mappers.exchanges.toDomain
import com.google.common.truth.Truth
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.add
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import org.junit.Test

class ExchangeTickersMappersTest {

    private fun sampleDto(
        token: JsonElement? = buildJsonObject { put("k", 1) }
    ): ExchangeTickersDto {
        val ticker = ExchangeTickersDto.Ticker(
            base = "BTC",
            bidAskSpreadPercentage = 0.0123,
            coinId = "bitcoin",
            coinMcapUsd = 1000.5,
            convertedLast = ExchangeTickersDto.Ticker.ConvertedLast(
                btc = 1.0,
                eth = 20.0,
                usd = 70000.0
            ),
            convertedVolume = ExchangeTickersDto.Ticker.ConvertedVolume(
                btc = 20000.0,
                eth = 400000.0,
                usd = 1_400_000_000.0
            ),
            isAnomaly = false,
            isStale = false,
            last = 69999.5,
            lastFetchAt = "2024-04-08T04:03:00+00:00",
            lastTradedAt = "2024-04-08T04:02:01+00:00",
            market = ExchangeTickersDto.Ticker.Market(
                hasTradingIncentive = false,
                identifier = "binance",
                name = "Binance"
            ),
            target = "USDT",
            targetCoinId = "tether",
            timestamp = "2024-04-08T04:02:01+00:00",
            tokenInfoUrl = token,
            tradeUrl = "https://www.binance.com/en/trade/BTC_USDT",
            trustScore = "green",
            volume = 20242.03975
        )
        return ExchangeTickersDto(
            name = "Binance",
            tickers = listOf(ticker)
        )
    }

    @Test
    fun `maps happy path with full fields`() {
        val dto = sampleDto()
        val domain: ExchangeTickers = dto.toDomain()

        Truth.assertThat(domain.name).isEqualTo("Binance")
        Truth.assertThat(domain.tickers).hasSize(1)

        val t = domain.tickers?.first()!!
        Truth.assertThat(t.base).isEqualTo("BTC")
        Truth.assertThat(t.target).isEqualTo("USDT")
        Truth.assertThat(t.coinId).isEqualTo("bitcoin")
        Truth.assertThat(t.coinMcapUsd).isEqualTo(1000.5)
        Truth.assertThat(t.bidAskSpreadPercentage).isEqualTo(0.0123)
        Truth.assertThat(t.isAnomaly).isFalse()
        Truth.assertThat(t.isStale).isFalse()
        Truth.assertThat(t.last).isWithin(1e-9).of(69999.5)
        Truth.assertThat(t.lastFetchAt).isEqualTo("2024-04-08T04:03:00+00:00")
        Truth.assertThat(t.lastTradedAt).isEqualTo("2024-04-08T04:02:01+00:00")
        Truth.assertThat(t.timestamp).isEqualTo("2024-04-08T04:02:01+00:00")
        Truth.assertThat(t.tradeUrl).contains("BTC_USDT")
        Truth.assertThat(t.trustScore).isEqualTo("green")
        Truth.assertThat(t.volume).isWithin(1e-9).of(20242.03975)

        // nested: convertedLast
        Truth.assertThat(t.convertedLast?.btc).isWithin(1e-9).of(1.0)
        Truth.assertThat(t.convertedLast?.eth).isWithin(1e-9).of(20.0)
        Truth.assertThat(t.convertedLast?.usd).isWithin(1e-9).of(70000.0)

        // nested: convertedVolume
        Truth.assertThat(t.convertedVolume?.btc).isWithin(1e-9).of(20000.0)
        Truth.assertThat(t.convertedVolume?.eth).isWithin(1e-9).of(400000.0)
        Truth.assertThat(t.convertedVolume?.usd).isWithin(1e-6).of(1_400_000_000.0)

        // nested: market
        Truth.assertThat(t.market?.identifier).isEqualTo("binance")
        Truth.assertThat(t.market?.name).isEqualTo("Binance")
        Truth.assertThat(t.market?.hasTradingIncentive).isFalse()

        // tokenInfoUrl: JsonElement must be preserved
        val token = t.tokenInfoUrl as JsonObject
        Truth.assertThat(token["k"]?.jsonPrimitive?.int).isEqualTo(1)
    }

    @Test
    fun `null tickers list stays null`() {
        val dto = ExchangeTickersDto(
            name = "X",
            tickers = null
        )
        val domain = dto.toDomain()
        Truth.assertThat(domain.tickers).isNull()
    }

    @Test
    fun `null items inside list are preserved`() {
        val full = sampleDto().tickers!!.first()
        val dto = ExchangeTickersDto(
            name = "X",
            tickers = listOf(null, full, null)
        )

        val domain = dto.toDomain()
        Truth.assertThat(domain.tickers).hasSize(3)
        Truth.assertThat(domain.tickers?.get(0)).isNull()
        Truth.assertThat(domain.tickers?.get(1)).isNotNull()
        Truth.assertThat(domain.tickers?.get(2)).isNull()
    }

    @Test
    fun `tokenInfoUrl primitive preserved`() {
        val token = JsonPrimitive("https://token.info")
        val dto = sampleDto(token)
        val domain = dto.toDomain()
        val el = domain.tickers?.first()?.tokenInfoUrl
        Truth.assertThat(el).isInstanceOf(JsonPrimitive::class.java)
        Truth.assertThat(el?.jsonPrimitive?.content).isEqualTo("https://token.info")
    }

    @Test
    fun `tokenInfoUrl array preserved`() {
        val token = buildJsonArray { add(1); add(2); add(3) }
        val dto = sampleDto(token)
        val domain = dto.toDomain()
        val el = domain.tickers?.first()?.tokenInfoUrl
        Truth.assertThat(el).isInstanceOf(JsonArray::class.java)
        val arr = el as JsonArray
        Truth.assertThat(arr.size).isEqualTo(3)
        Truth.assertThat(arr[0].jsonPrimitive.int).isEqualTo(1)
    }

    @Test
    fun `empty list maps to empty list`() {
        val dto = ExchangeTickersDto(
            name = "EmptyEx",
            tickers = emptyList()
        )
        val domain = dto.toDomain()
        Truth.assertThat(domain.tickers).isNotNull()
        Truth.assertThat(domain.tickers).isEmpty()
    }

    @Test
    fun `all nullable fields can be null without crash`() {
        val dto = ExchangeTickersDto(
            name = null,
            tickers = listOf(
                ExchangeTickersDto.Ticker(
                    base = null,
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
                    tokenInfoUrl = null,
                    tradeUrl = null,
                    trustScore = null,
                    volume = null
                )
            )
        )

        val domain = dto.toDomain()
        Truth.assertThat(domain.name).isNull()
        Truth.assertThat(domain.tickers).hasSize(1)
        val t = domain.tickers?.first()
        Truth.assertThat(t?.base).isNull()
        Truth.assertThat(t?.market).isNull()
        Truth.assertThat(t?.tokenInfoUrl).isNull()
    }
}