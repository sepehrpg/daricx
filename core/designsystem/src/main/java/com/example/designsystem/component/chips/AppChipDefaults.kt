package com.example.designsystem.component.chips


import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape

/**
 * Centralized defaults/tokens for your Chips.
 * Exposes sensible wrappers over Material defaults so you can override theme from one place.
 */
object AppChipDefaults {
    // ---- Assist ----
    val assistShape: Shape @Composable get() = AssistChipDefaults.shape
    @Composable fun assistColors(): ChipColors = AssistChipDefaults.assistChipColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        labelColor =  MaterialTheme.colorScheme.onSurfaceVariant,
    )
    @Composable fun assistElevation(): ChipElevation? = AssistChipDefaults.assistChipElevation()
    @Composable fun assistBorder(enabled: Boolean = true): BorderStroke? =
        AssistChipDefaults.assistChipBorder(enabled)

    // Elevated Assist
    @Composable fun elevatedAssistColors(): ChipColors = AssistChipDefaults.elevatedAssistChipColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        labelColor =  MaterialTheme.colorScheme.onSurfaceVariant,
    )
    @Composable fun elevatedAssistElevation(): ChipElevation? = AssistChipDefaults.elevatedAssistChipElevation()

    // ---- Suggestion ----
    val suggestionShape: Shape @Composable get() = SuggestionChipDefaults.shape
    @Composable fun suggestionColors(): ChipColors = SuggestionChipDefaults.suggestionChipColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        labelColor =  MaterialTheme.colorScheme.onSurfaceVariant,
    )
    @Composable fun suggestionElevation(): ChipElevation? = SuggestionChipDefaults.suggestionChipElevation()
    @Composable fun suggestionBorder(enabled: Boolean = true): BorderStroke? =
        SuggestionChipDefaults.suggestionChipBorder(enabled)

    // Elevated Suggestion
    @Composable fun elevatedSuggestionColors(): ChipColors =
        SuggestionChipDefaults.elevatedSuggestionChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor =  MaterialTheme.colorScheme.onSurfaceVariant,
        )
    @Composable fun elevatedSuggestionElevation(): ChipElevation? =
        SuggestionChipDefaults.elevatedSuggestionChipElevation()

    // ---- Filter (selectable) ----
    val filterShape: Shape @Composable get() = FilterChipDefaults.shape
    @Composable fun filterColors(): SelectableChipColors = FilterChipDefaults.filterChipColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        labelColor =  MaterialTheme.colorScheme.onSurfaceVariant,
    )
    @Composable fun filterElevation(): SelectableChipElevation? = FilterChipDefaults.filterChipElevation()
    @Composable fun filterBorder(enabled: Boolean = true, selected: Boolean = false): BorderStroke? =
        FilterChipDefaults.filterChipBorder(enabled, selected)

    // Elevated Filter
    @Composable fun elevatedFilterColors(): SelectableChipColors =
        FilterChipDefaults.elevatedFilterChipColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor =  MaterialTheme.colorScheme.onSurfaceVariant,
        )
    @Composable fun elevatedFilterElevation(): SelectableChipElevation? =
        FilterChipDefaults.elevatedFilterChipElevation()

    // ---- Input (selectable) ----
    val inputShape: Shape @Composable get() = InputChipDefaults.shape
    @Composable fun inputColors(): SelectableChipColors = InputChipDefaults.inputChipColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        labelColor =  MaterialTheme.colorScheme.onSurfaceVariant,
    )
    @Composable fun inputElevation(): SelectableChipElevation? = InputChipDefaults.inputChipElevation()
    @Composable fun inputBorder(enabled: Boolean = true, selected: Boolean = false): BorderStroke? =
        InputChipDefaults.inputChipBorder(enabled, selected)
}