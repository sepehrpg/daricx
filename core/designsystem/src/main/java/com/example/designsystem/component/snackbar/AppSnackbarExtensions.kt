package com.example.designsystem.component.snackbar

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult

suspend fun SnackbarHostState.showAppSnackbar(
    message: String,
    type: AppSnackbarType = AppSnackbarType.Default,
    actionLabel: String? = null,
    withDismissAction: Boolean = true,
    duration: SnackbarDuration = SnackbarDuration.Short
): SnackbarResult {
    val wrapped = AppSnackbarMessageParser.wrapMessage(message, type)
    return showSnackbar(
        message = wrapped,
        actionLabel = actionLabel,
        withDismissAction = withDismissAction,
        duration = duration
    )
}

suspend fun SnackbarHostState.showInfo(message: String, action: String? = null) =
    showAppSnackbar(message, AppSnackbarType.Info, action)

suspend fun SnackbarHostState.showSuccess(message: String, action: String? = null) =
    showAppSnackbar(message, AppSnackbarType.Success, action)

suspend fun SnackbarHostState.showWarning(message: String, action: String? = null) =
    showAppSnackbar(message, AppSnackbarType.Warning, action)

suspend fun SnackbarHostState.showError(message: String, action: String? = null) =
    showAppSnackbar(message, AppSnackbarType.Error, action)
