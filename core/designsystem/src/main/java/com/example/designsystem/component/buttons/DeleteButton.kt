package com.example.designsystem.component.buttons


import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

/**
 * A 'destructive' action. Outlined style with error color accents.
 */
@Composable
fun AppDeleteButton(
    text: String = "Delete",
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val error = MaterialTheme.colorScheme.error
    val onError = MaterialTheme.colorScheme.onError
    val border = BorderStroke(AppButtonDefaults.OutlinedBorderWidth, error)

    AppOutlinedButtonWithIcon(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = error,
            disabledContentColor = error.copy(alpha = 0.38f),
        ),
        border = border,
        leadingIcon = { Icon(Icons.Rounded.Delete, contentDescription = null, tint = error) },
        content = { Text(text, fontSize = 14.sp, fontWeight = FontWeight.Medium) }
    )
}

@ThemePreviews
@Composable private fun AppDeleteButtonPreview() {
    AppThemedPreview{
        AppDeleteButton(onClick = {}, modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp))
    }
}
