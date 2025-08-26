

package com.example.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.config.direction.AppDirection
import com.example.designsystem.config.direction.LayoutDirections
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.Blue20
import com.example.designsystem.theme.ClickUpGray4
import com.example.designsystem.theme.ClickUpPink1


//..................................................................................................
@Composable
fun AppDeleteButton(
    text:String = "Delete",
    onClick: () -> Unit
){
    AppElevatedButtonWithIcon(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation=0.dp, pressedElevation =2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = ClickUpGray4,
        ),
        shape = RoundedCornerShape(10.dp),
        content = {
            Row(Modifier.padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically){
                AppIcon(AppIcons.Delete, contentDescription = null, tint = ClickUpPink1)
                Spacer(Modifier.width(5.dp))
                AppText(text, fontSize = 14.sp, modifier = Modifier.padding(top = 3.dp), color = ClickUpPink1)
            }
        }
    )
}


@Preview(showBackground = true,showSystemUi = true, locale = "en")
@Composable
private fun AppButtonPreviewMain() {
    CompositionLocalProvider(AppDirection.appLayoutDirectionProvider(LayoutDirections.LTR)) {
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp)) {
                AppDeleteButton(){}
            }
        }
    }
}
//..................................................................................................





//App Button
//..................................................................................................
@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Blue20,
    ),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    shape: Shape = ButtonDefaults.shape,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
        colors = buttonColors,
        contentPadding = contentPadding,
        content = content,
        shape = shape
    )
}
@Composable
fun AppButtonWithIcon(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Blue20,
    ),
    shape: Shape = ButtonDefaults.shape,
    customSpace: Boolean = false,
    border: BorderStroke? = null,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    interactionSource: MutableInteractionSource? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit,
) {
    AppButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        interactionSource = interactionSource,
        border = border,
        buttonColors = buttonColors,
        shape = shape,
        contentPadding = contentPadding,
        /*contentPadding = if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },*/
    ) {
        ButtonContent(
            customSpace = customSpace,
            text = content,
            leadingIcon = leadingIcon,
        )
    }
}
//..................................................................................................




//App Outline Button
//..................................................................................................
@Composable
fun AppOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = MaterialTheme.colorScheme.onBackground,
    ),
    border: BorderStroke = BorderStroke(
        width = AppButtonDefaults.OutlinedButtonBorderWidth,
        color = if (enabled) {
            MaterialTheme.colorScheme.outline
        } else {
            MaterialTheme.colorScheme.onSurface.copy(
                alpha = AppButtonDefaults.DISABLED_OUTLINED_BUTTON_BORDER_ALPHA,
            )
        },
    ),
    interactionSource: MutableInteractionSource? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        elevation = elevation,
        colors = colors,
        border = border,
        interactionSource = interactionSource,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
fun AppOutlinedButtonWithIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    customSpace: Boolean = false,
    shape: Shape = ButtonDefaults.shape,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = Color.DarkGray,
    ),
    border: BorderStroke = BorderStroke(
        width = AppButtonDefaults.OutlinedButtonBorderWidth,
        color = if (enabled) {
            MaterialTheme.colorScheme.outline
        } else {
            MaterialTheme.colorScheme.onSurface.copy(
                alpha = AppButtonDefaults.DISABLED_OUTLINED_BUTTON_BORDER_ALPHA,
            )
        },
    ),
    interactionSource: MutableInteractionSource? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    AppOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        interactionSource = interactionSource,
        enabled = enabled,
        contentPadding = contentPadding,
    ) {
        ButtonContent(
            customSpace = customSpace,
            text = content,
            leadingIcon = leadingIcon,
        )
    }
}
//..................................................................................................



//App Text Button
//..................................................................................................
@Composable
fun AppTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors =  ButtonDefaults.textButtonColors(
        contentColor = Color.DarkGray,
    ),
    content: @Composable RowScope.() -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        content = content,
    )
}
@Composable
fun AppTextButtonWithIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    customSpace: Boolean = false,
    content: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    AppTextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        ButtonContent(
            customSpace = customSpace,
            text = content,
            leadingIcon = leadingIcon,
        )
    }

}
//..................................................................................................



// App Elevated Button
//..................................................................................................
@Composable
fun AppElevatedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
    ),
    shape: Shape = ButtonDefaults.shape,
    interactionSource: MutableInteractionSource? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit,
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        colors = colors,
        shape = shape,
        interactionSource = interactionSource,
        contentPadding = contentPadding,
        content = content,
    )
}

// App Elevated Button with Icon
@Composable
fun AppElevatedButtonWithIcon(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    customSpace: Boolean = false,
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Cyan,
    ),
    shape: Shape = ButtonDefaults.shape,
    interactionSource: MutableInteractionSource? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    AppElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        elevation = elevation,
        colors = colors,
        shape = shape,
        interactionSource = interactionSource,
        contentPadding = contentPadding,
    ) {
        ButtonContent(
            customSpace = customSpace,
            text = content,
            leadingIcon = leadingIcon,
        )
    }
}
//..................................................................................................



//Content
//..................................................................................................
@Composable
private fun ButtonContent(
    customSpace: Boolean,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    if(customSpace){

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start){
            if (leadingIcon != null) {
                Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    leadingIcon()
                }
            }
            Box(Modifier.weight(8f), contentAlignment = Alignment.Center){
                text()
            }
            Spacer(modifier = Modifier.weight(1f))
        }

    }
    else{
        if (leadingIcon != null) {
            Box(Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize)) {
                leadingIcon()
            }
        }
        Box(
            Modifier
                .padding(
                    start = if (leadingIcon != null) {
                        ButtonDefaults.IconSpacing
                    } else {
                        0.dp
                    },
                ),
        ) {
            text()
        }
    }


}
//..................................................................................................







@Preview(showBackground = true,showSystemUi = true, locale = "en")
@Composable
private fun AppButtonPreview(){
    CompositionLocalProvider(AppDirection.appLayoutDirectionProvider(LayoutDirections.LTR)) {
        Column(Modifier.fillMaxSize()) {
            Box(Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)){
                AppButton(
                    onClick = {},
                    content = { Text("AppButton") },
                    modifier = Modifier.fillMaxWidth(),
                    elevation =  ButtonDefaults.buttonElevation(defaultElevation=10.dp),
                    buttonColors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    contentPadding = PaddingValues(15.dp),
                    enabled = true,
                    border = null,
                    shape = RoundedCornerShape(10.dp),
                )
            }

            Box(Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp, vertical = 10.dp)){
                AppButtonWithIcon(
                    leadingIcon = {AppIcon(Icons.Rounded.Call,contentDescription = "")},
                    onClick = {},
                    content = { Text("AppButtonWithIcon") },
                    enabled = true,
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    buttonColors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                    border = null,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation= 4.dp ),
                    modifier = Modifier.fillMaxWidth(),
                    customSpace = true,
                )
            }

            Box(Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)){
                AppOutlinedButton(
                    onClick = {},
                    content = { Text(text = "HELLO WORK") },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.LightGray,
                    ),
                    enabled = true,
                    border = BorderStroke(
                        width = 2.dp,
                        color = Color.Cyan,
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation= 4.dp ),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(2.dp),
                    shape = RoundedCornerShape(20.dp)
                )
            }

            Box(Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)){
                AppOutlinedButtonWithIcon(
                    onClick = {},
                    content = { Text(text = "AppOutlinedButtonWithIcon") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Red
                    ),
                    enabled = true,
                    border = BorderStroke(
                        width = 2.dp,
                        color = Color.Cyan,
                    ),
                    customSpace = false,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation= 4.dp ),
                    shape = RectangleShape,
                    leadingIcon = { AppIcon(Icons.Rounded.Call,contentDescription = "") },
                )
            }

            Box(Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)){
                AppTextButton(
                    onClick = {},
                    content = { Text(text = "AppTextButton") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Box(Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)){
                AppTextButtonWithIcon(
                    onClick = {},
                    content = { Text(text = "AppTextButtonWithIcon") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { AppIcon(Icons.Rounded.Call,contentDescription = "") },
                )
            }

            Box(Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)){
                AppElevatedButton(
                    onClick = {},
                    content = { Text(text = "AppElevatedButton") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Red
                    ),
                    enabled = true,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation= 4.dp ),
                    shape = RectangleShape,
                )
            }

            Box(Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)){
                AppElevatedButtonWithIcon(
                    onClick = {},
                    leadingIcon = { AppIcon(Icons.Default.Favorite, contentDescription = null) },
                    content = { Text("AppElevatedButtonWithIcon") }
                )
            }

        }
    }
}







private object AppButtonDefaults {
    // TODO: File bug
    // OutlinedButton border color doesn't respect disabled state by default
    const val DISABLED_OUTLINED_BUTTON_BORDER_ALPHA = 0.12f

    // TODO: File bug
    // OutlinedButton default border width isn't exposed via ButtonDefaults
    val OutlinedButtonBorderWidth = 1.dp
}