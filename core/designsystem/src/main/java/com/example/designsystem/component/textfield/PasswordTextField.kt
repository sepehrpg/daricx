package com.example.designsystem.component.textfield


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    label: (@Composable () -> Unit)? = null,
    placeholder: (@Composable () -> Unit)? = null,
    isError: Boolean = false,
    singleLine: Boolean = true,
    textStyle: TextStyle = AppTextFieldDefaults.TextStyle,
    shape: Shape = AppTextFieldDefaults.Shape,
    colors: TextFieldColors = AppTextFieldDefaults.outlinedColors()
) {
    AppOutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        isError = isError,
        singleLine = singleLine,
        textStyle = textStyle,
        shape = shape,
        colors = colors,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { onPasswordVisibilityChange(!passwordVisible) }) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        }
    )
}

/* Preview */
@ThemePreviews
@Composable
private fun AppPasswordTextFieldPreview() {
    AppThemedPreview {
        var text by remember { mutableStateOf("") }
        var visible by remember { mutableStateOf(false) }

        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AppPasswordTextField(
                value = text,
                onValueChange = { text = it },
                passwordVisible = visible,
                onPasswordVisibilityChange = { visible = it },
                label = { Text("Password") },
                placeholder = { Text("••••••••") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
