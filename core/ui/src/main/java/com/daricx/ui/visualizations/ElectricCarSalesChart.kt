package com.daricx.ui.visualizations
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import kotlinx.coroutines.runBlocking
import android.text.Layout
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.compose.common.component.fixed
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.insets
import com.patrykandpatrick.vico.compose.common.shape.markerCorneredShape
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.common.LayeredComponent
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.shader.verticalGradient
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.max
import kotlin.random.Random






@Composable
fun CoinSparklineChart(
    values: List<Float>,
    modifier: Modifier = Modifier,
    lineColor: Color = Color(0xFF4CAF50),
    normalize: Boolean = true,
    mode: String = "delta"
) {
    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(values, normalize, mode) {
        if (values.isNotEmpty()) {
            val yValues = if (normalize) {
                when (mode) {
                    "delta" -> {
                        val base = values.first()
                        values.map { ((it - base) / base * 100f) }
                    }
                    "scale" -> {
                        val min = values.minOrNull() ?: 0f
                        val max = values.maxOrNull() ?: 1f
                        if (max - min == 0f) values.map { 0f }
                        else values.map { ((it - min) / (max - min) * 100f) }
                    }
                    else -> values
                }
            } else values

            modelProducer.runTransaction {
                lineSeries {
                    series(yValues.indices.toList(), yValues)
                }
            }
        }
    }

    ElectricCarSalesChart(
        modifier = modifier,
        modelProducer = modelProducer,
        lineColor = lineColor
    )
}













private val RangeProvider = CartesianLayerRangeProvider.fixed(maxY = 100.0)
private val YDecimalFormat = DecimalFormat("#.##'%'")
private val StartAxisValueFormatter = CartesianValueFormatter.decimal(YDecimalFormat)
private val MarkerValueFormatter = DefaultCartesianMarker.ValueFormatter.default(YDecimalFormat)



@Composable
fun ElectricCarSalesChart(
    modifier: Modifier = Modifier,
    modelProducer: CartesianChartModelProducer,
    lineColor:Color =  Color(0xFF4CAF50),
) {
    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                lineProvider = LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(
                        fill = LineCartesianLayer.LineFill.single(fill(lineColor)),
                        areaFill = LineCartesianLayer.AreaFill.single(
                            fill(
                                ShaderProvider.verticalGradient(
                                    arrayOf(Color.Transparent, Color.Transparent)
                                )
                            )
                            /*fill(
                                ShaderProvider.verticalGradient(
                                    arrayOf(lineColor.copy(alpha = 0.4f), Color.Transparent)
                                )
                            )*/
                        ),
                    )
                ),
                rangeProvider = CartesianLayerRangeProvider.auto()
            )
        ),
        modelProducer = modelProducer,
        modifier.height(50.dp).background(Color.Transparent),
        rememberVicoScrollState(scrollEnabled = false),
    )
}

private val x = (2010..2023).toList()
private val x7Day = (1..7).toList()
private val y = listOf<Number>(0.2, 11.4, 33.1, 55.8, 15, 52, 1, 2, 0.5, 6, 7, 100, 30, 10)

@Composable
fun ElectricCarSalesChart(
    modifier: Modifier = Modifier,
    lineColor:Color =  Color(0xFF4CAF50),
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            // Learn more: https://patrykandpatrick.com/vmml6t.
            lineSeries { series(x, y) }
        }
    }
    ElectricCarSalesChart(modifier= modifier , modelProducer = modelProducer, lineColor =   lineColor)
}

@Composable
@Preview(showBackground = false, showSystemUi = false)
private fun Preview() {
    val modelProducer = remember { CartesianChartModelProducer() }
    // Use `runBlocking` only for previews, which don’t support asynchronous execution.
    runBlocking {
        modelProducer.runTransaction {
            // Learn more: https://patrykandpatrick.com/vmml6t.
            lineSeries { series(x, y) }
        }
    }
    PreviewBox { ElectricCarSalesChart(modelProducer = modelProducer) }
}

@Composable
fun PreviewBox(content: @Composable BoxScope.() -> Unit) {
    Box(modifier = Modifier.background(Color.Transparent).padding(16.dp), content = content)
}

@Composable
internal fun rememberMarker(
    valueFormatter: DefaultCartesianMarker.ValueFormatter =
        DefaultCartesianMarker.ValueFormatter.default(),
    showIndicator: Boolean = true,
): CartesianMarker {
    val labelBackgroundShape = markerCorneredShape(CorneredShape.Corner.Rounded)
    val labelBackground =
        rememberShapeComponent(
            fill = fill(MaterialTheme.colorScheme.background),
            shape = labelBackgroundShape,
            strokeThickness = 1.dp,
            strokeFill = fill(MaterialTheme.colorScheme.outline),
        )
    val label =
        rememberTextComponent(
            color = MaterialTheme.colorScheme.onSurface,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            padding = insets(8.dp, 4.dp),
            background = labelBackground,
            minWidth = TextComponent.MinWidth.fixed(40.dp),
        )
    val indicatorFrontComponent =
        rememberShapeComponent(fill(MaterialTheme.colorScheme.surface), CorneredShape.Pill)
    val guideline = rememberAxisGuidelineComponent()
    return rememberDefaultCartesianMarker(
        label = label,
        valueFormatter = valueFormatter,
        indicator =
        if (showIndicator) {
            { color ->
                LayeredComponent(
                    back = ShapeComponent(fill(color.copy(alpha = 0.15f)), CorneredShape.Pill),
                    front =
                    LayeredComponent(
                        back = ShapeComponent(fill = fill(color), shape = CorneredShape.Pill),
                        front = indicatorFrontComponent,
                        padding = insets(5.dp),
                    ),
                    padding = insets(10.dp),
                )
            }
        } else {
            null
        },
        indicatorSize = 36.dp,
        guideline = guideline,
    )
}






/*@Composable
fun CoinSparklineChart(
    values: List<Float>,
    mode: ChartScalingMode = pickModeFor7d(values),
    modifier: Modifier = Modifier,
) {
    // transform Y
    val yValue = remember(values, mode) {
        when (mode) {
            ChartScalingMode.PriceLinear -> values
            ChartScalingMode.PriceLog -> toPriceLog(values)
            ChartScalingMode.PercentDelta -> toPercentDelta(values)
            ChartScalingMode.MinMax -> toMinMax(values)
        }.let { downsampleStride(it, target = 40) }
    }

    // range
    val range = remember(yValue, mode) {
        when (mode) {
            ChartScalingMode.PercentDelta -> symmetricAroundZero(yValue)
            else -> autoRangeWithPadding(yValue)
        }
    }

    // color: green if last >= first
    val isUp = (values.lastOrNull() ?: 0f) >= (values.firstOrNull() ?: 0f)
    val lineColor = if (isUp) Color(0xFF2E7D32) else Color(0xFFC62828)

    val modelProducer = remember { CartesianChartModelProducer() }

    LaunchedEffect(yValue) {
        if (yValue.isNotEmpty()) {
            modelProducer.runTransaction {
                lineSeries { series(yValue.indices.toList(), yValue) }
            }
        }
    }
    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                lineProvider = LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(
                        fill = LineCartesianLayer.LineFill.single(fill(lineColor)),
                        areaFill = LineCartesianLayer.AreaFill.single(
                            fill(
                                ShaderProvider.verticalGradient(
                                    arrayOf(lineColor.copy(alpha = 0.30f), Color.Transparent)
                                )
                            )
                        ),
                    )
                ),
                rangeProvider = range,
            ),
        ),
        modelProducer = modelProducer,
        modifier = modifier
            .height(40.dp)
            .width(70.dp)
            .background(Color.Transparent),
        scrollState = rememberVicoScrollState(scrollEnabled = false),
    )
}*/


/* ---------- Scaling Modes ---------- */
sealed class ChartScalingMode {
    data object PriceLinear : ChartScalingMode()
    data object PriceLog : ChartScalingMode()           // log(p) برای بازه‌های بزرگ
    data object PercentDelta : ChartScalingMode()       // (p/base - 1) * 100
    data object MinMax : ChartScalingMode()             // نگاشت به 0..100
}

/* ---------- Data transforms ---------- */
private fun toPercentDelta(values: List<Float>): List<Float> {
    if (values.isEmpty()) return values
    val base = values.first()
    if (base == 0f) return List(values.size) { 0f }
    return values.map { ((it - base) / base) * 100f }
}

private fun toMinMax(values: List<Float>): List<Float> {
    if (values.isEmpty()) return values
    val min = values.minOrNull() ?: 0f
    val maxV = values.maxOrNull() ?: min
    val span = (maxV - min).takeIf { it != 0f } ?: 1f
    return values.map { ((it - min) / span) * 100f }
}

private fun toPriceLog(values: List<Float>): List<Float> =
    values.map { v -> if (v > 0f) ln(v) else Float.NaN }.filter { it.isFinite() }

/* ---------- Range providers ---------- */
private fun autoRangeWithPadding(y: List<Float>, padRatio: Float = 0.08f): CartesianLayerRangeProvider {
    val min = y.minOrNull() ?: 0f
    val maxV = y.maxOrNull() ?: min
    val span = (maxV - min).takeIf { it > 0f } ?: 1f
    val pad = span * padRatio
    return CartesianLayerRangeProvider.fixed((min - pad).toDouble(), (maxV + pad).toDouble())
}

private fun symmetricAroundZero(y: List<Float>, padRatio: Float = 0.08f): CartesianLayerRangeProvider {
    val min = y.minOrNull() ?: 0f
    val maxV = y.maxOrNull() ?: 0f
    val m = max(abs(min), abs(maxV))
    val pad = max(m * padRatio, 1e-6f)
    return CartesianLayerRangeProvider.fixed((-m - pad).toDouble(), (m + pad).toDouble())
}

/* ---------- Downsample برای کارایی ---------- */
fun downsampleStride(values: List<Float>, target: Int = 40): List<Float> {
    if (values.size <= target || target <= 0) return values
    val step = values.size / target.toFloat()
    return List(target) { i -> values[(i * step).toInt().coerceIn(0, values.lastIndex)] }
}

/* ---------- انتخاب بهترین mode برای داده‌های 7 روزه ---------- */
fun pickModeFor7d(values: List<Float>): ChartScalingMode {
    if (values.isEmpty()) return ChartScalingMode.PercentDelta
    val first = values.first()
    if (first == 0f) return ChartScalingMode.MinMax

    val min = values.minOrNull() ?: first
    val maxV = values.maxOrNull() ?: first
    val relRange = (maxV - min) / abs(first) // تغییر نسبی

    // اگر نوسان کمتر از 0.25% بود => MinMax، وگرنه PercentDelta
    return if (relRange < 0.0025f) ChartScalingMode.MinMax else ChartScalingMode.PercentDelta
}



// Data class to hold coin information
data class ElectricCarSalesData(
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCap: String,
    val price: String,
    val priceChangePercent: Double,
    val chartData: List<Float>
)

// --- Mock Data ---
// In a real app, this data would come from a ViewModel connected to an API
val mockCoins = listOf(
    ElectricCarSalesData(30, "PEPE", "PEPE", "$4.72B", "$0.00001124", 3.21, generateRandomChartData()),
    ElectricCarSalesData(22, "SHIB", "SHIBA INU", "$7.76B", "$0.00001318", 2.26, generateRandomChartData()),
    ElectricCarSalesData(52, "BONK", "BONK", "$1.96B", "$0.00002441", 2.34, generateRandomChartData()),
    ElectricCarSalesData(77, "FLOKI", "FLOKI", "$1.02B", "$0.0001075", 2.10, generateRandomChartData()),
    ElectricCarSalesData(73, "PUMP", "PUMP", "$1.23B", "$0.003491", 2.65, generateRandomChartData()),
    ElectricCarSalesData(93, "JASMY", "JASMY", "$829.28M", "$0.01677", 3.42, generateRandomChartData()),
    ElectricCarSalesData(97, "GALA", "GALA", "$801.95M", "$0.01756", 3.48, generateRandomChartData()),
    ElectricCarSalesData(30, "PEPE", "PEPE", "$4.72B", "$0.00001124", 3.21, generateRandomChartData()),
    ElectricCarSalesData(22, "SHIB", "SHIBA INU", "$7.76B", "$0.00001318", 2.26, generateRandomChartData()),
    ElectricCarSalesData(52, "BONK", "BONK", "$1.96B", "$0.00002441", 2.34, generateRandomChartData()),
    ElectricCarSalesData(77, "FLOKI", "FLOKI", "$1.02B", "$0.0001075", 2.10, generateRandomChartData()),
    ElectricCarSalesData(73, "PUMP", "PUMP", "$1.23B", "$0.003491", 2.65, generateRandomChartData()),
    ElectricCarSalesData(93, "JASMY", "JASMY", "$829.28M", "$0.01677", 3.42, generateRandomChartData()),
    ElectricCarSalesData(97, "GALA", "GALA", "$801.95M", "$0.01756", 3.48, generateRandomChartData()),
    ElectricCarSalesData(30, "PEPE", "PEPE", "$4.72B", "$0.00001124", 3.21, generateRandomChartData()),
    ElectricCarSalesData(22, "SHIB", "SHIBA INU", "$7.76B", "$0.00001318", 2.26, generateRandomChartData()),
    ElectricCarSalesData(52, "BONK", "BONK", "$1.96B", "$0.00002441", 2.34, generateRandomChartData()),
    ElectricCarSalesData(77, "FLOKI", "FLOKI", "$1.02B", "$0.0001075", 2.10, generateRandomChartData()),
    ElectricCarSalesData(73, "PUMP", "PUMP", "$1.23B", "$0.003491", 2.65, generateRandomChartData()),
    ElectricCarSalesData(93, "JASMY", "JASMY", "$829.28M", "$0.01677", 3.42, generateRandomChartData()),
    ElectricCarSalesData(97, "GALA", "GALA", "$801.95M", "$0.01756", 3.48, generateRandomChartData()),
    ElectricCarSalesData(30, "PEPE", "PEPE", "$4.72B", "$0.00001124", 3.21, generateRandomChartData()),
    ElectricCarSalesData(22, "SHIB", "SHIBA INU", "$7.76B", "$0.00001318", 2.26, generateRandomChartData()),
    ElectricCarSalesData(52, "BONK", "BONK", "$1.96B", "$0.00002441", 2.34, generateRandomChartData()),
    ElectricCarSalesData(77, "FLOKI", "FLOKI", "$1.02B", "$0.0001075", 2.10, generateRandomChartData()),
    ElectricCarSalesData(73, "PUMP", "PUMP", "$1.23B", "$0.003491", 2.65, generateRandomChartData()),
    ElectricCarSalesData(93, "JASMY", "JASMY", "$829.28M", "$0.01677", 3.42, generateRandomChartData()),
    ElectricCarSalesData(97, "GALA", "GALA", "$801.95M", "$0.01756", 3.48, generateRandomChartData()),
    ElectricCarSalesData(30, "PEPE", "PEPE", "$4.72B", "$0.00001124", 3.21, generateRandomChartData()),
    ElectricCarSalesData(22, "SHIB", "SHIBA INU", "$7.76B", "$0.00001318", 2.26, generateRandomChartData()),
    ElectricCarSalesData(52, "BONK", "BONK", "$1.96B", "$0.00002441", 2.34, generateRandomChartData()),
    ElectricCarSalesData(77, "FLOKI", "FLOKI", "$1.02B", "$0.0001075", 2.10, generateRandomChartData()),
    ElectricCarSalesData(73, "PUMP", "PUMP", "$1.23B", "$0.003491", 2.65, generateRandomChartData()),
    ElectricCarSalesData(93, "JASMY", "JASMY", "$829.28M", "$0.01677", 3.42, generateRandomChartData()),
    ElectricCarSalesData(97, "GALA", "GALA", "$801.95M", "$0.01756", 3.48, generateRandomChartData()),
    ElectricCarSalesData(30, "PEPE", "PEPE", "$4.72B", "$0.00001124", 3.21, generateRandomChartData()),
    ElectricCarSalesData(22, "SHIB", "SHIBA INU", "$7.76B", "$0.00001318", 2.26, generateRandomChartData()),
    ElectricCarSalesData(52, "BONK", "BONK", "$1.96B", "$0.00002441", 2.34, generateRandomChartData()),
    ElectricCarSalesData(77, "FLOKI", "FLOKI", "$1.02B", "$0.0001075", 2.10, generateRandomChartData()),
    ElectricCarSalesData(73, "PUMP", "PUMP", "$1.23B", "$0.003491", 2.65, generateRandomChartData()),
    ElectricCarSalesData(93, "JASMY", "JASMY", "$829.28M", "$0.01677", 3.42, generateRandomChartData()),
    ElectricCarSalesData(97, "GALA", "GALA", "$801.95M", "$0.01756", 3.48, generateRandomChartData()),
    ElectricCarSalesData(30, "PEPE", "PEPE", "$4.72B", "$0.00001124", 3.21, generateRandomChartData()),
    ElectricCarSalesData(22, "SHIB", "SHIBA INU", "$7.76B", "$0.00001318", 2.26, generateRandomChartData()),
    ElectricCarSalesData(52, "BONK", "BONK", "$1.96B", "$0.00002441", 2.34, generateRandomChartData()),
    ElectricCarSalesData(77, "FLOKI", "FLOKI", "$1.02B", "$0.0001075", 2.10, generateRandomChartData()),
    ElectricCarSalesData(73, "PUMP", "PUMP", "$1.23B", "$0.003491", 2.65, generateRandomChartData()),
    ElectricCarSalesData(93, "JASMY", "JASMY", "$829.28M", "$0.01677", 3.42, generateRandomChartData()),
    ElectricCarSalesData(97, "GALA", "GALA", "$801.95M", "$0.01756", 3.48, generateRandomChartData()),
    ElectricCarSalesData(30, "PEPE", "PEPE", "$4.72B", "$0.00001124", 3.21, generateRandomChartData()),
    ElectricCarSalesData(22, "SHIB", "SHIBA INU", "$7.76B", "$0.00001318", 2.26, generateRandomChartData()),
    ElectricCarSalesData(52, "BONK", "BONK", "$1.96B", "$0.00002441", 2.34, generateRandomChartData()),
    ElectricCarSalesData(77, "FLOKI", "FLOKI", "$1.02B", "$0.0001075", 2.10, generateRandomChartData()),
    ElectricCarSalesData(73, "PUMP", "PUMP", "$1.23B", "$0.003491", 2.65, generateRandomChartData()),
    ElectricCarSalesData(93, "JASMY", "JASMY", "$829.28M", "$0.01677", 3.42, generateRandomChartData()),
    ElectricCarSalesData(97, "GALA", "GALA", "$801.95M", "$0.01756", 3.48, generateRandomChartData())
)

fun generateRandomChartData(): List<Float> {
    return List(20) { Random.nextFloat() * 100 }
}