package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.ScrollableTabRowEdgeStartPadding
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.Blue20
import com.example.designsystem.theme.GradientColors
import com.example.designsystem.theme.Orange20
import kotlinx.coroutines.launch


/**
 *
 * A custom Composable for creating a tabbed interface with swipeable pages.
 *
 * @param tabs List of tab titles.
 * @param contentScreens List of Composable functions representing content screens for each tab.
 * @param modifier Modifier for the parent layout.
 * @param tabContainerColor Background color for the tab row container.
 * @param tabContentColor Color for the text content of the tabs.
 * @param indicatorColor Color for the indicator line.
 *
 * EXAMPLE OF USAGE::
 *     val tabs = listOf("Tab 1", "Tab 2", "Tab 3")
 *     val contentScreens: List<@Composable () -> Unit> = listOf(
 *         { Text("Content 1") },
 *         { Text("Content 2") },
 *         { Text("Content 3") },
 *     )
 */
@Composable
fun AppTabPager(
    tabs: List<AppTabPagerItems>,
    screenModifier: Modifier = Modifier,
    tabRowModifier: Modifier = Modifier,
    tabModifier: Modifier = Modifier.padding(vertical = 5.dp, horizontal = 0.dp),
    pagerModifier: Modifier = Modifier,
    tabContainerColor: Color = Color.White,
    tabContentColor: Color = Color.Black,
    tabTextColor: Color = Color.Gray,
    tabTextColorSelected: Color = Color.Blue,
    indicatorColor: Color = Color.DarkGray,
    indicatorWidth:Dp? = null,
    indicatorShape: Shape = RoundedCornerShape(3.0.dp),
    thicknessIndicator: Dp = 1.dp,
    dividerModifier: Modifier = Modifier,
    dividerColor: Color = DividerDefaults.color,
    dividerThickness: Dp = DividerDefaults.Thickness,
    scrollableTab: Boolean = false,
    pageIndexCallBack: (index:Int)->Unit = {}
) {


    // Pager state for controlling pages.
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()

    // Function to render a single tab.
    @Composable
    fun RenderTab(index: Int, tabTitle: String) {
        Tab(
            modifier = tabs[index].tabModifier?: tabModifier,
            selected = pagerState.currentPage == index,
            onClick = {
                coroutineScope.launch { pagerState.animateScrollToPage(index) }
            }
        ) {
            // If an icon for this tab is provided, show icon above text.
            if (tabs[index].icon != null) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    tabs[index].icon?.let { it() }
                    Text(
                        text = tabTitle,
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color =
                            if (pagerState.currentPage == index)
                                tabs[index].tabTextColorSelected?:tabTextColorSelected
                            else tabs[index].tabTextColor?:tabTextColor
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            } else {
                // Otherwise, simply display the tab title.
                Text(
                    text = tabTitle,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color =
                        if (pagerState.currentPage == index)
                            tabs[index].tabTextColorSelected?:tabTextColorSelected
                        else tabs[index].tabTextColor?:tabTextColor
                    ),
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }



    Column(modifier = screenModifier.fillMaxSize()) {
        if (scrollableTab) {
            ScrollableTabRow(
                modifier = tabs[pagerState.currentPage].tabRowModifier?:tabRowModifier,
                selectedTabIndex = pagerState.currentPage,
                containerColor = tabs[pagerState.currentPage].tabContainerColor?:tabContainerColor,
                contentColor = tabs[pagerState.currentPage].tabContentColor?:tabContentColor,
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    if(indicatorWidth!=null){
                        TabRowDefaults.PrimaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = tabs[pagerState.currentPage].indicatorColor?:indicatorColor,
                            height = tabs[pagerState.currentPage].thicknessIndicator?:thicknessIndicator,
                            width = tabs[pagerState.currentPage].indicatorWidth?:indicatorWidth,
                            shape = tabs[pagerState.currentPage].indicatorShape?:indicatorShape
                        )
                    }
                    else{
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = tabs[pagerState.currentPage].indicatorColor?:indicatorColor,
                            height = tabs[pagerState.currentPage].thicknessIndicator?:thicknessIndicator,
                        )
                    }
                },
                divider = {
                    HorizontalDivider(
                        modifier = tabs[pagerState.currentPage].dividerModifier?:dividerModifier,
                        color = tabs[pagerState.currentPage].dividerColor?:dividerColor,
                        thickness = tabs[pagerState.currentPage].dividerThickness?:dividerThickness
                    )
                }
            ) {
                tabs.forEachIndexed { index, tabTitle ->
                    RenderTab(index, tabs[index].title)
                }
            }
        } else {
            TabRow(
                modifier = tabs[pagerState.currentPage].tabRowModifier?:tabRowModifier,
                selectedTabIndex = pagerState.currentPage,
                containerColor = tabs[pagerState.currentPage].tabContainerColor?:tabContainerColor,
                contentColor = tabs[pagerState.currentPage].tabContentColor?:tabContentColor,
                indicator = { tabPositions ->
                    if(indicatorWidth!=null){
                        TabRowDefaults.PrimaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = tabs[pagerState.currentPage].indicatorColor?:indicatorColor,
                            height = tabs[pagerState.currentPage].thicknessIndicator?:thicknessIndicator,
                            width = tabs[pagerState.currentPage].indicatorWidth?:indicatorWidth,
                            shape = tabs[pagerState.currentPage].indicatorShape?:indicatorShape
                        )
                    }
                    else{
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                            color = tabs[pagerState.currentPage].indicatorColor?:indicatorColor,
                            height = tabs[pagerState.currentPage].thicknessIndicator?:thicknessIndicator,
                        )
                    }
                },
                divider = {
                    HorizontalDivider(
                        modifier = tabs[pagerState.currentPage].dividerModifier?:dividerModifier,
                        color = tabs[pagerState.currentPage].dividerColor?:dividerColor,
                        thickness = tabs[pagerState.currentPage].dividerThickness?:dividerThickness
                    )
                }
            ) {
                tabs.forEachIndexed { index, tabTitle ->
                    RenderTab(index, tabs[index].title)
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = pagerModifier.weight(1f)
        ) { page ->
            tabs[page].contentScreens()
        }
    }


    // Use LaunchedEffect to call the callback when the current page changes.
    LaunchedEffect(pagerState.currentPage) {
        pageIndexCallBack(pagerState.currentPage)
    }
}






@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun AppTabPagerPreview(){
    AppGradientBackground(
        gradientColors = GradientColors(Color(0xFFee9ca7),Color(0xFFffdde1)),
        modifier = Modifier.fillMaxSize()
    ){
        val tabItems = listOf(
            AppTabPagerItems(
                title = "Home",
                icon = { AppIcon(imageVector = Icons.Filled.Home, contentDescription = "Home", modifier = Modifier.size(18.dp)) },
                contentScreens = { Surface(modifier = Modifier.fillMaxSize(), color = Color.White) { Text("Home Content", modifier = Modifier.padding(16.dp)) } }
            ),
            AppTabPagerItems(
                title = "Settings",
                icon = { AppIcon(imageVector = Icons.Filled.Settings, contentDescription = "Settings", modifier = Modifier.size(18.dp)) },
                contentScreens = { Surface(modifier = Modifier.fillMaxSize(), color = Color.White) { Text("Settings Content", modifier = Modifier.padding(16.dp)) } }
            ),
            AppTabPagerItems(
                title = "Info",
                icon = { AppIcon(imageVector = Icons.Filled.Info, contentDescription = "Info", modifier = Modifier.size(18.dp)) },
                contentScreens = { Surface(modifier = Modifier.fillMaxSize(), color = Color.White) { Text("Info Content", modifier = Modifier.padding(16.dp)) } }
            ),
            AppTabPagerItems(
                title = "Star",
                icon = { AppIcon(imageVector = Icons.Filled.Star, contentDescription = "Star", modifier = Modifier.size(18.dp)) },
                contentScreens = { Surface(modifier = Modifier.fillMaxSize(), color = Color.White) { Text("Star Content", modifier = Modifier.padding(16.dp)) } }
            )
        )

        val mod:Modifier = Modifier.fillMaxSize().clip(
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ).background(Color.White)
        AppTabPager(
            tabs = tabItems,
            //icons = icons, // comment out this line to preview without icons
            //contentScreens = contentScreens,
            scrollableTab = true,
            pagerModifier = mod,
            tabContainerColor = Color.Transparent,
            tabContentColor = Color.Black,
            tabTextColor = Color.Gray,
            tabTextColorSelected = Color.Blue,
            indicatorColor = Color.DarkGray,
            indicatorWidth = 20.dp,
            indicatorShape = RoundedCornerShape(10.dp),
            thicknessIndicator = 2.dp,
            dividerColor = Color.LightGray,
            dividerThickness = 0.dp
        )

    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun AppTabPagerPreview2(){

    val tabItems = listOf(
        AppTabPagerItems(
            title = "Tab 11",
            contentScreens = { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = ("HELLO WORLD 1"), fontSize = 30.sp)
            } }
        ),
        AppTabPagerItems(
            title = "Tab 22",
            contentScreens = { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = ("HELLO WORLD 2"), fontSize = 30.sp)
            } }
        ),
        AppTabPagerItems(
            title = "Tab 33",
            contentScreens = { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = ("HELLO WORLD 3"), fontSize = 30.sp)
            } }
        ),
    )


    Box(Modifier.fillMaxSize()){
        AppTabPager(
            tabs = tabItems,
            tabContainerColor = Color.White,
            indicatorColor = Orange20,
            tabTextColor = Color.LightGray,
            tabTextColorSelected = Blue20,
            thicknessIndicator = 2.dp,
            dividerThickness = 0.5.dp,
        )
    }
}



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
    val indicatorWidth:Dp? = null ,
    val indicatorShape: Shape? = null,
    val thicknessIndicator: Dp? = null,
    val dividerModifier: Modifier? = null,
    val dividerColor: Color? = null,
    val dividerThickness: Dp? = null,
    val gradientColorsList: GradientColors? = null,
    val icon: @Composable (() -> Unit)? = null,
    val contentScreens: @Composable () -> Unit
)
