package com.daricx.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlin.math.abs

@Composable
fun PercentChangeBadge(
    percent: Double,
    modifier: Modifier = Modifier,
    positiveColor: Color = Color(0xFF16A34A),
    negativeColor: Color = Color(0xFFDC2626)
) {
    val isPositive = percent >= 0.0
    val color = if (isPositive) positiveColor else negativeColor
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.height(20.dp)
    ) {
        Icon(
            imageVector = if (isPositive) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
            contentDescription = null,
            tint = color,
            modifier = Modifier.padding(end = 2.dp)
        )
        Text(
            text = "${if (isPositive) "+" else "−"}${"%.2f".format(abs(percent))}%",
            style = MaterialTheme.typography.labelMedium,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}