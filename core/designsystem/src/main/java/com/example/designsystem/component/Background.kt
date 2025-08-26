package com.example.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.GradientColors
import com.example.designsystem.theme.LocalBackgroundTheme
import com.example.designsystem.theme.LocalGradientColors
import com.example.designsystem.theme.AppTheme
import com.example.designsystem.theme.ThemePreviews
import kotlin.math.tan

/**
 * The main background for the app.
 * Uses [LocalBackgroundTheme] to set the color and tonal elevation of a [Surface].
 *
 * @param modifier Modifier to be applied to the background.
 * @param content The background content.
 */
@Composable
fun AppBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val color = LocalBackgroundTheme.current.color
    val tonalElevation = LocalBackgroundTheme.current.tonalElevation
    Surface(
        color = if (color == Color.Unspecified) Color.Transparent else color,
        tonalElevation = if (tonalElevation == Dp.Unspecified) 0.dp else tonalElevation,
        modifier = modifier,
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            content()
        }
    }
}

/**
 * A gradient background for select screens. Uses [LocalBackgroundTheme] to set the gradient colors
 * of a [Box] within a [Surface].
 *
 * @param modifier Modifier to be applied to the background.
 * @param gradientColors The gradient colors to be rendered.
 * @param content The background content.
 */
@Composable
fun AppGradientBackground(
    modifier: Modifier = Modifier,
    gradientColors: GradientColors = LocalGradientColors.current,
    content: @Composable () -> Unit,
) {
    val currentTopColor by rememberUpdatedState(gradientColors.top)
    val currentBottomColor by rememberUpdatedState(gradientColors.bottom)
    Surface(
        color = if (gradientColors.container == Color.Unspecified) {
            Color.Transparent
        } else {
            gradientColors.container
        },
        modifier = modifier,
    ) {
        Box(
            Modifier
                .drawWithCache {
                    // Compute the start and end coordinates such that the gradients are angled 11.06
                    // degrees off the vertical axis
                    val offset = size.height * tan(
                        Math
                            .toRadians(40.06)
                            .toFloat(),
                    )

                    //vertical
                    val start = Offset(size.width / 2 + offset / 2, 0f)
                    val end = Offset(size.width / 2 - offset / 2, size.height)

                    //horizontal
                    //val start = Offset(0f, size.height / 2)
                    //val end = Offset(size.width, size.height / 2)

                    // Create the top gradient that fades out after the halfway point vertically
                    val topGradient = Brush.linearGradient(
                        0f to if (currentTopColor == Color.Unspecified) {
                            Color.Transparent
                        } else {
                            currentTopColor
                        },
                        0.724f to Color.Transparent,
                        start = start,
                        end = end,
                    )
                    // Create the bottom gradient that fades in before the halfway point vertically
                    val bottomGradient = Brush.linearGradient(
                        0.2552f to Color.Transparent,
                        1f to if (currentBottomColor == Color.Unspecified) {
                            Color.Transparent
                        } else {
                            currentBottomColor
                        },
                        start = start,
                        end = end,
                    )

                    onDrawBehind {
                        // There is overlap here, so order is important
                        drawRect(topGradient)
                        drawRect(bottomGradient)
                    }
                },
        ) {
            content()
        }
    }
}


@Composable
fun AppHorizontalGradientBackground(
    modifier: Modifier = Modifier,
    gradientColors: GradientColors = LocalGradientColors.current,
    content: @Composable () -> Unit,
) {
    val currentTopColor by rememberUpdatedState(gradientColors.top)
    val currentBottomColor by rememberUpdatedState(gradientColors.bottom)
    Surface(
        color = if (gradientColors.container == Color.Unspecified) {
            Color.Transparent
        } else {
            gradientColors.container
        },
        modifier = modifier,
    ) {
        Box(
            Modifier
                .drawWithCache {
                    // Compute the start and end coordinates for the horizontal gradient
                    val offset = size.width * tan(
                        Math
                            .toRadians(11.06)
                            .toFloat(),
                    )

                    // Create the top gradient that fades out after the halfway point horizontally
                    val topGradient = Brush.horizontalGradient(
                        0f to if (currentTopColor == Color.Unspecified) {
                            Color.Transparent
                        } else {
                            currentTopColor
                        },
                        0.724f to Color.Transparent,
                        startX = 0f,
                        endX = size.width + offset,
                    )
                    // Create the bottom gradient that fades in before the halfway point horizontally
                    val bottomGradient = Brush.horizontalGradient(
                        0.2552f to Color.Transparent,
                        1f to if (currentBottomColor == Color.Unspecified) {
                            Color.Transparent
                        } else {
                            currentBottomColor
                        },
                        startX = 0f,
                        endX = size.width + offset,
                    )

                    onDrawBehind {
                        // There is overlap here, so order is important
                        drawRect(topGradient)
                        drawRect(bottomGradient)
                    }
                },
        ) {
            content()
        }
    }
}



@Composable
fun AppCircleGradientBackground(
    modifier: Modifier = Modifier,
    gradientColors: GradientColors = LocalGradientColors.current,
    content: @Composable () -> Unit,
) {
    val currentTopColor by rememberUpdatedState(gradientColors.top)
    val currentBottomColor by rememberUpdatedState(gradientColors.bottom)
    Surface(
        color = if (gradientColors.container == Color.Unspecified) {
            Color.Transparent
        } else {
            gradientColors.container
        },
        modifier = modifier.fillMaxSize(),
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .drawWithCache {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.minDimension / 2

                val radialGradient = Brush.radialGradient(
                    colors = listOf(
                        if (currentTopColor == Color.Unspecified) Color.Transparent else currentTopColor,
                        if (currentBottomColor == Color.Unspecified) Color.Transparent else currentBottomColor,
                    ),
                    center = center,
                    radius = radius
                )

                onDrawBehind {
                    drawRect(radialGradient)
                }
            },
        ) {
            content()
        }
    }
}




@ThemePreviews
@Composable
private fun BackgroundDefault() {
    AppTheme(disableDynamicTheming = true) {
        AppBackground(Modifier.size(100.dp), content = {})
    }
}

@ThemePreviews
@Composable
private fun BackgroundDynamic() {
    AppTheme(disableDynamicTheming = false) {
        AppBackground(Modifier.size(100.dp), content = {})
    }
}

@ThemePreviews
@Composable
private fun BackgroundAndroid() {
    AppTheme(androidTheme = true) {
        AppBackground(Modifier.size(100.dp), content = {})
    }
}

@ThemePreviews
@Composable
private fun GradientBackgroundDefault() {
    AppTheme(disableDynamicTheming = true) {
        AppGradientBackground(Modifier.size(100.dp), content = {})
    }
}

@ThemePreviews
@Composable
private fun GradientBackgroundDynamic() {
    AppTheme(disableDynamicTheming = false) {
        AppGradientBackground(Modifier.size(100.dp), content = {})
    }
}


@ThemePreviews
@Composable
private fun GradientBackgroundAndroid() {
    AppTheme(androidTheme = true) {
        AppGradientBackground(Modifier.size(100.dp), content = {})
    }
}
