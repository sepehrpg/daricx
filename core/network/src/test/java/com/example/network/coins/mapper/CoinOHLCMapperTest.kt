package com.example.network.coins.mapper


import com.example.model.coins.CoinOHLCChartCandle
import com.example.network.model.coins.CoinOHLCChartCandleDto
import com.example.network.model.mappers.coins.toDomain
import junit.framework.TestCase.assertEquals
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertNull


/**
 * Unit tests for array-shaped OHLC payload (5-tuple) mapping.
 */
class CoinOHLCMapperTest {

    private val json = Json { ignoreUnknownKeys = true }

    private val sample = """
        [
          [1709395200000, 61942, 62211, 61721, 61845],
          [1709409600000, 61828, 62139, 61726, 62139],
          [1709424000000, 62171, 62210, 61821, 62068]
        ]
    """.trimIndent()

    @Test
    fun `decodes array-of-arrays into DTO list`() {
        val dtoList: List<CoinOHLCChartCandleDto> = json.decodeFromString(sample)

        assertEquals(3, dtoList.size)
        val first = dtoList.first()
        assertEquals(1709395200000L, first.timestampMillis)
        assertEquals(61942.0, first.open)
        assertEquals(62211.0, first.high)
        assertEquals(61721.0, first.low)
        assertEquals(61845.0, first.close)
    }

    @Test
    fun `maps DTO list to Domain correctly`() {
        val dtoList: List<CoinOHLCChartCandleDto> = json.decodeFromString(sample)

        val domain: CoinOHLCChartCandle = dtoList.toDomain()

        assertEquals(3, domain.candles.size)
        val last = domain.candles.last()
        assertEquals(1709424000000L, last.timestampMillis)
        assertEquals(62171.0, last.open)
        assertEquals(62210.0, last.high)
        assertEquals(61821.0, last.low)
        assertEquals(62068.0, last.close)
    }

    @Test
    fun `handles null numeric values inside a candle`() {
        val withNulls = """
            [
              [1709395200000, 61942, null, 61721, null]
            ]
        """.trimIndent()

        val dtoList: List<CoinOHLCChartCandleDto> = json.decodeFromString(withNulls)
        val domain = dtoList.toDomain()

        assertEquals(1, domain.candles.size)
        val c = domain.candles[0]
        assertEquals(1709395200000L, c.timestampMillis)
        assertEquals(61942.0, c.open)
        assertNull(c.high)
        assertEquals(61721.0, c.low)
        assertNull(c.close)
    }
}
