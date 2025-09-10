package com.example.designsystem.component.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun AppIcon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.surfaceTint
){
    Icon(imageVector=imageVector,contentDescription=contentDescription,modifier=modifier,tint=tint)
}

@Composable
fun AppIcon(
    painter: Painter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.surfaceTint
){
    Icon(painter=painter,contentDescription=contentDescription,modifier=modifier,tint=tint)
}




@Preview
@Composable
private fun AppIconPreview(){
    AppIcon(Icons.Rounded.Call, contentDescription = "")
}