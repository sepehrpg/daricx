package com.daricx.markets.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.text.AppText
import timber.log.Timber

@Composable
fun LoadingBox(
    modifier: Modifier = Modifier,
    color : Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.primaryContainer
) = Box(
    Modifier.fillMaxSize()) {
    LinearProgressIndicator(
        modifier.fillMaxWidth(),
        color = color,
        trackColor = trackColor,
    )
}

@Composable
fun EmptyBox() = Box(Modifier.fillMaxSize()) {
    AppText(
        "No items",
        modifier = Modifier.align(Alignment.Center),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun ErrorBox(message: String, onRetry: () -> Unit) = Box(Modifier.fillMaxSize()) {
    Timber.tag("ERROBOX").i(message)
    Column(
        modifier = Modifier.align(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppText(text = message, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Button(onClick = onRetry, modifier = Modifier.padding(top = 8.dp)) { Text("Retry..") }
    }
}

@Composable
fun LoadingMore() = Box(
    Modifier
        .fillMaxSize()
        .padding(16.dp)
) { CircularProgressIndicator(Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary) }

@Composable
fun ErrorFooter(error: Throwable, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppText(
            text = error.message ?: "Append error",
            color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        Button(onClick = onRetry, modifier = Modifier.padding(top = 8.dp)) { Text("Retry.") }
    }
}