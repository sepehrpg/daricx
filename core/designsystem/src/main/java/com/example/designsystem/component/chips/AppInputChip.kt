package com.example.designsystem.component.chips
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
@Composable
fun AppInputChip(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    avatar: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null, // e.g. close (X)
    shape: Shape = AppChipDefaults.inputShape,
    colors: SelectableChipColors = AppChipDefaults.inputColors(),
    elevation: SelectableChipElevation? = AppChipDefaults.inputElevation(),
    border: BorderStroke? = AppChipDefaults.inputBorder(enabled, selected),
    interactionSource: androidx.compose.foundation.interaction.MutableInteractionSource? = null,
) {
    InputChip(
        selected = selected,
        onClick = onClick,
        label = label,
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        avatar = avatar,
        trailingIcon = trailingIcon,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource
    )
}