package com.daricx.ui.snackbar


import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackbarController = staticCompositionLocalOf<SnackbarController> {
    error("SnackbarController not provided")
}