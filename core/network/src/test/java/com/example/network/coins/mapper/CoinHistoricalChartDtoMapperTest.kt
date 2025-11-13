package com.example.network.coins.mapper

import com.example.model.coins.CoinHistoricalChart
import com.example.network.model.coins.CoinHistoricalChartDto
import com.example.network.model.coins.CoinHistoricalChartPointDto
import com.example.network.model.mappers.coins.toDomain
import junit.framework.TestCase.assertTrue
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlinx.serialization.json.Json


/**
 * Tests:
 * 1) Can decode the provided JSON into DTOs (including the array-of-2 points).
 * 2) Correctly maps DTO -> Domain.
 * 3) Handles top-level null lists and null values safely.
 */
class CoinHistoricalChartDtoMapperTest {

    private val json = Json { ignoreUnknownKeys = true }

    private val sample = """
        {
          "prices": [
            [1711843200000, 69702.3087473573],
            [1711929600000, 71246.9514406015],
            [1711983682000, 68887.7495158568]
          ],
          "market_caps": [
            [1711843200000, 1370247487960.09],
            [1711929600000, 1401370211582.37],
            [1711983682000, 1355701979725.16]
          ],
          "total_volumes": [
            [1711843200000, 16408802301.8374],
            [1711929600000, 19723005998.215],
            [1711983682000, 30137418199.6431]
          ]
        }
    """.trimIndent()

    @Test
    fun `decodes JSON into DTO shape`() {
        val dto: CoinHistoricalChartDto = json.decodeFromString(sample)

        // prices
        assertEquals(3, dto.prices?.size)
        val p0 = dto.prices!![0]
        assertEquals(1711843200000L, p0.timestampMillis)
        assertEquals(69702.3087473573, p0.value)

        // market_caps
        assertEquals(3, dto.marketCaps?.size)
        val mcLast = dto.marketCaps!!.last()
        assertEquals(1711983682000L, mcLast.timestampMillis)
        assertEquals(1355701979725.16, mcLast.value)

        // total_volumes
        assertEquals(3, dto.totalVolumes?.size)
        val tv1 = dto.totalVolumes!![1]
        assertEquals(1711929600000L, tv1.timestampMillis)
        assertEquals(19723005998.215, tv1.value)
    }

    @Test
    fun `maps DTO to Domain correctly`() {
        val dto: CoinHistoricalChartDto = json.decodeFromString(sample)

        val domain: CoinHistoricalChart = dto.toDomain()

        assertEquals(3, domain.prices.size)
        assertEquals(3, domain.marketCaps.size)
        assertEquals(3, domain.totalVolumes.size)

        val priceLast = domain.prices.last()
        assertEquals(1711983682000L, priceLast.timestampMillis)
        assertEquals(68887.7495158568, priceLast.value)

        val cap0 = domain.marketCaps.first()
        assertEquals(1711843200000L, cap0.timestampMillis)
        assertEquals(1370247487960.09, cap0.value)

        val volLast = domain.totalVolumes.last()
        assertEquals(1711983682000L, volLast.timestampMillis)
        assertEquals(30137418199.6431, volLast.value)
    }

    @Test
    fun `handles null lists and null values`() {
        // Arrange: DTO with null lists
        val dto = CoinHistoricalChartDto(
            prices = null,
            marketCaps = null,
            totalVolumes = null
        )

        // Act
        val domain = dto.toDomain()

        // Assert: lists are empty
        assertTrue(domain.prices.isEmpty())
        assertTrue(domain.marketCaps.isEmpty())
        assertTrue(domain.totalVolumes.isEmpty())

        // Arrange: DTO with null value inside a point
        val dtoWithNullValue = CoinHistoricalChartDto(
            prices = listOf(CoinHistoricalChartPointDto(1711843200000L, null)),
            marketCaps = listOf(CoinHistoricalChartPointDto(1711843200000L, 1.0)),
            totalVolumes = listOf(CoinHistoricalChartPointDto(1711843200000L, null))
        )

        val domain2 = dtoWithNullValue.toDomain()
        assertNull(domain2.prices.first().value)
        assertEquals(1.0, domain2.marketCaps.first().value)
        assertNull(domain2.totalVolumes.first().value)
    }
}

