@file:Suppress("MagicNumber")

package com.daricx.ui.visualizations

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

// Data class to define a colored range of the gauge
data class GaugeRange(
    val start: Int,     // start percentage (0..100)
    val end: Int,       // end percentage (0..100)
    val color: Color
)

@Composable
fun Gauge(
    modifier: Modifier = Modifier,
    value: Int,                          // Current value (0..100)
    strokeWidth: Dp = 5.dp,
    trackColor: Color = Color(0xFFE9EDF3),
    markerSize: Dp = 8.dp,
    fontSize: Int = 12,
    ranges: List<GaugeRange> = listOf(
        GaugeRange(0, 20, Color(0xFFE53935)),    // red
        GaugeRange(20, 40, Color(0xFFFB8C00)),   // orange
        GaugeRange(40, 60, Color(0xFFFDD835)),   // yellow
        GaugeRange(60, 80, Color(0xFF7CB342)),   // light green
        GaugeRange(80, 100, Color(0xFF2E7D32))   // green
    )
) {
    // Stroke definition for arcs
    val stroke = with(LocalDensity.current) { Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round) }

    // Bottom semicircle: start from 180° and sweep +180° (to 360°)
    val startAngle = 180f
    val totalSweep = 180f

    // Clamp value and animate smoothly
    val clamped = value.coerceIn(0, 100)
    val animatedPortion by animateFloatAsState(targetValue = clamped / 100f, label = "gaugePortion")

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val diameter = min(w, h)
            val arcSize = Size(diameter, diameter)
            val topLeft = Offset((w - diameter) / 2f, (h - diameter) / 2f)

            // Draw background track arc
            drawArc(
                color = trackColor,
                startAngle = startAngle,
                sweepAngle = totalSweep,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = stroke
            )

            // Draw colored ranges
            ranges.forEach { r ->
                val s = (r.start.coerceIn(0, 100)) / 100f
                val e = (r.end.coerceIn(0, 100)) / 100f
                val sweep = (e - s) * totalSweep
                val segStart = startAngle + s * totalSweep
                if (sweep != 0f) {
                    drawArc(
                        color = r.color,
                        startAngle = segStart,
                        sweepAngle = sweep,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = stroke
                    )
                }
            }

            // Calculate marker position
            val angleDeg = startAngle + animatedPortion * totalSweep
            val angleRad = Math.toRadians(angleDeg.toDouble())
            val radius = diameter / 2f
            val center = Offset(topLeft.x + radius, topLeft.y + radius)

            val px = center.x + radius * cos(angleRad).toFloat()
            val py = center.y + radius * sin(angleRad).toFloat()

            // Draw marker as a small circle
            drawCircle(
                color = Color.White,
                radius = markerSize.toPx() / 2f + stroke.width * 0.25f,
                center = Offset(px, py)
            )
            drawCircle(
                color = Color(0xFF90A4AE),
                radius = markerSize.toPx() / 2f,
                center = Offset(px, py),
                style = Stroke(width = 2.dp.toPx())
            )
        }

        // Center texts (title and value)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = clamped.toString(),
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.SemiBold, fontSize = fontSize.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GaugeSample(modifier: Modifier = Modifier, value: Int = 80) {
    // Simple container for the Gauge
    Box(
        modifier = modifier
            //.aspectRatio(1.8f) // ratio similar to the provided image
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
       Box(Modifier.width(120.dp).height(50.dp)){
           Gauge(
               modifier = Modifier.fillMaxSize(),
               value = value,
           )
       }
    }
}
