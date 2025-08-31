package com.daricx.ui.visualizations

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp


/**
 * Minimal sparkline. Values can be any float; it's normalized internally.
 */
@Composable
fun Sparkline(
    values: List<Float>,
    modifier: Modifier = Modifier,
    strokeColor: Color = Color(0xFF16A34A), // M3 green-ish
    strokeWidthDp: Float = 2f
) {
    if (values.isEmpty()) return
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val min = values.minOrNull() ?: 0f
        val max = values.maxOrNull() ?: 1f
        val span = (max - min).takeIf { it != 0f } ?: 1f

        val stepX = w / (values.lastIndex.takeIf { it > 0 } ?: 1).coerceAtLeast(1)
        val path = Path()

        values.forEachIndexed { i, v ->
            val x = stepX * i
            val yNorm = (v - min) / span
            val y = h - (yNorm * h)
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        drawPath(path, color = strokeColor, style = Stroke(width = strokeWidthDp.dp.toPx()))
    }
}