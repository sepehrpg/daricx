package com.daricx.ui.visualizations

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.sp


// Data class to define colored ranges of the horizontal bar
data class BarRange(
    val start: Int,     // start percentage (0..100)
    val end: Int,       // end percentage (0..100)
    val color: Color
)

@Composable
fun HorizontalGauge(
    modifier: Modifier = Modifier,
    value: Int,                         // Current value (0..100)
    strokeHeight: Dp = 7.dp,
    markerSize: Dp = 10.dp,
    fontSize: Int = 12,
    ranges: List<BarRange> = listOf(
        BarRange(0, 25, Color(0xFFF57C00)),   // orange
        BarRange(25, 50, Color(0xFFCFD8DC)),  // gray
        BarRange(50, 75, Color(0xFF1565C0)),  // blue
        BarRange(75, 100, Color(0xFF4CAF50)), // green
    ),
    trackColor: Color = Color(0xFFE9EDF3)     // full bar background
) {
    // Clamp and animate value smoothly
    val clamped = value.coerceIn(0, 100)
    val animatedPortion by animateFloatAsState(targetValue = clamped / 100f, label = "barPortion")

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Value display (bold + /100 in gray)
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = clamped.toString(),
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = fontSize.sp),
                color = Color.Black
            )
            Text(
                text = "/100",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = fontSize.sp),
                color = Color.Gray,
            )
        }

        Spacer(Modifier.height(0.dp))

        // Horizontal bar with rounded ends and marker
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(markerSize), // enough space for bar + marker
            contentAlignment = Alignment.CenterStart
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val barHeight = strokeHeight.toPx()
                val barTop = size.height / 2 - barHeight / 2
                val barRect = Rect(0f, barTop, size.width, barTop + barHeight)

                // 1) Draw the full background bar with rounded ends
                drawRoundRect(
                    color = trackColor,
                    topLeft = barRect.topLeft,
                    size = barRect.size,
                    cornerRadius = CornerRadius(barHeight / 2, barHeight / 2)
                )

                // 2) Clip to the same rounded-rect, so only the overall bar has rounded ends
                val clip = Path().apply {
                    addRoundRect(RoundRect(barRect, CornerRadius(barHeight / 2, barHeight / 2)))
                }
                clipPath(clip) {
                    // Draw each colored range as plain rectangles (no corner radius)
                    ranges.forEach { r ->
                        val startX = (r.start.coerceIn(0, 100) / 100f) * size.width
                        val endX = (r.end.coerceIn(0, 100) / 100f) * size.width
                        drawRect(
                            color = r.color,
                            topLeft = Offset(startX, barTop),
                            size = Size(endX - startX, barHeight)
                        )
                    }
                }

                // 3) Draw the circular marker on top
                val markerX = animatedPortion * size.width
                val markerY = size.height / 2
                val radius = markerSize.toPx() / 2f

                // White fill to create a solid knob
                drawCircle(
                    color = Color.White,
                    radius = radius,
                    center = Offset(markerX, markerY)
                )

                // Subtle outline
                drawCircle(
                    color = Color(0xFF90A4AE),
                    radius = radius,
                    center = Offset(markerX, markerY),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun HorizontalGaugeSample(value: Int = 70) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(Modifier.width(100.dp).height(50.dp)){
            HorizontalGauge(
                modifier = Modifier.fillMaxWidth(),
                value = value,
            )
        }
    }
}
