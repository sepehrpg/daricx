package com.example.network.model.coins


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.KSerializer
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
 * Each array item in `prices`, `market_caps`, and `total_volumes` is a 2-tuple:
 *   [timestampMillis, value]
 * where:
 *   - timestampMillis is Unix timestamp in milliseconds (long)
 *   - value is a Double (may be null in edge cases)
 */
@Serializable
data class CoinHistoricalChartDto(
   @SerialName("prices")
   val prices: List<CoinHistoricalChartPointDto>?,
   @SerialName("market_caps")
   val marketCaps: List<CoinHistoricalChartPointDto>?,
   @SerialName("total_volumes")
   val totalVolumes: List<CoinHistoricalChartPointDto>?
)


/**
 * A single time/value pair as used by `/market_chart` arrays:
 *   JSON array shape: [timestampMillis, value]
 */
@Serializable(with = CoinHistoricalChartPointDtoSerializer::class)
data class CoinHistoricalChartPointDto(
   val timestampMillis: Long,
   val value: Double?
)

/**
 * Custom serializer to decode/encode a 2-element JSON array as MarketChartPointDto.
 * Index mapping:
 *   0 -> timestampMillis (Long, required)
 *   1 -> value           (Double?, nullable)
 */
object CoinHistoricalChartPointDtoSerializer : KSerializer<CoinHistoricalChartPointDto> {
   override val descriptor: SerialDescriptor =
      buildClassSerialDescriptor("CoinHistoricalChartPointDto") {
         element<Long>("timestampMillis")
         element<Double?>("value")
      }

   override fun deserialize(decoder: Decoder): CoinHistoricalChartPointDto {
      val jsonDecoder = decoder as? JsonDecoder
         ?: error("CoinHistoricalChartPointDtoSerializer only supports JSON")
      val arr: JsonArray = jsonDecoder.decodeJsonElement().jsonArray

      val t = arr.getOrNull(0)?.jsonPrimitive?.longOrNull
         ?: error("timestampMillis (index 0) is required")
      val v = arr.getOrNull(1)?.jsonPrimitive?.doubleOrNull

      return CoinHistoricalChartPointDto(timestampMillis = t, value = v)
   }

   override fun serialize(encoder: Encoder, value: CoinHistoricalChartPointDto) {
      val jsonEncoder = encoder as? JsonEncoder
         ?: error("CoinHistoricalChartPointDtoSerializer only supports JSON")
      val json = buildJsonArray {
         add(JsonPrimitive(value.timestampMillis))
         add(value.value?.let(::JsonPrimitive) ?: JsonNull)
      }
      jsonEncoder.encodeJsonElement(json)
   }
}