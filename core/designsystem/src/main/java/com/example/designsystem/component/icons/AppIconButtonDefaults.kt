package com.example.designsystem.component.icons


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Centralized defaults/tokens for DARICX IconButtons.
 */
object AppIconButtonDefaults {

    // Shape
    val Shape: Shape = RoundedCornerShape(12.dp)

    // Non-toggle colors
    @Composable
    fun iconColors(): IconButtonColors = IconButtonDefaults.iconButtonColors(
        contentColor = MaterialTheme.colorScheme.surfaceTint,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    )

    @Composable
    fun filledColors(): IconButtonColors = IconButtonDefaults.filledIconButtonColors(
        contentColor = MaterialTheme.colorScheme.surfaceTint,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    )

    @Composable
    fun tonalColors(): IconButtonColors = IconButtonDefaults.filledTonalIconButtonColors(
        contentColor = MaterialTheme.colorScheme.surfaceTint,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    )

    @Composable
    fun outlinedColors(): IconButtonColors = IconButtonDefaults.outlinedIconButtonColors(
        contentColor = MaterialTheme.colorScheme.surfaceTint,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    )

    // Toggle colors
    @Composable
    fun toggleColors(): IconToggleButtonColors = IconButtonDefaults.iconToggleButtonColors(
        contentColor = MaterialTheme.colorScheme.surfaceTint,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    )

    @Composable
    fun filledToggleColors(): IconToggleButtonColors =
        IconButtonDefaults.filledIconToggleButtonColors(
            contentColor = MaterialTheme.colorScheme.surfaceTint,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )

    @Composable
    fun tonalToggleColors(): IconToggleButtonColors =
        IconButtonDefaults.filledTonalIconToggleButtonColors(
            contentColor = MaterialTheme.colorScheme.surfaceTint,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )

    @Composable
    fun outlinedToggleColors(): IconToggleButtonColors =
        IconButtonDefaults.outlinedIconToggleButtonColors(
            contentColor = MaterialTheme.colorScheme.surfaceTint,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        )
}
