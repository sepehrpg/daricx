package com.example.designsystem.component.textfield


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppTextArea(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    minLines: Int = 3,
    maxLines: Int = 6,
    supportingText: (@Composable () -> Unit)? = null,
    textStyle: TextStyle = AppTextFieldDefaults.TextStyle
) {
    AppOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = { Text("Write something…") },
        supportingText = supportingText,
        singleLine = false,
        minLines = minLines,
        maxLines = maxLines,
        textStyle = textStyle
    )
}

/* Preview */
@ThemePreviews
@Composable
private fun AppTextAreaPreview() {
    AppThemedPreview {
        var text by remember { mutableStateOf("") }
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AppTextArea(
                value = text,
                onValueChange = { text = it },
                supportingText = { Text("${text.length}/200") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
