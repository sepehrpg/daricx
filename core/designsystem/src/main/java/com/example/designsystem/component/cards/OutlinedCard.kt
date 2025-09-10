package com.example.designsystem.component.cards


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppOutlinedCard(
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = AppCardDefaults.Shape,
    colors: CardColors = AppCardDefaults.outlinedColors(),
    elevation: CardElevation = AppCardDefaults.outlinedElevation(),
    border: BorderStroke = AppCardDefaults.outlinedBorder(),
    content: @Composable ColumnScope.() -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        content = content
    )
}

@Composable
fun AppOutlinedCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = AppCardDefaults.Shape,
    colors: CardColors = AppCardDefaults.outlinedColors(),
    elevation: CardElevation = AppCardDefaults.outlinedElevation(),
    border: BorderStroke = AppCardDefaults.outlinedBorder(),
    content: @Composable ColumnScope.() -> Unit
) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        content = content
    )
}

@ThemePreviews
@Composable
private fun AppOutlinedCardPreview() {
    AppThemedPreview {
        AppOutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                "AppOutlinedCard (plain)",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        AppOutlinedCard(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                "AppOutlinedCard (clickable)",
                modifier = Modifier.padding(16.dp),
               // style = MaterialTheme.typTypography.bodyMedium
            )
        }
    }
}
