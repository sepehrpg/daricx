package com.example.designsystem.component.textfield


import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

/**
 * Centralized defaults/tokens for DARICX TextFields.
 */
object AppTextFieldDefaults {
    // Shape
    val Shape: Shape = RoundedCornerShape(12.dp)

    // Text style
    val TextStyle: TextStyle
        @Composable get() = LocalTextStyle.current

    // Colors (Filled / Outlined)
    @Composable
    fun filledColors(): TextFieldColors = TextFieldDefaults.colors()

    @Composable
    fun outlinedColors(): TextFieldColors = TextFieldDefaults.colors()

    // Helper: default single-line height hint
    val SingleLineHeightDp = 56.dp
}
