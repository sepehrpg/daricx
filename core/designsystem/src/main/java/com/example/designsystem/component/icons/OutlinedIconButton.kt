package com.example.designsystem.component.icons


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppOutlinedIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = AppIconButtonDefaults.outlinedColors(),
    border: BorderStroke? = null,
    content: @Composable () -> Unit,
) {
    OutlinedIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        border = border,
        content = content
    )
}

@ThemePreviews
@Composable
private fun AppOutlinedIconButtonPreview() {
    AppThemedPreview {
        Column(Modifier.padding(12.dp)) {
            AppOutlinedIconButton(onClick = {},border = BorderStroke(1.dp, Color.Gray)) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }
        }
    }
}
