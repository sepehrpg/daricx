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
import kotlin.random.Random

private val RangeProvider = CartesianLayerRangeProvider.fixed(maxY = 100.0)
private val YDecimalFormat = DecimalFormat("#.##'%'")
private val StartAxisValueFormatter = CartesianValueFormatter.decimal(YDecimalFormat)
private val MarkerValueFormatter = DefaultCartesianMarker.ValueFormatter.default(YDecimalFormat)

@Composable
private fun ElectricCarSalesChart(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier = Modifier,
) {
    val lineColor = Color(0xffa485e0)
    CartesianChartHost(
        rememberCartesianChart(
            rememberLineCartesianLayer(
                lineProvider =
                LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(
                        fill = LineCartesianLayer.LineFill.single(fill(lineColor)),
                        areaFill =
                        LineCartesianLayer.AreaFill.single(
                            fill(
                                ShaderProvider.verticalGradient(
                                    arrayOf(lineColor.copy(alpha = 0.4f), Color.Transparent)
                                )
                            )
                        ),
                    )
                ),
                rangeProvider = RangeProvider,
            ),
            marker = rememberMarker(MarkerValueFormatter),
        ),
        modelProducer,
        modifier.height(50.dp).background(Color.Transparent),
        rememberVicoScrollState(scrollEnabled = false),
    )
}

private val x = (2010..2023).toList()
private val y = listOf<Number>(0.2, 11.4, 33.1, 55.8, 15, 52, 1, 2, 0.5, 6, 7, 100, 30, 10)

@Composable
fun ElectricCarSalesChart(modifier: Modifier = Modifier) {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            // Learn more: https://patrykandpatrick.com/vmml6t.
            lineSeries { series(x, y) }
        }
    }
    ElectricCarSalesChart(modelProducer, modifier)
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
    PreviewBox { ElectricCarSalesChart(modelProducer) }
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
    ElectricCarSalesData(97, "GALA", "GALA", "$801.95M", "$0.01756", 3.48, generateRandomChartData())
)

fun generateRandomChartData(): List<Float> {
    return List(20) { Random.nextFloat() * 100 }
}