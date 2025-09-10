package com.example.designsystem.component.buttons


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@ThemePreviews
@Composable
private fun ButtonsGalleryPreview() {
    AppThemedPreview {
        Column(Modifier.padding(horizontal = 15.dp)) {
            AppButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Filled") }
            Spacer(Modifier.height(8.dp))
            AppButtonWithIcon(
                onClick = {}, modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Rounded.Call, null) }
            ) { Text("Filled + Icon") }
            Spacer(Modifier.height(8.dp))
            AppOutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Outlined") }
            Spacer(Modifier.height(8.dp))
            AppOutlinedButtonWithIcon(
                onClick = {}, modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Rounded.Call, null) }
            ) { Text("Outlined + Icon") }
            Spacer(Modifier.height(8.dp))
            AppTextButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Text") }
            Spacer(Modifier.height(8.dp))
            AppTextButtonWithIcon(
                onClick = {}, modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Rounded.Call, null) }
            ) { Text("Text + Icon") }
            Spacer(Modifier.height(8.dp))
            AppElevatedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) { Text("Elevated") }
            Spacer(Modifier.height(8.dp))
            AppElevatedButtonWithIcon(
                onClick = {}, modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Rounded.Call, null) }
            ) { Text("Elevated + Icon") }
            Spacer(Modifier.height(8.dp))
            AppDeleteButton(onClick = {}, modifier = Modifier.fillMaxWidth())
        }
   }
}




