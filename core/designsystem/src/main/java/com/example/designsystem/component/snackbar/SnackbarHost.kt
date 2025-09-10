package com.example.designsystem.component.snackbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import kotlinx.coroutines.launch


@Composable
fun AppSnackbarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    content: (@Composable (data: SnackbarData, type: AppSnackbarType, message: String) -> Unit)? = null
) {
    SnackbarHost(hostState = hostState, modifier = modifier) { data ->
        val (type, msg) = AppSnackbarMessageParser.parseType(data.visuals.message)
        if (content != null) {
            content(data, type, msg)
        } else {
            AppSnackbar(
                message = msg,
                type = type,
                actionLabel = data.visuals.actionLabel,
                onAction = { data.performAction() },
                withDismissAction = data.visuals.withDismissAction,
                onDismiss = { data.dismiss() }
            )
        }
    }
}

/** parse/pack type در message (prefix) */
internal object AppSnackbarMessageParser {
    private val regex =
        Regex("""^\s*\[(default|info|success|warning|error)]\s*\|\s*(.*)$""", RegexOption.IGNORE_CASE)

    fun parseType(raw: String): Pair<AppSnackbarType, String> {
        val m = regex.find(raw) ?: return AppSnackbarType.Default to raw
        val t = when (m.groupValues[1].lowercase()) {
            "info" -> AppSnackbarType.Info
            "success" -> AppSnackbarType.Success
            "warning" -> AppSnackbarType.Warning
            "error" -> AppSnackbarType.Error
            else -> AppSnackbarType.Default
        }
        return t to m.groupValues[2]
    }

    fun wrapMessage(message: String, type: AppSnackbarType): String =
        "[${type.name.lowercase()}]| $message"
}


/**
 * FOR SHOW SNACK BAR RUN IT PREVIEW IN DEVICE
 */
@ThemePreviews
@Composable
private fun AppSnackbarHostPreviewSample(){
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    AppThemedPreview {
        Scaffold(
            snackbarHost = {

                // CUSTOM UI
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

                // DEFAULT UI
                /*AppSnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )*/
            }
        ) {
            Box(Modifier.fillMaxSize().padding(it)){
                Text("TEST SNACKBAR", modifier = Modifier.clickable {
                    scope.launch {
                        snackbarHostState.showSuccess("SUCCESS")
                    }
                })
            }
        }
        LaunchedEffect(Unit) {
            snackbarHostState.showSuccess("Order placed", action = "Details")
        }
    }
}