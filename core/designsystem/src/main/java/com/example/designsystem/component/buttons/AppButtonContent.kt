package com.example.designsystem.component.buttons


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Shared content layout for all DARICX buttons.
 * - If centerText = true, the label is perfectly centered with icons balanced left/right.
 * - Otherwise uses standard M3 spacing (icon + label).
 */
@Composable
internal fun AppButtonContent(
    text: @Composable () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    centerText: Boolean = false,
) {
    if (centerText) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(Modifier.size(ButtonDefaults.IconSize), contentAlignment = Alignment.CenterStart) {
                leadingIcon?.invoke()
            }
            Box(Modifier.padding(horizontal = 8.dp), contentAlignment = Alignment.Center) {
                text()
            }
            Box(Modifier.size(ButtonDefaults.IconSize), contentAlignment = Alignment.CenterEnd) {
                trailingIcon?.invoke()
            }
        }
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leadingIcon != null) {
                Box(Modifier.size(ButtonDefaults.IconSize)) { leadingIcon() }
            }
            Box(
                Modifier.padding(
                    start = if (leadingIcon != null) ButtonDefaults.IconSpacing else 0.dp,
                    end = if (trailingIcon != null) ButtonDefaults.IconSpacing else 0.dp,
                )
            ) { text() }
            if (trailingIcon != null) {
                Box(Modifier.size(ButtonDefaults.IconSize)) { trailingIcon() }
            }
        }
    }
}
