package com.example.designsystem.component.buttons


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.TextButton
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = AppButtonDefaults.textColors(),
    content: @Composable () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        content = { AppButtonContent(text = content) }
    )
}

@Composable
fun AppTextButtonWithIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = AppButtonDefaults.textColors(),
    centerText: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
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
@Composable private fun AppTextButtonPreview() {
    AppThemedPreview {
      Column {
          AppTextButtonWithIcon(
              onClick = {},
              modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
              leadingIcon = { Icon(Icons.Filled.Check, contentDescription = null) }
          ) {
              Text("Accept")
          }

          Spacer(Modifier.height(12.dp))

          AppTextButton(onClick = {}, modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
              Text("Learn more", fontSize = 14.sp, fontWeight = FontWeight.Medium)
          }
      }
    }
}
