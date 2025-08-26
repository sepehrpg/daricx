package com.example.designsystem.extension

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp



@SuppressLint("UnrememberedMutableInteractionSource")
fun Modifier.clickableWithNoRipple(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null, // No ripple effect
        interactionSource = MutableInteractionSource(), // Required for handling interactions
        onClick = onClick
    )
}


@Stable
fun Modifier.mirror(): Modifier {
    return if (!AppCompatDelegate.getApplicationLocales().toLanguageTags().contains("en"))
        this.scale(scaleX = -1f, scaleY = 1f)
    else
        this
}


@Stable
fun Modifier.mirrorReverse(forceMirrorReverse:Boolean = false): Modifier {
        return if(forceMirrorReverse) this.scale(scaleX = -1f, scaleY = 1f)
        else if (AppCompatDelegate.getApplicationLocales().toLanguageTags().contains("en"))
        this.scale(scaleX = -1f, scaleY = 1f)
        else this
}


fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter = (BlurMaskFilter(blurRadius.toPx(),
                    BlurMaskFilter.Blur.NORMAL))
            }
            frameworkPaint.color = color.toArgb()

            val leftPixel = offsetX.toPx()
            val topPixel = offsetY.toPx()
            val rightPixel = size.width + topPixel
            val bottomPixel = size.height + leftPixel

            canvas.drawRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                paint = paint,
            )
        }
    }
)



fun Modifier.notificationDot(): Modifier =
    composed {
        val tertiaryColor = MaterialTheme.colorScheme.tertiary
        drawWithContent {
            drawContent()
            drawCircle(
                tertiaryColor,
                radius = 5.dp.toPx(),
                // This is based on the dimensions of the NavigationBar's "indicator pill";
                // however, its parameters are private, so we must depend on them implicitly
                // (NavigationBarTokens.ActiveIndicatorWidth = 64.dp)
                center = center + Offset(
                    64.dp.toPx() * .45f,
                    32.dp.toPx() * -.45f - 6.dp.toPx(),
                ),
            )
        }
    }


/**
 * Screen height fraction
 *
 * ex::Box(
 *             modifier = Modifier
 *                 .screenHeightFraction(0.5f) // 50% of screen height
 *                 .screenWidthFraction(0.5f)  // 50% of screen width
 *                 .background(Color.Blue)
 *         ) {
 *             // Your content here
 *         }
 *
 * @param fraction
 * @return
 */
@Composable
fun Modifier.screenHeightFraction(fraction: Float): Modifier {
    return this.then(
        with(LocalDensity.current) {
            val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
            val heightDp = screenHeightDp * fraction
            Modifier.height(heightDp)
        }
    )
}


/**
 * Screen width fraction
 *
 * ex:: Box(
 *             modifier = Modifier
 *                 .screenHeightFraction(0.5f) // 50% of screen height
 *                 .screenWidthFraction(0.5f)  // 50% of screen width
 *                 .background(Color.Blue)
 *         ) {
 *             // Your content here
 *         }
 *
 * @param fraction
 * @return
 */
@Composable
fun Modifier.screenWidthFraction(fraction: Float): Modifier {
    return this.then(
        with(LocalDensity.current) {
            val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp
            val widthDp = screenWidthDp * fraction
            Modifier.width(widthDp)
        }
    )
}





enum class BorderSide {
    START, TOP, END, BOTTOM
}

fun Modifier.borderSide(
    color: Color = Color.Black,
    width: Dp = 1.dp,
    sides: Set<BorderSide> = setOf(BorderSide.BOTTOM)
): Modifier = this.then(
    Modifier.drawBehind {
        val strokeWidth = width.toPx()
        sides.forEach { side ->
            when (side) {
                BorderSide.START -> drawLine(
                    color = color,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = strokeWidth
                )
                BorderSide.TOP -> drawLine(
                    color = color,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = strokeWidth
                )
                BorderSide.END -> drawLine(
                    color = color,
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
                BorderSide.BOTTOM -> drawLine(
                    color = color,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            }
        }
    }
)






fun Modifier.dottedLine(
    color: Color = Color.Black,
    dotRadius: Dp = 4.dp,
    gap: Dp = 8.dp
) = this.drawBehind {
    val paint = android.graphics.Paint().apply {
        this.color = color.toArgb()
        style = android.graphics.Paint.Style.FILL
    }

    val radiusPx = with(Density(density)) { dotRadius.toPx() }
    val gapPx = with(Density(density)) { gap.toPx() }
    val totalWidth = size.width

    var currentX = 0f

    while (currentX < totalWidth) {
        drawIntoCanvas { canvas ->
            canvas.nativeCanvas.drawCircle(currentX + radiusPx, size.height / 2, radiusPx, paint)
        }
        currentX += 2 * radiusPx + gapPx
    }
}








