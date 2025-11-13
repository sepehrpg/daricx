package com.example.network.model.exchanges


import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

/**
 * Represents a single time-volume pair in `/exchanges/{id}/volume_chart`:
 * JSON array: [timestampMillis, volume]
 *
 * Notes:
 * - `timestampMillis` is Unix time in milliseconds.
 * - `volume` may arrive as a number or a numeric string (e.g., "302561.81").
 *   The custom serializer handles both cases.
 */
@Serializable(with = ExchangeVolumeChartListSerializer::class)
data class ExchangeVolumeChartDto(
    val points: List<Point>
) {
    @Serializable
    data class Point(
        val timestampMillis: Long,
        val volume: Double?
    )
}



/**
 * Custom serializer to decode/encode a 2-element JSON array.
 * Index mapping:
 *   0 -> timestampMillis (Long; supports numeric or string via fallback)
 *   1 -> volume          (Double?; supports numeric or string; nullable)
 */
object ExchangeVolumeChartListSerializer : KSerializer<ExchangeVolumeChartDto> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("ExchangeVolumeChartDto") {
            element<List<Double>>("points")
        }

    override fun deserialize(decoder: Decoder): ExchangeVolumeChartDto {
        val jsonDecoder = decoder as? JsonDecoder
            ?: error("ExchangeVolumeChartListSerializer only supports JSON.")

        val arr = jsonDecoder.decodeJsonElement().jsonArray

        val points = arr.mapNotNull { element ->
            val item = element.jsonArray
            val tPrim = item.getOrNull(0)?.jsonPrimitive ?: return@mapNotNull null
            val vPrim = item.getOrNull(1)?.jsonPrimitive

            val t = tPrim.longOrNull
                ?: tPrim.doubleOrNull?.toLong()
                ?: tPrim.content.toLongOrNull()
                ?: return@mapNotNull null

            val v = when {
                vPrim == null || vPrim is JsonNull -> null
                else -> vPrim.doubleOrNull ?: vPrim.content.toDoubleOrNull()
            }

            ExchangeVolumeChartDto.Point(timestampMillis = t, volume = v)
        }

        return ExchangeVolumeChartDto(points)
    }

    override fun serialize(encoder: Encoder, value: ExchangeVolumeChartDto) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: error("ExchangeVolumeChartListSerializer only supports JSON.")

        val json = buildJsonArray {
            value.points.forEach { p ->
                add(buildJsonArray {
                    add(JsonPrimitive(p.timestampMillis))
                    add(p.volume?.let(::JsonPrimitive) ?: JsonNull)
                })
            }
        }
        jsonEncoder.encodeJsonElement(json)
    }
}