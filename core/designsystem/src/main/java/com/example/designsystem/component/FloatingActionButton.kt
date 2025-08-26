package com.example.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.override.ExtendedFloatingActionButtonOverride
import com.example.designsystem.config.direction.AppDirection
import com.example.designsystem.config.direction.LayoutDirections
import com.example.designsystem.theme.ThemePreviews


// App Floating Action Button
//..................................................................................................
@Composable
fun AppFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: Shape = FloatingActionButtonDefaults.shape,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        shape = shape,
        elevation = elevation,
        interactionSource = interactionSource,
        content = content,
    )
}
//..................................................................................................


// App Extended Floating Action Button
//..................................................................................................
@Composable
fun AppExtendedFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: Shape = FloatingActionButtonDefaults.shape,
    expanded: Boolean = true,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
) {
    ExtendedFloatingActionButtonOverride(
        onClick = onClick,
        modifier = modifier,
        expanded = expanded,
        containerColor = containerColor,
        contentColor = contentColor,
        shape = shape,
        elevation = elevation,
        icon = icon,
        text = text,
    )
}
//..................................................................................................



@ThemePreviews
@Composable
private fun AppFloatingActionButtonPreview(){
    CompositionLocalProvider(AppDirection.appLayoutDirectionProvider(LayoutDirections.LTR)) {
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp)){
                Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
                    AppFloatingActionButton(
                        onClick = { /* Do something */ },
                        content = { AppIcon(Icons.Default.Add, contentDescription = null) }
                    )
                    AppExtendedFloatingActionButton(
                        onClick = { /* Do something */ },
                        icon = { AppIcon(Icons.Default.Check, contentDescription = null) },
                        text = { Text("Extended FAB") }
                    )
                }
            }
        }
    }

}