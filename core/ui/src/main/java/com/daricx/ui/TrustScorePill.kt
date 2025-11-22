package com.daricx.ui


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews


@Composable
fun TrustScorePill(
    score: Int?,
    modifier: Modifier = Modifier,
    max: Int = 10,
) {
    val clamped = score?.coerceIn(0, max)

    if (clamped == null) {
        Row(
            modifier = modifier
                .background(
                    MaterialTheme.colorScheme.surfaceVariant,
                    RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "—",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
            )
        }
        return
    }

    val fg = trustColorFor(clamped)
    val isLight = MaterialTheme.colorScheme.background.luminance() > 0.5


    val bg = if (isLight) fg.copy(alpha = 0.20f) else fg.copy(alpha = 0.35f)

    val animatedBg by animateColorAsState(targetValue = bg, label = "trust-bg")

    Row(
        modifier = modifier
            .background(animatedBg, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$clamped/$max",
            color = fg,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}

/**
 * Returns a distinct foreground color for scores 0..10.
 */
@ReadOnlyComposable
private fun trustColorFor(score: Int): Color {
    // 11 stops (0..10)
    val stops = listOf(
        Color(0xFFEF4444), // 0  - red 500
        Color(0xFFF05252), // 1
        Color(0xFFF59E0B), // 2  - amber 500
        Color(0xFFF59D38), // 3
        Color(0xFFFACC15), // 4  - yellow 400
        Color(0xFF9BB470), // 5  - lime 200
        Color(0xFFA3E635), // 6  - lime 400
        Color(0xFF74C58F), // 7  - green 300
        Color(0xFF34D399), // 8  - emerald 400
        Color(0xFF22C55E), // 9  - green 500
        Color(0xFF16A34A)  // 10 - green 600
    )
    return stops[score.coerceIn(0, 10)]
}



/**
 * Trust score pill like CoinGecko (e.g., "9/10") with 10 distinct colors.
 * score: 0..10 (null -> neutral).
 */
@Composable
fun TrustScorePill(
    score: Int?,
    modifier: Modifier = Modifier,
    max: Int = 10,
    // Allow override from design system if needed:
    neutralBg: Color = MaterialTheme.colorScheme.surfaceVariant,
    neutralFg: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    val clamped = score?.coerceIn(0, max)
    val (fg, bg) = if (clamped == null) {
        neutralFg to neutralBg
    } else {
        val idx = clamped.coerceIn(0, max) // 0..10
        val base = trustColorFor(idx)      // foreground color
        val bgCol = base.copy(alpha = 0.20f)
        base to bgCol
    }

    val animatedBg by animateColorAsState(targetValue = if (clamped == null) neutralBg else bg, label = "trust-bg")

    val cd = if (clamped == null) "No trust score" else "Trust score $clamped out of $max"

    Row(
        modifier = modifier
            .semantics { contentDescription = cd }
            .background(animatedBg, RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = clamped?.let { "$it/$max" } ?: "—",
            color = fg,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}




// ------------------------- Previews -------------------------

@ThemePreviews
@Composable
private fun TrustScorePillPreview() {
    AppThemedPreview {
        Column {
            val samples = listOf(0,1,2,3,4,5,6,7,8,9,10, null)
            samples.forEach {
                Box(Modifier.padding(6.dp)) { TrustScorePill(score = it) }
            }
        }
    }
}
