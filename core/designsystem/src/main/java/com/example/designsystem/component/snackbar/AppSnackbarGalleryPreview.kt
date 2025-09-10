package com.example.designsystem.component.snackbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import kotlinx.coroutines.delay

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


@ThemePreviews
@Composable
private fun AppSnackbarHost_DefaultPreview() {
    AppThemedPreview {
        val fakeData = object : SnackbarData {
            override val visuals = object : SnackbarVisuals {
                override val message = "[info]| Loading markets…"
                override val actionLabel: String? = "Undo"
                override val withDismissAction: Boolean = true
                override val duration = SnackbarDuration.Short
            }
            override fun performAction() {}
            override fun dismiss() {}
        }

        Box() {
            AppSnackbar(
                message = "Loading markets…",
                type = AppSnackbarType.Info,
                actionLabel = "Undo"
            )
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
