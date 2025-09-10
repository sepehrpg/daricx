package com.example.designsystem.component.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = AppButtonDefaults.outlinedColors(),
    elevation: ButtonElevation? = null, // Outlined usually has no elevation
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    border: BorderStroke = AppButtonDefaults.outlinedBorder(enabled),
    shape: Shape = AppButtonDefaults.Shape,
    content: @Composable () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = contentPadding ,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        content = { AppButtonContent(text = content) }
    )
}

@Composable
fun AppOutlinedButtonWithIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = AppButtonDefaults.outlinedColors(),
    border: BorderStroke = AppButtonDefaults.outlinedBorder(enabled),
    shape: Shape = AppButtonDefaults.Shape,
    centerText: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        border = border,
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
@Composable private fun AppOutlinedButtonPreview() {
   AppThemedPreview {
      Column {
          AppOutlinedButtonWithIcon(
              onClick = {},
              modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
              leadingIcon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = null) },
          ) {
              Text("Likes")
          }

          Spacer(Modifier.height(12.dp))

          AppOutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
              Text("Outlined", fontSize = 14.sp, fontWeight = FontWeight.Medium)
          }
      }
   }
}
