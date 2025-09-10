package com.example.designsystem.component.buttons


import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors

/**
 * Centralized defaults/tokens for DARICX buttons.
 */
object AppButtonDefaults {
    // Shapes
    val Shape: Shape = RoundedCornerShape(12.dp)

    // Borders
    const val DisabledOutlinedBorderAlpha: Float = 0.12f
    val OutlinedBorderWidth: Dp = 1.dp

    // Elevations
    @Composable
    fun filledElevation(): ButtonElevation = ButtonDefaults.buttonElevation()
    @Composable
    fun elevatedElevation(): ButtonElevation = ButtonDefaults.elevatedButtonElevation()

    // Colors
    @Composable
    fun filledColors(
        container: Color = MaterialTheme.colorScheme.primary,
        content: Color = MaterialTheme.colorScheme.onPrimary
    ): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = container,
        contentColor = content,
        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    )

    @Composable
    fun outlinedColors(
        content: Color = MaterialTheme.colorScheme.onSurface
    ): ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = content,
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    )

    @Composable
    fun textColors(
        content: Color = MaterialTheme.colorScheme.primary
    ): ButtonColors = ButtonDefaults.textButtonColors(
        contentColor = content,
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    )

    @Composable
    fun elevatedColors(
        container: Color = MaterialTheme.colorScheme.surface,
        content: Color = MaterialTheme.colorScheme.primary
    ): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = container,
        contentColor = content,
        disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
        disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    )

    @Composable
    fun outlinedBorder(enabled: Boolean = true): BorderStroke = BorderStroke(
        width = OutlinedBorderWidth,
        color = if (enabled) {
            // outlineVariant if available; fall back to outline
            MaterialTheme.colorScheme.outlineVariant ?: MaterialTheme.colorScheme.outline
        } else {
            MaterialTheme.colorScheme.onSurface.copy(alpha = DisabledOutlinedBorderAlpha)
        }
    )
}

/** Small helper to safely read optional colorScheme.outlineVariant across versions. */
private val androidx.compose.material3.ColorScheme.outlineVariant: Color?
    get() = try { this.javaClass.getDeclaredField("outlineVariant").get(this) as? Color } catch (_: Throwable) { null }
