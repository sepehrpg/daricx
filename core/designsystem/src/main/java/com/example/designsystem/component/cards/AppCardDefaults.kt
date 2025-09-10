package com.example.designsystem.component.cards


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Centralized defaults/tokens for DARICX Cards.
 */
object AppCardDefaults {
    // Shape
    val Shape: Shape = RoundedCornerShape(12.dp)

    // Colors
    @Composable
    fun colors(): CardColors = CardDefaults.cardColors()

    @Composable
    fun elevatedColors(): CardColors = CardDefaults.elevatedCardColors()

    @Composable
    fun outlinedColors(): CardColors = CardDefaults.outlinedCardColors()

    // Elevations
    @Composable
    fun elevation(): CardElevation = CardDefaults.cardElevation()

    @Composable
    fun elevatedElevation(): CardElevation = CardDefaults.elevatedCardElevation()

    @Composable
    fun outlinedElevation(): CardElevation = CardDefaults.outlinedCardElevation()

    // Borders (outlined)
    @Composable
    fun outlinedBorder(): BorderStroke = CardDefaults.outlinedCardBorder()
}
