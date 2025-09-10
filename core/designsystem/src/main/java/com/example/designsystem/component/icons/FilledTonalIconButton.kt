package com.example.designsystem.component.icons


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppFilledTonalIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = AppIconButtonDefaults.tonalColors(),
    content: @Composable () -> Unit,
) {
    FilledTonalIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        content = content
    )
}

@ThemePreviews
@Composable
private fun AppFilledTonalIconButtonPreview() {
    AppThemedPreview {
        Column(Modifier.padding(12.dp)) {
            AppFilledTonalIconButton(onClick = {}) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings")
            }
        }
    }
}
