package com.example.designsystem.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Represents a single swipe action revealed behind the row when swiping.
 */
data class SwipeAction(
    val icon: ImageVector,
    val iconSize:Dp= 20.dp,
    val label: String? = null,
    val backgroundColor: Color,
    val contentColor: Color = Color.White,
    val onClick: () -> Unit,
)

/**
 * A reusable row that reveals actions when swiping horizontally.
 *
 * - Swiping towards the start side reveals [startActions]
 * - Swiping towards the end side reveals [endActions]
 *
 * You can:
 * - Use only [endActions] for a "right-to-left" swipe experience
 * - Use only [startActions] for a "left-to-right" swipe experience
 * - Use both for bi-directional actions
 *
 * The [actions] parameter is kept for backward compatibility and is treated as [endActions].
 */
@Composable
fun SwipeableActionsRow(
    modifier: Modifier = Modifier,
    actions: List<SwipeAction> = emptyList(),
    startActions: List<SwipeAction> = emptyList(),
    endActions: List<SwipeAction> = actions,
    actionWidth: Dp = 72.dp,
    shape: Shape = RoundedCornerShape(0.dp),
    elevation: Dp = 2.dp,
    cardColors: CardColors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
    ),
    openThresholdFraction: Float = 0.4f,
    swipeEnabled: Boolean = true,
    onItemClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    val hasStartActions = startActions.isNotEmpty()
    val hasEndActions = endActions.isNotEmpty()

    if (!hasStartActions && !hasEndActions) {
        Card(
            modifier = modifier,
            shape = shape,
            colors = cardColors,
            elevation = CardDefaults.cardElevation(elevation)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = onItemClick != null) {
                        onItemClick?.invoke()
                    }
                    .padding(0.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
        return
    }

    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    val startWidthPx = if (hasStartActions) {
        startActions.size * with(density) { actionWidth.toPx() }
    } else 0f

    val endWidthPx = if (hasEndActions) {
        endActions.size * with(density) { actionWidth.toPx() }
    } else 0f

    val minOffset = if (hasEndActions) -endWidthPx else 0f
    val maxOffset = if (hasStartActions) startWidthPx else 0f

    val offsetX = remember { Animatable(0f) }

    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            // 🔴 Global shape clipping applied here
            .clip(shape)
    ) {

        // 🔹 Background layer: start & end actions (NO extra clip here)
        Box(
            modifier = Modifier
                .matchParentSize()
        ) {
            if (hasStartActions) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    startActions.forEach { action ->
                        SwipeActionBox(
                            action = action,
                            width = actionWidth,
                            onActionPerformed = {
                                coroutineScope.launch {
                                    offsetX.animateTo(
                                        targetValue = 0f,
                                        animationSpec = tween(durationMillis = 200)
                                    )
                                }
                            }
                        )
                    }
                }
            }

            if (hasEndActions) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    endActions.forEach { action ->
                        SwipeActionBox(
                            action = action,
                            width = actionWidth,
                            onActionPerformed = {
                                coroutineScope.launch {
                                    offsetX.animateTo(
                                        targetValue = 0f,
                                        animationSpec = tween(durationMillis = 200)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }

        // 🔹 Foreground layer: main card sliding above actions
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .then(
                    if (swipeEnabled) {
                        Modifier.pointerInput(startActions, endActions, startWidthPx, endWidthPx) {
                            detectHorizontalDragGestures(
                                onHorizontalDrag = { change, dragAmount ->
                                    change.consume()
                                    coroutineScope.launch {
                                        val newValue = (offsetX.value + dragAmount)
                                            .coerceIn(minOffset, maxOffset)
                                        offsetX.snapTo(newValue)
                                    }
                                },
                                onDragEnd = {
                                    coroutineScope.launch {
                                        val current = offsetX.value

                                        val startThreshold = startWidthPx * openThresholdFraction
                                        val endThreshold = endWidthPx * openThresholdFraction

                                        val target = when {
                                            current > 0f && hasStartActions -> {
                                                if (current > startThreshold) startWidthPx else 0f
                                            }
                                            current < 0f && hasEndActions -> {
                                                if (abs(current) > endThreshold) -endWidthPx else 0f
                                            }
                                            else -> 0f
                                        }

                                        offsetX.animateTo(
                                            targetValue = target,
                                            animationSpec = tween(durationMillis = 200)
                                        )
                                    }
                                }
                            )
                        }
                    } else {
                        Modifier
                    }
                )
                .clickable(
                    enabled = onItemClick != null,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple()
                ) {
                    onItemClick?.invoke()
                },
            // 🔴 Inner card uses rectangle shape; outer Box already has the real rounded shape
            shape = RectangleShape,
            colors = cardColors,
            elevation = CardDefaults.cardElevation(elevation)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

/**
 * Internal building block for a single action box behind the row.
 */
@Composable
private fun SwipeActionBox(
    action: SwipeAction,
    width: Dp,
    onActionPerformed: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(width)
            .fillMaxHeight()
            .background(action.backgroundColor)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true, color = action.contentColor)
            ) {
                action.onClick()
                onActionPerformed()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = action.label,
                tint = action.contentColor,
                modifier = Modifier.size(action.iconSize)
            )
            if (!action.label.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = action.label,
                    color = action.contentColor,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Preview demonstrating:
 * - End-only actions (right-to-left swipe)
 * - Start-only actions (left-to-right swipe)
 * - Bi-directional actions (both sides)
 */
@Preview(showBackground = true)
@Composable
fun SwipeableActionsRowPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // End side only (right-to-left swipe)
            SwipeableActionsRow(
                endActions = listOf(
                    SwipeAction(
                        icon = Icons.Default.Edit,
                        label = "Edit",
                        backgroundColor = Color(0xFF4CAF50),
                        onClick = {}
                    ),
                    SwipeAction(
                        icon = Icons.Default.Delete,
                        label = "Delete",
                        backgroundColor = Color(0xFFF44336),
                        onClick = {}
                    )
                ),
                onItemClick = { }
            ) {
                Text(
                    text = "Swip from right to left",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Start side only (left-to-right swipe)
            SwipeableActionsRow(
                startActions = listOf(
                    SwipeAction(
                        icon = Icons.Default.Edit,
                        label = "Edit",
                        backgroundColor = Color(0xFF1976D2),
                        onClick = {}
                    )
                ),
                onItemClick = {}
            ) {
                Text(
                    text = "Swipe from left to right",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Both sides enabled
            SwipeableActionsRow(
                startActions = listOf(
                    SwipeAction(
                        icon = Icons.Default.Edit,
                        label = "Edit",
                        backgroundColor = Color(0xFF4CAF50),
                        onClick = {}
                    )
                ),
                endActions = listOf(
                    SwipeAction(
                        icon = Icons.Default.Delete,
                        label = "Delete",
                        backgroundColor = Color(0xFFF44336),
                        onClick = {}
                    )
                ),
                onItemClick = {}
            ) {
                Text(
                    text = "Bi-directional swipe (both sides)",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
