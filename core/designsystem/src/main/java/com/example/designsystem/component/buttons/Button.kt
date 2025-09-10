package com.example.designsystem.component.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = AppButtonDefaults.filledColors(),
    elevation: ButtonElevation? = AppButtonDefaults.filledElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    shape: Shape = AppButtonDefaults.Shape,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding ,
        shape = shape,
        content = { AppButtonContent(text = content) }
    )
}

@Composable
fun AppButtonWithIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = AppButtonDefaults.filledColors(),
    elevation: ButtonElevation? = AppButtonDefaults.filledElevation(),
    border: BorderStroke? = null,
    shape: Shape = AppButtonDefaults.Shape,
    centerText: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        elevation = elevation,
        border = border,
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
@Composable private fun AppButtonPreview() {
    AppThemedPreview {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Column {
                AppButton(onClick = {}, modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),) {
                    Text("AppButton", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(Modifier.height(12.dp))
                AppButtonWithIcon(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                    leadingIcon = { Icon(Icons.Rounded.Call, contentDescription = null) },
                    content = { Text("Call", fontSize = 14.sp, fontWeight = FontWeight.Medium) }
                )
            }
        }
    }
}
