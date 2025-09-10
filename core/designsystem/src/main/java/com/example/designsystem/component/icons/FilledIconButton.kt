package com.example.designsystem.component.icons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppFilledIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = AppIconButtonDefaults.filledColors(),
    content: @Composable () -> Unit,
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        content = content
    )
}

@ThemePreviews
@Composable
private fun AppFilledIconButtonPreview() {
    AppThemedPreview {
        Column(Modifier.padding(12.dp)) {
            AppFilledIconButton(onClick = {}) {
                Icon(Icons.Filled.Favorite, contentDescription = "Fav")
            }
            Spacer(Modifier.height(12.dp))
            AppFilledIconButton(onClick = {}, enabled = false) {
                Icon(Icons.Filled.Favorite, contentDescription = "Fav Disabled")
            }
        }
    }
}
