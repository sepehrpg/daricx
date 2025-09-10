package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


/**
 * Data model for each tab in the pager.
 * You can override colors / modifiers per tab if needed.
 */
data class AppTabPagerItems(
    val title: String,
    val screenModifier: Modifier? = null,
    val tabRowModifier: Modifier? = null,
    val tabModifier: Modifier? = null,
    val pagerModifier: Modifier? = null,
    val tabContainerColor: Color? = null,
    val tabContentColor: Color? = null,
    val tabTextColor: Color? = null,
    val tabTextColorSelected: Color? = null,
    val indicatorColor: Color? = null,
    val indicatorWidth: Dp? = null,
    val indicatorShape: Shape? = null,
    val thicknessIndicator: Dp? = null,
    val dividerModifier: Modifier? = null,
    val dividerColor: Color? = null,
    val dividerThickness: Dp? = null,
    val icon: @Composable (() -> Unit)? = null,
    val contentScreens: @Composable () -> Unit = {}
)

/**
 * Reusable Tab + Pager component.
 */
@Composable
fun AppTabPager(
    tabs: List<AppTabPagerItems>,
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState(initialPage = 0) { tabs.size },
    // global defaults
    tabContainerColor: Color = Color.White,
    tabContentColor: Color = Color.Black,
    tabTextColor: Color = Color.Gray,
    tabTextColorSelected: Color = Color.DarkGray,
    indicatorColor: Color = Color.DarkGray,
    indicatorShape: Shape? = null,
    indicatorWidth: Dp? = null,
    indicatorWidthMatchWithTextSize: Boolean = true,
    thicknessIndicator: Dp = 2.dp,
    dividerColor: Color = DividerDefaults.color,
    dividerThickness: Dp = DividerDefaults.Thickness,
    scrollable: Boolean = tabs.size > 4,
    userScrollEnabled: Boolean = true,
    tabPadding: Dp = 0.dp,
    onPageChanged: (Int) -> Unit = {}
) {
    if (tabs.isEmpty()) {
        AppHorizontalDivider(color = dividerColor, thickness = dividerThickness)
        return
    }
    val textWidths = remember { mutableStateMapOf<Int, Int>() }

    val scope = rememberCoroutineScope()

    val currentPage = pagerState.currentPage
    val currentTab = tabs[currentPage]

    // Customizable indicator: rounded if shape != null, otherwise use Material default
    val indicator: @Composable (List<TabPosition>) -> Unit = Indicator@ { positions ->
        if (positions.isEmpty()) return@Indicator

        val safeIndex = pagerState.currentPage.coerceIn(0, positions.lastIndex)
        val tab = tabs[safeIndex]
        val current = positions[safeIndex]

        val height = tab.thicknessIndicator ?: thicknessIndicator
        val color = tab.indicatorColor ?: indicatorColor
        val shape = tab.indicatorShape ?: indicatorShape ?: RectangleShape
        val width = tab.indicatorWidth ?: indicatorWidth
        val textWidth = textWidths[safeIndex]?.toDp() ?: current.width

        if (width != null || indicatorWidthMatchWithTextSize) {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(current),
                    //.padding(horizontal = (current.width - width) / 2),
                width = if (indicatorWidthMatchWithTextSize) textWidth else width?:2.dp,
                height = height,
                color = color,
                shape = shape
            )
        } else {
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(current),
                height = height,
                color = color,
            )
        }
    }

    // divider
    val divider: @Composable () -> Unit = {
        AppHorizontalDivider(
            modifier = currentTab.dividerModifier ?: Modifier,
            color = currentTab.dividerColor ?: dividerColor,
            thickness = currentTab.dividerThickness ?: dividerThickness
        )
    }

    // Tabs row
    val tabRowContent: @Composable () -> Unit = {
        tabs.forEachIndexed { index, tab ->
            val selected = currentPage == index
            Tab(
                modifier = tab.tabModifier ?: Modifier.padding(vertical = 0.dp, horizontal = 0.dp),
                selected = selected,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } }
            ) {
                Column(
                    modifier = Modifier.padding(vertical = tabPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    tab.icon?.invoke()
                    Text(
                        text = tab.title,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = if (selected)
                                tab.tabTextColorSelected ?: tabTextColorSelected
                            else
                                tab.tabTextColor ?: tabTextColor
                        ),
                        modifier = Modifier.onGloballyPositioned { coords ->
                            textWidths[index] = coords.size.width
                        }
                    )
                }
            }
        }
    }

   Column {
       if (scrollable) {
           ScrollableTabRow(
               selectedTabIndex = currentPage,
               modifier = currentTab.tabRowModifier ?: Modifier,
               containerColor = currentTab.tabContainerColor ?: tabContainerColor,
               contentColor = currentTab.tabContentColor ?: tabContentColor,
               edgePadding = 0.dp,
               indicator = indicator,
               divider = divider,
               tabs = tabRowContent,
           )
       } else {
           TabRow(
               selectedTabIndex = currentPage,
               modifier = currentTab.tabRowModifier ?: Modifier,
               containerColor = currentTab.tabContainerColor ?: tabContainerColor,
               contentColor = currentTab.tabContentColor ?: tabContentColor,
               indicator = indicator,
               divider = divider,
               tabs = tabRowContent
           )
       }

       HorizontalPager(
           state = pagerState,
           modifier = modifier.fillMaxSize(),
           userScrollEnabled = userScrollEnabled
       ) { page ->
           tabs[page].contentScreens()
       }

   }
    LaunchedEffect(pagerState) {
        snapshotFlow { currentPage }
            .collect { onPageChanged(it) }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppTabPagerPreview() {
    val tabs = listOf(
        AppTabPagerItems(
            title = "Home",
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            contentScreens = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Home Page Content", fontSize = 22.sp)
                }
            },
            tabTextColorSelected = Color.Green,
            indicatorColor = Color.Green,
        ),
        AppTabPagerItems(
            title = "Settings",
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            contentScreens = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Settings Page Content", fontSize = 22.sp)
                }
            },
            tabTextColorSelected = Color.Red,
            indicatorColor = Color.Red
        ),
        AppTabPagerItems(
            title = "Favorites",
            icon = { Icon(Icons.Filled.Star, contentDescription = "Favorites") },
            contentScreens = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Favorites Page Content", fontSize = 22.sp)
                }
            },
            tabTextColorSelected = Color.Blue,
            indicatorColor = Color.Blue
        )
    )

    AppTabPager(tabs = tabs)
}


@Composable
private fun Int.toDp(): Dp {
    return with(LocalDensity.current) { this@toDp.toDp() }
}