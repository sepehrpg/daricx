package com.example.designsystem.component.icons


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppFilledTonalIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconToggleButtonColors = AppIconButtonDefaults.tonalToggleColors(),
    content: @Composable () -> Unit,
) {
    FilledTonalIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        content = content
    )
}

@ThemePreviews
@Composable
private fun AppFilledTonalIconToggleButtonPreview() {
    AppThemedPreview {
        var checked by remember { mutableStateOf(false) }
        Column(Modifier.padding(12.dp)) {
            AppFilledTonalIconToggleButton(
                checked = checked,
                onCheckedChange = { checked = it }
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = "Tonal Toggle")
            }
        }
    }
}
