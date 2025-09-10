package com.example.designsystem.component.snackbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews


/**
 * App snackbar container
 *
 * @param modifier
 * @param type
 * @param shape
 * @param colors
 * @param action
 * @param dismissAction
 * @param content
 * @receiver
 *
 * EXAMPLE USAGE :
 * /*
 * AppCustomSnackbar(type = AppSnackbarType.Success) {
 *     Row(verticalAlignment = Alignment.CenterVertically) {
 *         Icon(Icons.Rounded.CheckCircle, null)
 *         Spacer(Modifier.width(8.dp))
 *         Column(Modifier.weight(1f)) {
 *             Text("Watchlist updated", style = MaterialTheme.typography.bodyMedium)
 *             Text("BTC added", style = MaterialTheme.typography.labelSmall, color = LocalContentColor.current.copy(alpha = 0.7f))
 *         }
 *         TextButton(onClick = { /* undo */ }) { Text("Undo") }
 *     }
 * }
 * */
 */
@Composable
fun AppCustomSnackbar(
    modifier: Modifier = Modifier,
    type: AppSnackbarType = AppSnackbarType.Default,
    shape: Shape = AppSnackbarDefaults.Shape,
    colors: AppSnackbarColors = AppSnackbarDefaults.colors(type),
    action: (@Composable () -> Unit)? = null,
    dismissAction: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Snackbar(
        modifier = modifier,
        shape = shape,
        containerColor = colors.container,
        contentColor = colors.content,
        actionContentColor = colors.action,
        dismissActionContentColor = colors.dismiss,
        action = action,
        dismissAction = dismissAction
    ) {
        CompositionLocalProvider(LocalContentColor provides colors.content) {
            content()
        }
    }
}


@ThemePreviews
@Composable
private fun AppSnackbarHost_CustomUIPreview() {
    AppThemedPreview {
        AppCustomSnackbar(
            type = AppSnackbarType.Success,
            colors = AppSnackbarDefaults.colors(AppSnackbarType.Success),
            dismissAction = { IconButton(onClick = {}) { Text("✕") } },
            action = { TextButton(onClick = {}) { Text("Undo", fontWeight = FontWeight.SemiBold) } }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                CompositionLocalProvider(LocalContentColor provides AppSnackbarDefaults.colors(AppSnackbarType.Success).iconTint) {
                    Icon(Icons.Rounded.CheckCircle, contentDescription = null)
                }
                Spacer(Modifier.width(8.dp))
                Column(Modifier.weight(1f)) {
                    Text("BTC added to watchlist", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                    Text("Tap action to undo", style = MaterialTheme.typography.labelSmall, color = LocalContentColor.current.copy(alpha = 0.75f))
                }
            }
        }
    }
}
