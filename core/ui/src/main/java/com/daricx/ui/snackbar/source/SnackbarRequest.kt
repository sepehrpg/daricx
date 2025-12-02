package com.daricx.ui.snackbar.source

import androidx.compose.material3.SnackbarDuration
import com.example.designsystem.component.snackbar.AppSnackbarType

data class SnackbarRequest(
    val message: String,
    val type: AppSnackbarType = AppSnackbarType.Default,
    val actionLabel: String? = null,
    val withDismissAction: Boolean = true,
    val duration: SnackbarDuration = SnackbarDuration.Short
)