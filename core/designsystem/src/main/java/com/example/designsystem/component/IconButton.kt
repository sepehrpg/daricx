package com.example.designsystem.component
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilledTonalIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.ThemePreviews


// 1. Regular Icon Button
@Composable
fun AppIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    brush: Brush = Brush.horizontalGradient(listOf(Color.Transparent)),
    boxModifier:Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = boxModifier
    ){
        IconButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = colors,
            interactionSource = interactionSource,
            content = content
        )
    }

}


// 2. Filled Icon Button
@Composable
fun AppFilledIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.filledShape,
    colors: IconButtonColors = IconButtonDefaults.filledIconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    boxModifier:Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = boxModifier
    ){
        FilledIconButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            colors = colors,
            interactionSource = interactionSource,
            content = content
        )
    }

}


// 3. Filled Tonal Icon Button
@Composable
fun AppFilledTonalIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.filledShape,
    colors: IconButtonColors = IconButtonDefaults.filledTonalIconButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    boxModifier:Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = boxModifier
    ){
        FilledTonalIconButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            colors = colors,
            interactionSource = interactionSource,
            content = content
        )
    }

}



// 4. Outlined Icon Button
@Composable
fun AppOutlinedIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconButtonColors = IconButtonDefaults.outlinedIconButtonColors(),
    border: BorderStroke? = IconButtonDefaults.outlinedIconButtonBorder(enabled),
    shape: Shape = IconButtonDefaults.outlinedShape,
    interactionSource: MutableInteractionSource? = null,
    boxModifier:Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = boxModifier
    ){
        OutlinedIconButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            colors = colors,
            border = border,
            shape = shape ,
            interactionSource = interactionSource,
            content = content
        )
    }

}


// 5. Regular Icon Toggle Button
@Composable
fun AppIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: IconToggleButtonColors = IconButtonDefaults.iconToggleButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    boxModifier:Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = boxModifier
    ){
        IconToggleButton(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier,
            enabled = enabled,
            colors = colors,
            interactionSource = interactionSource,
            content = content
        )
    }

}


// 6. Filled Icon Toggle Button
@Composable
fun AppFilledIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.filledShape,
    colors: IconToggleButtonColors = IconButtonDefaults.filledIconToggleButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    boxModifier:Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = boxModifier
    ){
        FilledIconToggleButton(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            colors = colors,
            interactionSource = interactionSource,
            content = content
        )
    }

}


// 7. Filled Tonal Icon Toggle Button
@Composable
fun AppFilledTonalIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.filledShape,
    colors: IconToggleButtonColors = IconButtonDefaults.filledTonalIconToggleButtonColors(),
    interactionSource: MutableInteractionSource? = null,
    boxModifier:Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = boxModifier
    ){
        FilledTonalIconToggleButton(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier,
            enabled = enabled,
            shape = shape,
            colors = colors,
            interactionSource = interactionSource,
            content = content
        )
    }

}

// 8. Outlined Icon Toggle Button
@Composable
fun AppOutlinedIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = IconButtonDefaults.outlinedShape,
    colors: IconToggleButtonColors = IconButtonDefaults.outlinedIconToggleButtonColors(),
    border: BorderStroke? = IconButtonDefaults.outlinedIconToggleButtonBorder(enabled, checked),
    interactionSource: MutableInteractionSource? = null,
    boxModifier:Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = boxModifier
    ){
        OutlinedIconToggleButton(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier,
            enabled = enabled,
            colors = colors,
            shape = shape,
            border = border,
            interactionSource = interactionSource,
            content = content
        )
    }

}




@ThemePreviews
@Composable
private fun AppIconButtonsPreview() {
    // For toggle buttons, define some state variables.
    val toggleState1 = remember { mutableStateOf(false) }
    val toggleState2 = remember { mutableStateOf(true) }
    val toggleState3 = remember { mutableStateOf(false) }
    val toggleState4 = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        AppFilledTonalIconButton(
            onClick = { /* Handle click */ },
            modifier = Modifier.size(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = IconButtonDefaults.filledTonalIconButtonColors(
                containerColor = Color.Transparent,
                contentColor = Color.DarkGray
            ),
            boxModifier = Modifier.shadow(2.dp,RoundedCornerShape(12.dp)).background(Brush.horizontalGradient(listOf(Color(0xFFEEEEEE),Color.White)),RoundedCornerShape(12.dp)),
        ) {
            AppIcon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings"
            )
        }


        // 1. Regular Icon Button
        AppIconButton(
            onClick = { /* Handle click */ }
        ) {
            AppIcon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Home"
            )
        }
        // 2. Filled Icon Button
        AppFilledIconButton(
            onClick = { /* Handle click */ },
            shape = RoundedCornerShape(7.dp)
        ) {
            AppIcon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite"
            )
        }
        // 3. Filled Tonal Icon Button
        AppFilledTonalIconButton(
            onClick = { /* Handle click */ }
        ) {
            AppIcon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings"
            )
        }
        // 4. Outlined Icon Button
        AppOutlinedIconButton(
            onClick = { /* Handle click */ }
        ) {
            AppIcon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Outlined Home"
            )
        }
        // 5. Regular Icon Toggle Button
        AppIconToggleButton(
            checked = toggleState1.value,
            onCheckedChange = { toggleState1.value = it }
        ) {
            AppIcon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Icon Toggle"
            )
        }
        // 6. Filled Icon Toggle Button
        AppFilledIconToggleButton(
            checked = toggleState2.value,
            onCheckedChange = { toggleState2.value = it }
        ) {
            AppIcon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Filled Icon Toggle"
            )
        }
        // 7. Filled Tonal Icon Toggle Button
        AppFilledTonalIconToggleButton(
            checked = toggleState3.value,
            onCheckedChange = { toggleState3.value = it }
        ) {
            AppIcon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Filled Tonal Icon Toggle"
            )
        }
        // 8. Outlined Icon Toggle Button
        AppOutlinedIconToggleButton(
            checked = toggleState4.value,
            onCheckedChange = { toggleState4.value = it }
        ) {
            AppIcon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Outlined Icon Toggle"
            )
        }
    }
}


