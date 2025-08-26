package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.config.direction.AppDirection
import com.example.designsystem.config.direction.LayoutDirections
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.theme.ClickUpGray1
import com.example.designsystem.theme.ClickUpWhiteBackground
import com.example.designsystem.theme.PrimaryColor


@Composable
fun AppBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(Color.Black),
    placeholder: @Composable (() -> Unit)? = null,
    customDecorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        @Composable { innerTextField -> innerTextField() }
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource ?: remember { MutableInteractionSource() },
        cursorBrush = cursorBrush,
        decorationBox = { innerTextField ->
            customDecorationBox {
                Box {
                    if (value.isEmpty() && placeholder != null) {
                        placeholder()
                    }
                    innerTextField()
                }
            }
        }
    )
}



@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = TextFieldDefaults.shape,
    colors: TextFieldColors = TextFieldDefaults.colors()
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource ?: remember { MutableInteractionSource() },
        shape = shape,
        colors = colors
    )
}







@Composable
fun AppOutlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = remember { MutableInteractionSource() },
    shape: Shape = OutlinedTextFieldDefaults.shape,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors
    )
}



@Composable
fun AppCustomSearchBarBasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.padding(vertical = 0.dp, horizontal = 0.dp),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle(fontSize = 16.sp, color = Color.Black),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = SolidColor(Color.Black),
    brush: Brush = Brush.horizontalGradient(listOf(Color.White,Color.Gray)),
    shape: Shape = RoundedCornerShape(20.dp),
    shadowValue:Dp = 2.dp,
    height: Dp = 48.dp,
    clearTextField: () -> Unit = {},
    placeholder: @Composable (() -> Unit)? = {
        AppText(
            text = "Search",
            style = TextStyle(fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
        )
    },
    customDecorationBox: @Composable (innerTextField: @Composable () -> Unit) -> Unit =
        { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .shadow(elevation = shadowValue, shape)
                    .clip(shape)
                    .background(brush), contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppIcon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.DarkGray,
                    )
                    Spacer(Modifier.width(7.dp))
                    Box(modifier = Modifier
                        .weight(1f)
                        .padding(top = 2.dp)) {
                        innerTextField()
                    }
                    if (value.isNotEmpty()){
                        AppIconButton(onClick ={ clearTextField() }) {
                            AppIcon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close Icon",
                            )
                        }
                    }
                }
            }
        }
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource ?: remember { MutableInteractionSource() },
        cursorBrush = cursorBrush,
        decorationBox = { innerTextField ->
            customDecorationBox {
                Box {
                    if (value.isEmpty() && placeholder != null) {
                        placeholder()
                    }
                    innerTextField()
                }
            }
        }
    )
}





@Preview()
@Composable
private fun AppTextFieldPreview() {
    var text by remember { mutableStateOf("") }

    CompositionLocalProvider(AppDirection.appLayoutDirectionProvider(LayoutDirections.LTR)) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 25.dp, horizontal = 10.dp)){

            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)){
                AppCustomSearchBarBasicTextField(
                    value = text,
                    onValueChange = { text = it },
                )
            }

        }
    }

}




@Preview(showBackground = true, showSystemUi = true, locale = "en")
@Composable
private fun AppTextFieldPreview2() {
    var text by remember { mutableStateOf("") }
    CompositionLocalProvider(AppDirection.appLayoutDirectionProvider(LayoutDirections.LTR)) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp, horizontal = 10.dp)){

            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)){
                AppTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Label") },
                    placeholder = { Text("Enter text here") },
                    leadingIcon = { AppIcon(imageVector = Icons.Default.Email, contentDescription = "Email Icon") },
                    trailingIcon = { AppIcon(imageVector = Icons.Default.Check, contentDescription = "Check Icon") },
                    supportingText = { Text("Supporting text goes here") },
                    isError = false,
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.Yellow,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                )
            }


            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)){
                AppBasicTextField(
                    value = text,
                    placeholder = {
                        AppText(
                            text = "Enter text here...",
                            style = TextStyle(fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
                        )
                    },
                    onValueChange = { text = it },
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black,textAlign = TextAlign.Center),
                    modifier = Modifier.padding(20.dp),
                    customDecorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .shadow(elevation = 4.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.White)
                                .padding(vertical = 10.dp, horizontal = 10.dp),contentAlignment = Alignment.Center
                        ) {
                            innerTextField()
                        }
                    }
                )
            }




            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)){
                AppBasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    placeholder = {
                        AppText(
                            text = "Enter text here...",
                            style = TextStyle(fontSize = 16.sp, color = Color.Gray, textAlign = TextAlign.Center)
                        )
                    },
                    textStyle = TextStyle(fontSize = 16.sp, color = Color.Black, textAlign = TextAlign.Center),
                    modifier = Modifier.padding(20.dp),
                    customDecorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .shadow(elevation = 4.dp, RoundedCornerShape(20.dp))
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(vertical = 10.dp, horizontal = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AppIcon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Icon",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Box(modifier = Modifier.weight(1f)) {
                                    innerTextField()
                                }
                            }
                        }
                    }
                )
            }



            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)){
                AppOutlineTextField(
                    value = "Enter text",
                    onValueChange = { },
                    label = { Text("Label") },
                    placeholder = { Text("Placeholder") },
                    leadingIcon = { AppIcon(Icons.Default.Search, contentDescription = "Search Icon") },
                    trailingIcon = { AppIcon(Icons.Default.Clear, contentDescription = "Clear Icon") },
                    supportingText = { Text("This is supporting text") },
                    isError = false,
                    singleLine = true
                )
            }


            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp)){
                AppOutlineTextFieldStatic1(
                    value = "Enter text",
                    onValueChange = { },
                )
            }

        }
    }

}









@Composable
fun AppOutlineTextFieldStatic1(
    value: String,
    placeHolder:String = "",
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    AppOutlineTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(fontSize = 14.sp),
        modifier = Modifier
            .fillMaxWidth().background(ClickUpWhiteBackground)
            .height(52.dp),
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = ClickUpGray1,
            focusedBorderColor = PrimaryColor.copy(alpha = 0.5f)
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus(true)
                keyboardController?.hide()
            }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
        ),
        placeholder = {
            AppText(placeHolder, color = Color.Gray, fontSize = 14.sp)
        }
    )
}


