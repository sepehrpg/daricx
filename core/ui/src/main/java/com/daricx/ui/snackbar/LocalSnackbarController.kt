package com.daricx.ui.snackbar


import androidx.compose.runtime.staticCompositionLocalOf
import com.daricx.ui.snackbar.source.SnackbarController

val LocalSnackbarController = staticCompositionLocalOf<SnackbarController> {
    error("SnackbarController not provided")
}