package com.daricx.ui.snackbar.source

import com.example.designsystem.component.snackbar.AppSnackbarType
import kotlinx.coroutines.flow.SharedFlow

interface SnackbarController {
    val requests: SharedFlow<SnackbarRequest>

    fun show(request: SnackbarRequest)

    fun showInfo(message: String, action: String? = null) =
        show(SnackbarRequest(message, AppSnackbarType.Info, action))
    fun showSuccess(message: String, action: String? = null) =
        show(SnackbarRequest(message, AppSnackbarType.Success, action))
    fun showWarning(message: String, action: String? = null) =
        show(SnackbarRequest(message, AppSnackbarType.Warning, action))
    fun showError(message: String, action: String? = null) =
        show(SnackbarRequest(message, AppSnackbarType.Error, action))
}