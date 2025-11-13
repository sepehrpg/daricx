package com.daricx.ui

import com.example.designsystem.theme.ThemePreviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



/**
 * A reusable notice for demo/in-development features.
 *
 * Example:
 * 🚀 Coming Soon
 * This feature will be available in the next update.
 */
@Composable
fun ComingSoonNoticeText(
    modifier: Modifier = Modifier,
    title: String = "🚀 Coming Soon 🚀",
    message: String = "This feature will be available in the next update.",
    centered: Boolean = true,
    pill: Boolean = true,
    padding: Dp = 16.dp,
) {
    val horizontalAlignment =
        if (centered) Alignment.CenterHorizontally else Alignment.Start

    val textAlign =
        if (centered) TextAlign.Center else TextAlign.Start

    val content: @Composable () -> Unit = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = horizontalAlignment
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = textAlign,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = textAlign,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
    if (pill) {
        Surface(
            modifier = modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 0.dp,
            shadowElevation = 0.dp
        ) {
            content()
        }
    } else {
        content()
    }
}


@ThemePreviews
@Composable
private fun ComingSoonNoticePreview() {
    MaterialTheme {
        ComingSoonNoticeText()
    }
}