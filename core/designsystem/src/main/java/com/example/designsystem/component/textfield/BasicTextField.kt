package com.example.designsystem.component.textfield


import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

@Composable
fun AppBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(Color.Black),
    placeholder: @Composable (() -> Unit)? = null,
    decoration: @Composable (inner: @Composable () -> Unit) -> Unit = { it() }
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource ?: remember { MutableInteractionSource() },
        cursorBrush = cursorBrush,
        decorationBox = { inner ->
            decoration {
                Box {
                    if (value.isEmpty()) placeholder?.invoke()
                    inner()
                }
            }
        }
    )
}

/* Preview */
@ThemePreviews
@Composable
private fun AppBasicTextFieldPreview() {
    AppThemedPreview {
        var text by remember { mutableStateOf("") }
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AppBasicTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = { Text("Basic placeholder") },
                decoration = { inner ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(AppTextFieldDefaults.SingleLineHeightDp)
                            .padding(horizontal = 12.dp),
                        contentAlignment = Alignment.CenterStart
                    ) { inner() }
                }
            )
        }
    }
}
