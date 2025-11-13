package com.example.network.exchanges.mapper

import com.example.model.exchanges.ExchangeVolumeChart
import com.example.network.model.exchanges.ExchangeVolumeChartDto
import com.example.network.model.mappers.exchanges.toDomain
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertNull


/**
 * Tests for decoding `/exchanges/{id}/volume_chart` response and mapping to domain.
 *
 * Covers:
 * - Decoding with "string numeric" volumes.
 * - Mapping list -> domain.
 * - Mixed numeric/string/null volume values.
 * - Empty payloads.
 */
class ExchangeVolumeChartMapperTest {

    private val json = Json { ignoreUnknownKeys = true }

    private val sampleStringNumbers = """
        [
          [1711792200000, "306800.0517941023777005"],
          [1711795800000, "302561.8185582217570913"],
          [1711799400000, "298240.5127048246776691"]
        ]
    """.trimIndent()

    @Test
    fun `decodes string-numeric volumes into DTO list`() {
        val dtoList: List<ExchangeVolumeChartDto> = json.decodeFromString(sampleStringNumbers)

        assertEquals(3, dtoList.size)

        assertEquals(1711792200000L, dtoList[0].timestampMillis)
        assertEquals(306800.0517941023777005, dtoList[0].volume!!, 1e-9)

        assertEquals(1711795800000L, dtoList[1].timestampMillis)
        assertEquals(302561.8185582217570913, dtoList[1].volume!!, 1e-9)

        assertEquals(1711799400000L, dtoList[2].timestampMillis)
        assertEquals(298240.5127048246776691, dtoList[2].volume!!, 1e-9)
    }

    @Test
    fun `maps DTO list to domain correctly`() {
        val dtoList: List<ExchangeVolumeChartDto> = json.decodeFromString(sampleStringNumbers)
        val domain: ExchangeVolumeChart = dtoList.toDomain()

        assertEquals(3, domain.points.size)

        val last = domain.points.last()
        assertEquals(1711799400000L, last.timestampMillis)
        assertEquals(298240.5127048246776691, last.volume!!, 1e-9)
    }

    @Test
    fun `decodes mixed numeric, string, and null volumes`() {
        val mixed = """
            [
              [1711792200000, 123.45],
              [1711795800000, "678.90"],
              [1711799400000, null]
            ]
        """.trimIndent()

        val dtoList: List<ExchangeVolumeChartDto> = json.decodeFromString(mixed)
        val domain = dtoList.toDomain()

        assertEquals(3, domain.points.size)

        assertEquals(123.45, domain.points[0].volume!!, 1e-9)
        assertEquals(678.90, domain.points[1].volume!!, 1e-9)
        assertNull(domain.points[2].volume)
    }

    @Test
    fun `handles empty payload`() {
        val empty = "[]"
        val dtoList: List<ExchangeVolumeChartDto> = json.decodeFromString(empty)
        val domain = dtoList.toDomain()

        assertTrue(dtoList.isEmpty())
        assertTrue(domain.points.isEmpty())
    }
}
