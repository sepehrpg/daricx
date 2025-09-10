package com.example.designsystem.component.icons


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

/**
 * Gallery preview for all DARICX IconButtons and IconToggleButtons.
 * Shows: Regular, Filled, Tonal, Outlined + their Toggle counterparts.
 */
@ThemePreviews
@Composable
private fun AppIconButtonsGalleryPreview() {
    AppThemedPreview {
        // toggle states
        var toggle1 by remember { mutableStateOf(false) }
        var toggle2 by remember { mutableStateOf(true) }
        var toggle3 by remember { mutableStateOf(false) }
        var toggle4 by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Regular Icon Button
            AppIconButton(onClick = {}) {
                Icon(Icons.Filled.Home, contentDescription = "Home")
            }

            // 2. Filled Icon Button
            AppFilledIconButton(onClick = {}) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
            }

            // 3. Filled Tonal Icon Button
            AppFilledTonalIconButton(onClick = {}) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings")
            }

            // 4. Outlined Icon Button
            AppOutlinedIconButton(onClick = {}) {
                Icon(Icons.Filled.Home, contentDescription = "Outlined Home")
            }

            // 5. Regular Icon Toggle Button
            AppIconToggleButton(
                checked = toggle1,
                onCheckedChange = { toggle1 = it }
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = "Toggle")
            }

            // 6. Filled Icon Toggle Button
            AppFilledIconToggleButton(
                checked = toggle2,
                onCheckedChange = { toggle2 = it }
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = "Filled Toggle")
            }

            // 7. Filled Tonal Icon Toggle Button
            AppFilledTonalIconToggleButton(
                checked = toggle3,
                onCheckedChange = { toggle3 = it }
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = "Tonal Toggle")
            }

            // 8. Outlined Icon Toggle Button
            AppOutlinedIconToggleButton(
                checked = toggle4,
                onCheckedChange = { toggle4 = it }
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = "Outlined Toggle")
            }
        }
    }
}
