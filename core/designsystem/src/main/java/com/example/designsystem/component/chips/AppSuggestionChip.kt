package com.example.designsystem.component.chips
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

@Composable
fun AppSuggestionChip(
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: (@Composable () -> Unit)? = null,
    shape: Shape = AppChipDefaults.suggestionShape,
    colors: ChipColors = AppChipDefaults.suggestionColors(),
    elevation: ChipElevation? = AppChipDefaults.suggestionElevation(),
    border: BorderStroke? = AppChipDefaults.suggestionBorder(enabled),
    interactionSource: androidx.compose.foundation.interaction.MutableInteractionSource? = null,
) {
    SuggestionChip(
        onClick = onClick,
        label = label,
        modifier = modifier,
        enabled = enabled,
        icon = icon,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    )
}