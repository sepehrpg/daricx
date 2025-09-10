package com.example.designsystem.component.cards


import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppElevatedCard(
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = AppCardDefaults.Shape,
    colors: CardColors = AppCardDefaults.elevatedColors(),
    elevation: CardElevation = AppCardDefaults.elevatedElevation(),
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation,
        content = content
    )
}

@Composable
fun AppElevatedCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = AppCardDefaults.Shape,
    colors: CardColors = AppCardDefaults.elevatedColors(),
    elevation: CardElevation = AppCardDefaults.elevatedElevation(),
    content: @Composable ColumnScope.() -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation,
        content = content
    )
}

@ThemePreviews
@Composable
private fun AppElevatedCardPreview() {
    AppThemedPreview {
        AppElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                "AppElevatedCard (plain)",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        AppElevatedCard(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(
                "AppElevatedCard (clickable)",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
