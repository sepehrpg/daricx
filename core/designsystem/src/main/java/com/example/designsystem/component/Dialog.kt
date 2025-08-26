package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.ClickUpGray4
import com.example.designsystem.theme.ClickUpPink1
import com.example.designsystem.theme.PrimaryColor


@Composable
fun AppAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier,
    dismissButton: @Composable (() -> Unit)?,
    icon: @Composable (() -> Unit)?,
    title: @Composable (() -> Unit)?,
    text: @Composable (() -> Unit)?,
    shape: Shape,
    containerColor: Color,
    iconContentColor: Color,
    titleContentColor: Color,
    textContentColor: Color,
    tonalElevation: Dp,
    properties: DialogProperties
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        modifier = modifier,
        dismissButton = dismissButton,
        icon = icon,
        title = title,
        text = text,
        shape = shape,
        containerColor = containerColor,
        iconContentColor = iconContentColor,
        titleContentColor = titleContentColor,
        textContentColor = textContentColor,
        tonalElevation = tonalElevation,
        properties = properties
    )
}


@Composable
fun AppDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
        content = content
    )
}


@Composable
fun AppDeleteDialog(
    onDismissRequest: () -> Unit,
    onDelete: () -> Unit,
    showDialog:Boolean = false,
    properties: DialogProperties,
    content: @Composable () -> Unit = {
        Box(Modifier.fillMaxSize().background(Color.Transparent), contentAlignment = Alignment.BottomCenter){
            Column(Modifier.padding(horizontal = 20.dp)){
                Box(
                    Modifier
                        .fillMaxWidth()

                ) {
                    AppElevatedButtonWithIcon(
                        onClick = { onDelete() },
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation=0.dp, pressedElevation =2.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ClickUpGray4,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        content = {
                            Row(Modifier.padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically){
                                AppText("Delete", fontSize = 14.sp, modifier = Modifier.padding(top = 3.dp), color = ClickUpPink1)
                            }
                        }
                    )
                }

                Spacer(Modifier.height(10.dp))

                Box(
                    Modifier
                        .fillMaxWidth()
                ) {
                    AppElevatedButtonWithIcon(
                        onClick = { onDismissRequest() },
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation=0.dp, pressedElevation =2.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ClickUpGray4,
                        ),
                        shape = RoundedCornerShape(10.dp),
                        content = {
                            Row(Modifier.padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically){
                                AppText("Cancel", fontSize = 14.sp, modifier = Modifier.padding(top = 3.dp), color = PrimaryColor)
                            }
                        }
                    )
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
){
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
        content = content
    )
}







@Preview(showBackground = true)
@Composable
private fun AppAlertDialogPreview() {
    AppAlertDialog(
        onDismissRequest = { /* Handle dismiss */ },
        confirmButton = {
            TextButton(onClick = { /* Handle confirm */ }) {
                Text("Confirm")
            }
        },
        modifier = Modifier.padding(16.dp),
        dismissButton = {
            TextButton(onClick = { /* Handle dismiss */ }) {
                Text("Dismiss")
            }
        },
        icon = {
            AppIcon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Alert Icon",
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            Text(
                text = "Alert Title",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Text(
                text = "This is the alert dialog content. Add your message here.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        iconContentColor = MaterialTheme.colorScheme.error,
        titleContentColor = MaterialTheme.colorScheme.primary,
        textContentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 4.dp,
        properties = DialogProperties()
    )
}



@Preview(showBackground = true)
@Composable
fun AppDialogPreview() {
    AppDialog(
        onDismissRequest = { /* Handle dismiss */ },
        properties = DialogProperties(),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Custom Dialog",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "This is the content of the dialog. Customize it as needed.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { /* Handle dismiss */ }) {
                            Text("Dismiss")
                        }
                        TextButton(onClick = { /* Handle confirm */ }) {
                            Text("Confirm")
                        }
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppDeleteDialogPreview(){
    AppDeleteDialog(onDismissRequest = {}, properties = DialogProperties(), onDelete = {} )
}
