package com.example.designsystem.component.snackbar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import com.example.designsystem.icon.AppIcons.CheckCircleIcon
import com.example.designsystem.icon.AppIcons.ErrorIcon
import com.example.designsystem.icon.AppIcons.InfoIcon
import com.example.designsystem.icon.AppIcons.WarningIcon

data class AppSnackbarColors(
    val container: Color,
    val content: Color,
    val action: Color,
    val dismiss: Color = content,
    val iconTint: Color = content
)

object AppSnackbarDefaults {
    val Shape: Shape = RoundedCornerShape(12.dp)

    @Composable
    fun colors(type: AppSnackbarType): AppSnackbarColors {
        val cs = MaterialTheme.colorScheme
        return when (type) {
            AppSnackbarType.Error -> AppSnackbarColors(
                container = cs.errorContainer, content = cs.onErrorContainer, action = cs.onErrorContainer
            )
            AppSnackbarType.Success -> AppSnackbarColors(
                container = cs.secondaryContainer, content = cs.onSecondaryContainer, action = cs.onSecondaryContainer
            )
            AppSnackbarType.Info -> AppSnackbarColors(
                container = cs.primaryContainer, content = cs.onPrimaryContainer, action = cs.onPrimaryContainer
            )
            AppSnackbarType.Warning -> AppSnackbarColors(
                container = cs.tertiaryContainer, content = cs.onTertiaryContainer, action = cs.onTertiaryContainer
            )
            AppSnackbarType.Default -> AppSnackbarColors(
                container = cs.surface, content = cs.onSurface, action = cs.primary
            )
        }
    }

    @Composable
    fun LeadingIcon(type: AppSnackbarType) {
        when (type) {
            AppSnackbarType.Error -> Icon(ErrorIcon, contentDescription = null)
            AppSnackbarType.Success -> Icon(CheckCircleIcon, contentDescription = null)
            AppSnackbarType.Info -> Icon(InfoIcon, contentDescription = null)
            AppSnackbarType.Warning -> Icon(WarningIcon, contentDescription = null)
            AppSnackbarType.Default -> {}
        }
    }
}
