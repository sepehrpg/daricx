package com.example.designsystem.component.cards


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@ThemePreviews
@Composable
private fun AppCardsGalleryPreview() {
    AppThemedPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Card
            AppCard(
                modifier = Modifier.fillMaxWidth()
            ) { Text("Card", Modifier.padding(16.dp), style = MaterialTheme.typography.bodyMedium) }

            AppCard(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) { Text("Card (clickable)", Modifier.padding(16.dp), style = MaterialTheme.typography.bodyMedium) }

            // ElevatedCard
            AppElevatedCard(
                modifier = Modifier.fillMaxWidth()
            ) { Text("ElevatedCard", Modifier.padding(16.dp), style = MaterialTheme.typography.bodyMedium) }

            AppElevatedCard(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) { Text("ElevatedCard (clickable)", Modifier.padding(16.dp), style = MaterialTheme.typography.bodyMedium) }

            // OutlinedCard
            AppOutlinedCard(
                modifier = Modifier.fillMaxWidth()
            ) { Text("OutlinedCard", Modifier.padding(16.dp), style = MaterialTheme.typography.bodyMedium) }

            AppOutlinedCard(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) { Text("OutlinedCard (clickable)", Modifier.padding(16.dp), style = MaterialTheme.typography.bodyMedium) }
        }
    }
}
