package com.example.designsystem.component.snackbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews


@Composable
fun AppSnackbar(
    message: String,
    modifier: Modifier = Modifier,
    type: AppSnackbarType = AppSnackbarType.Default,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    withDismissAction: Boolean = false,
    onDismiss: (() -> Unit)? = null,
    shape: Shape = AppSnackbarDefaults.Shape,
    colors: AppSnackbarColors = AppSnackbarDefaults.colors(type),
    leadingIcon: (@Composable () -> Unit)? = null,
) {
    AppCustomSnackbar(
        modifier = modifier,
        type = type,
        shape = shape,
        colors = colors,
        action = {
            if (actionLabel != null && onAction != null) {
                TextButton(onClick = onAction) { Text(actionLabel) }
            }
        },
        dismissAction = if (withDismissAction && onDismiss != null) {
            { IconButton(onClick = onDismiss) { Text("✕") } }
        } else null
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val iconTint = colors.iconTint
            if (leadingIcon != null) {
                CompositionLocalProvider(LocalContentColor provides iconTint) { leadingIcon() }
                Spacer(Modifier.width(8.dp))
            } else if (type != AppSnackbarType.Default) {
                CompositionLocalProvider(LocalContentColor provides iconTint) { AppSnackbarDefaults.LeadingIcon(type) }
                Spacer(Modifier.width(8.dp))
            }
            Text(message, style = MaterialTheme.typography.bodyMedium)
        }
    }
}



@ThemePreviews
@Composable
private fun AppSnackbarGalleryPreview() {
    AppThemedPreview {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AppSnackbar("Default snackbar")
            AppSnackbar("Info snackbar", type = AppSnackbarType.Info)
            AppSnackbar("Success snackbar", type = AppSnackbarType.Success, actionLabel = "Undo") {}
            AppSnackbar("Warning snackbar", type = AppSnackbarType.Warning)
            AppSnackbar("Error snackbar", type = AppSnackbarType.Error, withDismissAction = true, onDismiss = {})
        }
    }
}

