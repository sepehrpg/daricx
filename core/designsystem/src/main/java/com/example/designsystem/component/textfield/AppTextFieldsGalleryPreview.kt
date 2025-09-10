package com.example.designsystem.component.textfield


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@ThemePreviews
@Composable
private fun AppTextFieldsGalleryPreview() {
    AppThemedPreview {
        var v1 by remember { mutableStateOf("") } // filled
        var v2 by remember { mutableStateOf("") } // outlined
        var v3 by remember { mutableStateOf("") } // search
        var v4 by remember { mutableStateOf("") } // password
        var visible by remember { mutableStateOf(false) } // password toggle
        var v5 by remember { mutableStateOf("") } // textarea
        var v6 by remember { mutableStateOf("") } // basic

        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            Text("Filled")
            AppTextField(
                value = v1,
                onValueChange = { if (it.length <= 50) v1 = it },
                label = { Text("Email") },
                placeholder = { Text("you@domain.com") },
                singleLine = true,
                supportingText = { Text("${v1.length}/50") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Outlined")
            AppOutlinedTextField(
                value = v2,
                onValueChange = { v2 = it },
                label = { Text("Search") },
                placeholder = { Text("Type a query...") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Search (Basic + Decoration)")
            AppSearchTextField(
                value = v3,
                onValueChange = { v3 = it },
                onClear = { v3 = "" },
                placeholder = { Text("Search") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Password (Outlined)")
            AppPasswordTextField(
                value = v4,
                onValueChange = { v4 = it },
                passwordVisible = visible,
                onPasswordVisibilityChange = { visible = it },
                modifier = Modifier.fillMaxWidth()
            )

            Text("TextArea (Outlined, multiline)")
            AppTextArea(
                value = v5,
                onValueChange = { v5 = it },
                modifier = Modifier.fillMaxWidth()
            )

            Text("BasicTextField")
            AppBasicTextField(
                value = v6,
                onValueChange = { v6 = it },
                placeholder = { Text("Placeholder…") },
                decoration = { inner ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(AppTextFieldDefaults.SingleLineHeightDp)
                            .padding(horizontal = 12.dp),
                    ) { inner() }
                }
            )
        }
    }
}
