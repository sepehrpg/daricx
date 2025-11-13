package com.example.designsystem.component.tabs

import android.annotation.SuppressLint

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.Timeline
import com.example.designsystem.component.text.AppText
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

/**
 * Represents a single icon tab item.
 * Each tab can have:
 * - an icon (required)
 * - an optional contentDescription for accessibility
 * - an optional badge count (for notifications, updates, etc.)
 * - enabled/disabled state
 */
data class AppIconTab(
    val icon: ImageVector,
    val contentDescription: String? = null,
    val badgeCount: Int? = null,
    val enabled: Boolean = true,
)

/**
 * A pill-style icon-based segmented control.
 *
 * Similar to [AppPillTabs], but displays icons instead of text.
 * Use this when you need a compact toggle between visual icon states,
 * such as chart types, views, or tool modes.
 */
@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AppPillIconTabs(
    items: List<AppIconTab>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    height: Dp = 36.dp,
    cornerRadius: Dp = 12.dp,
    iconSize: Dp = 18.dp,
    colors: AppPillTabsColors = AppPillTabsDefaults.colors()
) {
    require(items.size >= 2) { "AppPillIconTabs requires at least two items." }

    val outerShape = RoundedCornerShape(cornerRadius)
    val innerShape = RoundedCornerShape((cornerRadius - 2.dp).coerceAtLeast(0.dp))

    BoxWithConstraints(
        modifier = modifier
            .height(height)
            .clip(outerShape)
            .background(colors.containerColor)
            .padding(4.dp)
    ) {
        // Each tab will take equal width inside the container
        val itemWidth = maxWidth / items.size

        // Calculate the horizontal offset of the selection indicator
        val targetX = itemWidth * selectedIndex
        val x by animateDpAsState(
            targetValue = targetX,
            animationSpec = spring(
                stiffness = Spring.StiffnessMediumLow,
                dampingRatio = 0.9f
            ),
            label = "pillOffset"
        )

        // Draw the selection background "pill"
        Surface(
            modifier = Modifier
                .offset(x = x)
                .width(itemWidth)
                .fillMaxHeight(),
            shape = innerShape,
            color = colors.selectedContainerColor,
            border = BorderStroke(1.dp, colors.selectedBorderColor),
            shadowElevation = 0.dp
        ) {}

        // Draw icon tabs
        Row(
            modifier = Modifier
                .fillMaxSize()
                .selectableGroup(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = index == selectedIndex
                val isEnabled = enabled && item.enabled
                val interaction = MutableInteractionSource()

                Box(
                    modifier = Modifier
                        .width(itemWidth)
                        .fillMaxHeight()
                        .clip(innerShape)
                        .alpha(if (isEnabled) 1f else 0.5f)
                        .semantics {
                            contentDescription = item.contentDescription ?: "tab_$index"
                        }
                        .selectable(
                            selected = isSelected,
                            onClick = { if (isEnabled) onSelected(index) },
                            role = Role.Tab,
                            interactionSource = interaction,
                            indication = ripple(bounded = true)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Optional badge indicator for notifications or updates
                    BadgedBox(
                        badge = {
                            val count = item.badgeCount
                            if (count != null && count > 0) {
                                Badge(){
                                    AppText("$count")
                                }
                            }
                        }
                    ) {
                        // Main icon content
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.contentDescription,
                            tint = if (isSelected)
                                colors.selectedContentColor
                            else
                                colors.unselectedContentColor,
                            modifier = Modifier.size(iconSize)
                        )
                    }
                }
            }
        }
    }
}

/** -------- Preview Section -------- */
@ThemePreviews
@Composable
private fun AppPillIconTabsPreview() {
    AppThemedPreview {
        var selected = 0
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppPillIconTabs(
                items = listOf(
                    AppIconTab(Icons.Outlined.ShowChart, "Price Chart"),
                    AppIconTab(Icons.Outlined.Timeline, "Trends", badgeCount = 3),
                    AppIconTab(Icons.Outlined.BarChart, "Markets"),
                ),
                selectedIndex = selected,
                onSelected = { selected = it }
            )
        }
    }
}
