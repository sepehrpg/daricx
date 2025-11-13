package com.example.designsystem.component.chips


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@ThemePreviews
@Composable
private fun AppChipsGalleryPreview() {
    AppThemedPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Assist
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppAssistChip(
                    onClick = {},
                    label = { Text("Assist") },
                    leadingIcon = { Icon(Icons.Filled.Settings, null) }
                )
                AppElevatedAssistChip(
                    onClick = {},
                    label = { Text("Assist (elevated)") },
                    leadingIcon = { Icon(Icons.Filled.Settings, null) }
                )
            }

            // Suggestion
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppSuggestionChip(
                    onClick = {},
                    label = { Text("Suggestion") },
                    icon = { Icon(Icons.Filled.Settings, null) }
                )
                AppElevatedSuggestionChip(
                    onClick = {},
                    label = { Text("Suggestion (elevated)") },
                    icon = { Icon(Icons.Filled.Settings, null) }
                )
            }

            // Filter (external state)
            var sel1 by remember { mutableStateOf(false) }
            var sel2 by remember { mutableStateOf(true) }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AppFilterChip(
                    selected = sel1,
                    onClick = { sel1 = !sel1 },
                    label = { Text("Filter") },
                    leadingIcon = if (sel1) { { Icon(Icons.Filled.Done, null) } } else null
                )
                AppElevatedFilterChip(
                    selected = sel2,
                    onClick = { sel2 = !sel2 },
                    label = { Text("Filter (elevated)") },
                    leadingIcon = if (sel2) { { Icon(Icons.Filled.Done, null) } } else null
                )
            }

            // Input (selected + dismiss affordance)
            var chipOn by remember { mutableStateOf(true) }
            if (chipOn) {
                AppInputChip(
                    selected = true,
                    onClick = { chipOn = false }, // treat click as dismiss
                    label = { Text("Alice") },
                    avatar = { Icon(Icons.Filled.Person, null) },
                    trailingIcon = { Icon(Icons.Filled.Close, null) }
                )
            }

            Text(
                "Tip: hold chip state externally (ViewModel) in lists.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
