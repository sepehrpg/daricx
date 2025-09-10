package com.example.designsystem.component.tabs


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color
import com.example.designsystem.component.AppText
import com.example.designsystem.theme.AppTheme
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

/**
 * A pill-style segmented control.
 *
 * Use when you need to toggle between a small number of mutually exclusive options.
 */
@Composable
fun AppPillTabs(
    items: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    height: Dp = 36.dp,
    cornerRadius: Dp = 12.dp,
    colors: AppPillTabsColors = AppPillTabsDefaults.colors()
) {
    require(items.size >= 2) { "AppPillTabs requires at least two items." }
    val outerShape = RoundedCornerShape(cornerRadius)
    val innerShape = RoundedCornerShape((cornerRadius - 2.dp).coerceAtLeast(0.dp))

    BoxWithConstraints(
        modifier = modifier
            .height(height)
            .clip(outerShape)
            .background(colors.containerColor)
            .padding(4.dp)
    ) {
        val itemWidth = maxWidth / items.size
        val targetX = itemWidth * selectedIndex
        val x by animateDpAsState(
            targetValue = targetX,
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow, dampingRatio = 0.9f),
            label = "pillOffset"
        )

        // Selection background
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

        // Tab labels
        Row(
            modifier = Modifier
                .fillMaxSize()
                .selectableGroup(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, title ->
                val selected = index == selectedIndex
                val interaction = remember { MutableInteractionSource() }
                Box(
                    modifier = Modifier
                        .width(itemWidth)
                        .fillMaxHeight()
                        .clip(innerShape)
                        .selectable(
                            selected = selected,
                            onClick = { if (enabled) onSelected(index) },
                            role = androidx.compose.ui.semantics.Role.Tab,
                            interactionSource = interaction,
                            indication = ripple()
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AppText(
                        text = title,
                        color = if (selected) colors.selectedContentColor
                        else colors.unselectedContentColor,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

/** Colors used by [AppPillTabs]. */
class AppPillTabsColors internal constructor(
    val containerColor: Color,
    val selectedContainerColor: Color,
    val selectedBorderColor: Color,
    val selectedContentColor: Color,
    val unselectedContentColor: Color
)

/** Default colors for [AppPillTabs]. */
object AppPillTabsDefaults {
    @Composable
    fun colors(
        containerColor: Color =
            MaterialTheme.colorScheme.surfaceVariant,
        selectedContainerColor: Color =
            MaterialTheme.colorScheme.background,
        selectedBorderColor: Color =
            MaterialTheme.colorScheme.outlineVariant,
        selectedContentColor: Color =
            MaterialTheme.colorScheme.onSurface,
        unselectedContentColor: Color =
            MaterialTheme.colorScheme.onSurfaceVariant
    ) = AppPillTabsColors(
        containerColor,
        selectedContainerColor,
        selectedBorderColor,
        selectedContentColor,
        unselectedContentColor
    )
}

@ThemePreviews
@Composable
private fun AppPillTabsPreview() {
    var selected by remember { mutableStateOf(0) }
    AppThemedPreview {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AppPillTabs(
                items = listOf("Light", "Dark", "System"),
                selectedIndex = selected,
                onSelected = { selected = it }
            )
            AppPillTabs(
                items = listOf("1D", "7D", "1M", "1Y", "All"),
                selectedIndex = selected.coerceIn(0, 4),
                onSelected = { selected = it }
            )
        }
    }
}
