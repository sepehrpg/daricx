package com.example.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.component.AppBackground
import com.example.designsystem.component.AppGradientBackground

/**
 * A class to model gradient color values for Now in Android.
 *
 * @param top The top gradient color to be rendered.
 * @param bottom The bottom gradient color to be rendered.
 * @param container The container gradient color over which the gradient will be rendered.
 */
@Immutable
data class GradientColors(
    val top: Color = Color.Unspecified,
    val bottom: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
)



/**
 * A composition local for [GradientColors].
 * staticCompositionLocalOf : instead of passing value as parameter in composable function you can define
 * this (for static value that don't need to recompose) or compositionLocalOf for dynamic value
 */
val LocalGradientColors = staticCompositionLocalOf { GradientColors() }


val GradientColor1 = GradientColors(
    top =  Color.White,
    bottom = Color(0xFF7E64FF),
    container = Color.White
)
val GradientColor2 = GradientColors(
    top =  Color.White,
    bottom = Color(0xFFffc6ff),
    container = Color.White
)
val GradientColor3 = GradientColors(
    top =  Color.White,
    bottom = Color(0xFFa0c4ff),
    container = Color.White
)
val GradientColor4 = GradientColors(
    top =  Color.White,
    bottom = Color(0xFF9bf6ff),
    container = Color.White
)
val GradientColor5 = GradientColors(
    top =  Color.White,
    bottom = Color(0xFFcaffbf),
    container = Color.White
)
val GradientColor6 = GradientColors(
    top =  Color.White,
    bottom = Color(0xFFffd6a5),
    container = Color.White
)
val GradientColor7 = GradientColors(
    top =  Color.White,
    bottom = Color(0xFFffadad),
    container = Color.White
)


val g1 = GradientColors(
    top =  Color.White,
    bottom = Color(0xFFcfbaf0),
    container = Color.White
)
val g2 = GradientColors(
    top =  Color.White,
    bottom = Color(0xFFf1c0e8),
    container = Color.White
)
val g3 = GradientColors(
    top =  Color.White,
    bottom = Color(0xFFffcfd2),
    container = Color.White
)
val g4 = GradientColors(
    top =  Color.White,
    bottom = Color(0xFFfde4cf),
    container = Color.White
)
val g5 = GradientColors(
    top =  Color.White,
    bottom = Color(0xFFfbf8cc),
    container = Color.White
)


data class GradientColorsList(
    val gradientColor:List<GradientColors>? = emptyList()
)



@Preview
@Composable
fun GradientPreview(){
    AppGradientBackground(
        gradientColors = GradientColor1,
        modifier = Modifier.fillMaxSize()
    ){

    }
}

@Preview
@Composable
fun GradientPreview2(){
    AppGradientBackground(
        gradientColors = GradientColor2,
        modifier = Modifier.fillMaxSize()
    ){

    }
}

@Preview
@Composable
fun GradientPreview3(){
    AppGradientBackground(
        gradientColors = g1,
        modifier = Modifier.fillMaxSize()
    ){

    }
}

@Preview
@Composable
fun GradientPreview5(){
    AppGradientBackground(
        gradientColors = g2,
        modifier = Modifier.fillMaxSize()
    ){

    }
}


@Preview
@Composable
fun GradientPreview6(){
    Box(Modifier.fillMaxSize().background(
        brush = Brush.linearGradient(
            listOf(
            Color(0xFF7E64FF),
            Color(0xFFffc6ff),
            Color(0xFFa0c4ff),
            Color(0xFF9bf6ff),
            Color(0xFFE91E63),
        ),
           //start = Offset(0.5f,100f),
           //end = Offset(100f,0f),
        )
    ))
}