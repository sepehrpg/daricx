package com.example.network.model.coins


import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull




/**
 * DTO representing a single OHLC candle coming from an array-shaped payload.
 *
 * Upstream response shape for each candle:
 *   [timestampMillis, open, high, low, close]
 *
 * We use a custom serializer to map JSON arrays to a typed DTO.
 */
@Serializable(with = CoinOHLCChartCandleListSerializer::class)
data class CoinOHLCChartCandleDto(
    val candles: List<Ohlc>
) {
    @Serializable
    data class Ohlc(
        val timestampMillis: Long,
        val open: Double,
        val high: Double,
        val low: Double,
        val close: Double
    )
}
/**
 * Custom serializer to decode/encode an OHLC candle represented as a 5-element JSON array.
 * Index mapping:
 *   0 -> timestampMillis (required, Long)
 *   1 -> open            (nullable, Double?)
 *   2 -> high            (nullable, Double?)
 *   3 -> low             (nullable, Double?)
 *   4 -> close           (nullable, Double?)
 */
object CoinOHLCChartCandleListSerializer : KSerializer<CoinOHLCChartCandleDto> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("CoinOHLCChartCandleDto") {
            element<List<Double>>("candles")
        }

    override fun deserialize(decoder: Decoder): CoinOHLCChartCandleDto {
        val jsonDecoder = decoder as? JsonDecoder
            ?: error("CoinOHLCChartCandleListSerializer only supports JSON")

        val arr = jsonDecoder.decodeJsonElement().jsonArray

        val candles = arr.mapNotNull { element ->
            val item = element.jsonArray
            val timestamp = item.getOrNull(0)?.jsonPrimitive?.longOrNull ?: return@mapNotNull null
            val open = item.getOrNull(1)?.jsonPrimitive?.doubleOrNull ?: return@mapNotNull null
            val high = item.getOrNull(2)?.jsonPrimitive?.doubleOrNull ?: return@mapNotNull null
            val low = item.getOrNull(3)?.jsonPrimitive?.doubleOrNull ?: return@mapNotNull null
            val close = item.getOrNull(4)?.jsonPrimitive?.doubleOrNull ?: return@mapNotNull null
            CoinOHLCChartCandleDto.Ohlc(timestamp, open, high, low, close)
        }

        return CoinOHLCChartCandleDto(candles)
    }

    override fun serialize(encoder: Encoder, value: CoinOHLCChartCandleDto) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: error("CoinOHLCChartCandleListSerializer only supports JSON")

        val json = buildJsonArray {
            value.candles.forEach { ohlc ->
                add(buildJsonArray {
                    add(JsonPrimitive(ohlc.timestampMillis))
                    add(JsonPrimitive(ohlc.open))
                    add(JsonPrimitive(ohlc.high))
                    add(JsonPrimitive(ohlc.low))
                    add(JsonPrimitive(ohlc.close))
                })
            }
        }
        jsonEncoder.encodeJsonElement(json)
    }
}
