package com.daricx.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.common.numbers.facade.roundToString
import com.example.common.numbers.precision.FixedPricePrecisionPolicy
import com.example.designsystem.component.text.AppText
import com.example.designsystem.component.icons.AppIcon
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import kotlin.math.abs


/**
 * Core rendering for percent change.
 *
 * @param percent value (positive / negative)
 * @param showBackground true for pill style, false for badge style
 * @param positiveColor text/fg color for positive
 * @param negativeColor text/fg color for negative
 * @param positiveBg optional bg color (only used if showBackground = true)
 * @param negativeBg optional bg color (only used if showBackground = true)
 */
@Composable
fun PercentChangeView(
    percent: Number,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
    showBackground: Boolean,
    showIcon : Boolean = true,
    size: Dp = 18.dp,
    positiveColor: Color = Color(0xFF16A34A),
    negativeColor: Color = Color(0xFFDC2626),
    positiveBg: Color = Color(0x3316A34A),
    negativeBg: Color = Color(0x33DC2626)
) {
    val value = percent.toDouble()
    val isPositive = value >= 0.0
    val fg = if (isPositive) positiveColor else negativeColor
    val bg = if (isPositive) positiveBg else negativeBg
    val arrow = if (isPositive) AppIcons.KeyboardArrowUp else AppIcons.KeyboardArrowDown

    Row(
        modifier = if (showBackground) {
            modifier.background(bg, RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        } else modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showIcon){
            AppIcon(arrow, null, tint = fg, modifier = Modifier.padding(end = 2.dp).size(size))
        }

        AppText(
            text = "${if (isPositive) "+" else ""}${value.roundToString(1)}%",
            style = style,
            color = fg,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )
    }
}



@Composable
fun PercentChangeBadge(
    percent: Number,
    modifier: Modifier = Modifier,
    positiveColor: Color = Color(0xFF16A34A),
    negativeColor: Color = Color(0xFFDC2626)
) {
    PercentChangeView(
        percent = percent,
        modifier = modifier,
        showBackground = false,
        positiveColor = positiveColor,
        negativeColor = negativeColor
    )
}


@Composable
fun PercentChangePill(
    percent: Number,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
    positiveColor: Color = Color(0xFF16A34A),
    negativeColor: Color = Color(0xFFDC2626),
    positiveBg: Color = Color(0x3316A34A),
    negativeBg: Color = Color(0x33DC2626)
) {
    PercentChangeView(
        percent = percent,
        modifier = modifier,
        style = style,
        showBackground = true,
        positiveColor = positiveColor,
        negativeColor = negativeColor,
        positiveBg = positiveBg,
        negativeBg = negativeBg
    )
}


@ThemePreviews
@Composable
fun PercentChangeBadgePreview() {
    AppThemedPreview {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PercentChangeBadge(+2.34)
            PercentChangeBadge(-8.19)
        }
    }
}

@ThemePreviews
@Composable
fun PercentChangePillPreview() {
    AppThemedPreview {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            PercentChangePill(+0.01)
            PercentChangePill(-7.16)
        }
    }
}