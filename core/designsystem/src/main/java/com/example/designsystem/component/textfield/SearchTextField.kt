package com.example.designsystem.component.textfield


import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppSearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: (@Composable () -> Unit)? = null,
    height: Dp = 48.dp,
    shape: RoundedCornerShape = RoundedCornerShape(20.dp),
    shadow: Dp = 2.dp,
    brush: Brush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surface
        )
    ),
    interactionSource: MutableInteractionSource? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    AppBasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = textStyle,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { /* handle search */ }),
        interactionSource = interactionSource,
        placeholder = placeholder,
        decoration = { inner ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .shadow(shadow, shape)
                    .clip(shape)
                    .background(brush)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.width(8.dp))
                Box(Modifier.weight(1f)) { inner() }
                if (value.isNotEmpty()) {
                    IconButton(onClick = onClear) {
                        Icon(Icons.Filled.Close, contentDescription = "Clear")
                    }
                }
            }
        }
    )
}

/* Preview */
@ThemePreviews
@Composable
private fun AppSearchTextFieldPreview() {
    AppThemedPreview {
        var text by remember { mutableStateOf("") }
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AppSearchTextField(
                value = text,
                onValueChange = { text = it },
                onClear = { text = "" },
                placeholder = { androidx.compose.material3.Text("Search") }
            )
        }
    }
}
