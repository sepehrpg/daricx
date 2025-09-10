package com.example.designsystem.component.buttons


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppElevatedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: ButtonElevation? = AppButtonDefaults.elevatedElevation(),
    colors: ButtonColors = AppButtonDefaults.elevatedColors(),
    shape: Shape = AppButtonDefaults.Shape,
    content: @Composable () -> Unit,
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        colors = colors,
        shape = shape,
        content = { AppButtonContent(text = content) }
    )
}

@Composable
fun AppElevatedButtonWithIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: ButtonElevation? = AppButtonDefaults.elevatedElevation(),
    colors: ButtonColors = AppButtonDefaults.elevatedColors(),
    shape: Shape = AppButtonDefaults.Shape,
    centerText: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        colors = colors,
        shape = shape,
        content = {
            AppButtonContent(
                text = content,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                centerText = centerText
            )
        }
    )
}


@ThemePreviews
@Composable private fun AppElevatedButtonPreview() {
    AppThemedPreview {
       Column {
           AppElevatedButtonWithIcon(
               onClick = {},
               modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
               leadingIcon = { Icon(Icons.Filled.Add, contentDescription = null) }
           ) {
               Text("Add")
           }
           Spacer(Modifier.height(12.dp))
           AppElevatedButton(onClick = {}, modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
               Text("Elevated", fontSize = 14.sp, fontWeight = FontWeight.Medium)
           }
       }
    }
}
