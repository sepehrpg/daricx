package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun AppContainerPrimary(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    backgroundColor: Color = Color.White,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = shape)
            .clip(shape),
        contentAlignment = Alignment.Center,
        content = content
    )
}


@Preview
@Composable
private fun AppContainerPrimaryPreview(){
    AppContainerPrimary(
        modifier = Modifier
            .size(100.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.Blue
    ) {
        Icon(
            Icons.Default.Brush,
            contentDescription = "",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}