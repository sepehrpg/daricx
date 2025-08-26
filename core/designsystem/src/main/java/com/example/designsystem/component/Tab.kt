package com.example.designsystem.component

import android.util.Log
import android.widget.Space
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabIndicatorScope
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.icon.AppIcons
import com.example.designsystem.theme.ClickUpGray2
import com.example.designsystem.theme.ClickUpGray3
import com.example.designsystem.theme.GradientColor1
import kotlinx.coroutines.launch
/**
 *Guide Tabs::
 *
 * Tab Component::
 * Usage: A Tab is a single tab in a tab row, which typically has a label or content to represent a particular section.
 * Components: It can be a part of either TabRow, PrimaryTabRow, SecondaryTabRow, or any other tab container.
 * Key Features::
 * selected: A boolean flag that indicates if the tab is currently selected.
 * onClick: A callback function that defines what happens when the tab is clicked.
 * text: A composable to display the tab label (usually Text).
 * Can include an icon by using icon.
 *
 * TabRow Component::
 * Usage: TabRow is the container that holds the Tab components. It lays out the tabs horizontally and manages the selection indicator.
 * Key Features::
 * It can be used with Tab to create basic tab navigation.
 * You can customize the indicator and divider.
 * Contains the state for the selected tab and handles updating it when a tab is clicked.
 *
 * PrimaryTabRow Component::
 * Usage: A specialized version of TabRow that is designed for primary tabs. These are typically used as the main tabs at the top of the screen for major sections of your app.
 * Key Features::
 * Provides a more prominent visual style with a higher emphasis on the selected tab.
 * Often used in layouts where the tabs represent primary sections or categories within the app.
 * By default, it has a more pronounced indicator and content color, giving it a more "important" visual hierarchy than TabRow
 *
 * SecondaryTabRow Component::
 * Usage: A version of TabRow for secondary or less emphasized tabs. These are used when you need a secondary layer of tabbed navigation.
 * Key Features::
 * Typically used in contexts where the tabs are less critical or meant for additional content that doesn't require primary focus.
 * Has a more subtle appearance compared to PrimaryTabRow, including a less pronounced tab indicator.
 * It’s good for organizing content that doesn’t need as much visual emphasis.
 *
 * LeadingIconTab Component::
 * Usage: A type of Tab that includes a leading icon alongside the text. This is useful for adding an icon before the tab label, offering more visual context to the user.
 * Key Features::
 * Contains an icon parameter that places the icon before the text in the tab.
 * Great for tabs where you want to visually represent the tab's content (e.g., icons for different categories).
 *
 *
 * Key Differences::
 * Tab: Represents a single tab, which can be customized with text, icons, and a selection state. It's used inside a TabRow or any tab container.
 * TabRow: The container that holds all the Tab components. It manages the layout and selection state of the tabs.
 * PrimaryTabRow: A more emphasized version of TabRow, typically used for main sections of the app that need prominent tab navigation.
 * SecondaryTabRow: A subtler version of TabRow, used for secondary tabs that are less visually emphasized.
 * LeadingIconTab: A version of Tab with a leading icon, useful when you need to visually represent the tab with both text and an icon.
 *
 * When to Use Each:
 * Tab: When you want to define an individual tab.
 * TabRow: When you need a row to organize multiple Tab components.
 * PrimaryTabRow: Use for tabs that represent the main sections of your app or where you want a more prominent style.
 * SecondaryTabRow: Use for secondary sections or when you want a less emphasized tab style.
 * LeadingIconTab: When you need a tab with an icon and text, providing a more visually rich tab component.
 */





@Composable
fun AppTab(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit
) {
    Tab(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
        interactionSource = interactionSource,
        content = content
    )
}


@Composable
fun AppTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.primaryContainerColor,
    contentColor: Color = TabRowDefaults.primaryContentColor,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit =
        @Composable { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                )
            }
        },
    divider: @Composable () -> Unit = @Composable { HorizontalDivider() },
    tabs: @Composable () -> Unit
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        indicator = indicator,
        divider = divider,
        tabs = tabs
    )
}



@ExperimentalMaterial3Api
@Composable
fun AppPrimaryTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.primaryContainerColor,
    contentColor: Color =  TabRowDefaults.primaryContentColor,
    indicator: @Composable TabIndicatorScope.() -> Unit = {
        TabRowDefaults.PrimaryIndicator(
            modifier = Modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = true),
            width = Dp.Unspecified,
        )
    },
    divider: @Composable () -> Unit = @Composable { HorizontalDivider() },
    tabs: @Composable () -> Unit
) {
    PrimaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        indicator = indicator,
        divider = divider,
        tabs = tabs
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSecondaryTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.secondaryContainerColor,
    contentColor: Color = TabRowDefaults.secondaryContentColor,
    indicator: @Composable TabIndicatorScope.() -> Unit =
        @Composable {
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(selectedTabIndex, matchContentSize = false)
            )
        },
    divider: @Composable () -> Unit = @Composable { HorizontalDivider() },
    tabs: @Composable () -> Unit
){
    SecondaryTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier,
        containerColor = containerColor ,
        contentColor = contentColor,
        indicator = indicator,
        divider = divider,
        tabs = tabs
    )
}



@Composable
fun AppLeadingIconTab(
    selected: Boolean,
    onClick: () -> Unit,
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor,
    interactionSource: MutableInteractionSource? = null
) {
    LeadingIconTab(
        selected = selected,
        onClick = onClick,
        text = text,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
        interactionSource = interactionSource
    )
}





@Preview
@Composable
fun AppTabPreview() {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(Modifier.padding(16.dp)) {
        AppTab(
            selected = selectedTabIndex == 0,
            onClick = { selectedTabIndex=1 },
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.Gray
        ) {
            Text(text = "TAB 0", Modifier.padding(8.dp))
        }

        AppTab(
            selected = selectedTabIndex == 1,
            onClick = { selectedTabIndex = 0 },
            selectedContentColor = Color.Red,
            unselectedContentColor = Color.Gray
        ) {
            Text(text = "TAB 1", Modifier.padding(8.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AppTabRowPreview() {

    var selectedTab by remember { mutableStateOf(0) }
    Column(modifier = Modifier
        .height(100.dp)
        .padding(16.dp)) {
        AppTabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Tab 1")
            }

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Tab 2")
            }

            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Tab 3")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Selected Tab: ${selectedTab + 1}", style = MaterialTheme.typography.bodyLarge)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AppPrimaryTabRowPreview() {
    var selectedTab by remember { mutableStateOf(0) }

    Column(modifier = Modifier
        .height(100.dp)
        .padding(16.dp)) {
        AppPrimaryTabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Tab 1")
            }

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Tab 2")
            }

            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Tab 3")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Selected Tab: ${selectedTab + 1}", style = MaterialTheme.typography.bodyLarge)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AppSecondaryTabRowPreview(){
    val titles = listOf("Tab 1", "Tab 2", "Tab 3 with lots of text")
    var state by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxWidth()) {
        AppSecondaryTabRow(
            selectedTabIndex = 0,
            tabs = {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = state == index,
                        onClick = { state = index },
                        text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) }
                    )
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AppLeadingIconTabPreview() {
    Row(Modifier.fillMaxWidth().height(100.dp)){
        var index by remember { mutableStateOf(0) }
        repeat(3){
            AppLeadingIconTab(
                selected = index==it,
                onClick = { index=it },
                text = { Text("Tab Item") },
                modifier = Modifier.weight(1f),
                icon = {
                    AppIcon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home Icon"
                    )
                },
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray
            )
        }
    }
}



data class AppCustomLeadingIconTabItem(
    val id:Int,
    val text: @Composable () -> Unit,
    val icon: @Composable () -> Unit,
    val description: String? = null,
    var selected: Boolean = false,
    val modifier: Modifier? = null,
    val enabled: Boolean? = null,
    val additionalUi: Boolean = false,
    val selectedContentColor: Color? = null,
    val unselectedContentColor: Color? = null,
    val interactionSource: MutableInteractionSource? = null
)

@Composable
fun AppCustomLeadingIconTab(
    item:List<AppCustomLeadingIconTabItem>,
    onClick: (index:Int) -> Unit,
    sharedClick: () -> Unit,
    modifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier.fillMaxWidth()
        .shadow(0.dp, RoundedCornerShape(0.dp)).clip(RoundedCornerShape(10.dp)).background(ClickUpGray2),
    rowModifier: Modifier = Modifier.fillMaxWidth(),
    selectedModifier:Modifier = Modifier.fillMaxWidth().shadow(0.dp, RoundedCornerShape(0.dp)).clip(RoundedCornerShape(10.dp))
        .background(Color.White).clip(RoundedCornerShape(12.dp)),
    enabled: Boolean = true,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor,
    interactionSource: MutableInteractionSource? = null
) {
    var selected :Int? by remember { mutableStateOf(null) }

    Box(boxModifier){
        Column(Modifier.fillMaxWidth()){
            Row(rowModifier.height(52.dp)){
                repeat(item.size){ index->
                    if (item[index].selected) selected = index
                    LeadingIconTab(
                        selected = item[index].selected,
                        onClick = {
                            onClick(index)
                            selected = index
                        },
                        text = item[index].text,
                        icon = item[index].icon,
                        modifier = Modifier.weight(1f).padding(horizontal = 5.dp, vertical = 5.dp)
                            .then(if (item[index].selected) selectedModifier else item[index].modifier?:modifier ),
                        enabled = item[index].enabled?:enabled,
                        selectedContentColor = item[index].selectedContentColor?:selectedContentColor,
                        unselectedContentColor = item[index].unselectedContentColor?:unselectedContentColor,
                        interactionSource = item[index].interactionSource?:interactionSource
                    )
                }
            }

            if (selected!=null){
                if (item[selected!!].selected && !item[selected!!].description.isNullOrEmpty()){
                    HorizontalDivider(thickness = 1.dp, color = Color(0xFFEEEEEE))

                    if(item[selected!!].additionalUi){
                        Spacer(Modifier.height(10.dp))
                        Row(Modifier.fillMaxWidth().clickableWithNoRipple {
                            sharedClick()
                        }.padding(horizontal = 14.dp, vertical = 5.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                            AppText("Share With", color = Color.DarkGray, fontSize = 13.sp)
                            AppIcon(AppIcons.ArrowRight, contentDescription = "", tint = Color.Gray)
                        }
                        Spacer(Modifier.height(2.dp))
                    }

                    Box(Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 5.dp).background(ClickUpGray3, shape = RoundedCornerShape(10.dp))){
                        AppText(item[selected!!].description.toString(), fontSize = 13.sp, modifier = Modifier.padding(10.dp))
                    }
                }
            }

        }
    }
}


@Preview
@Composable
fun AppCustomLeadingIconTabPreview(){
    val list = listOf(
        AppCustomLeadingIconTabItem(id = 1,text = {Text("Company", fontSize = 11.sp, fontWeight = FontWeight.Bold)},
            icon = { AppIcon(modifier = Modifier.size(20.dp), imageVector = Icons.Default.Groups, contentDescription = "Home Icon") },
            selected = true
        ),
        AppCustomLeadingIconTabItem(id = 2,text ={Text("Private", fontSize = 10.sp, fontWeight = FontWeight.Bold)},
            icon = { AppIcon(modifier = Modifier.size(20.dp),imageVector = Icons.Default.Group, contentDescription = "Home Icon") } ),
        AppCustomLeadingIconTabItem(id = 3,text ={Text("Personal", fontSize = 10.sp, fontWeight = FontWeight.Bold)},
            icon = { AppIcon(modifier = Modifier.size(20.dp),imageVector = Icons.Default.Person, contentDescription = "Home Icon") } ,
            selected = true),
    )
    Box(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp)){
        AppCustomLeadingIconTab(
            item = list,
            selectedContentColor = GradientColor1.bottom,
            unselectedContentColor = Color.Gray,
            onClick = {

            },
            sharedClick = {

            }
        )
    }
}


















@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabIndicatorScope.AnimatedIndicatorWithModifier(index: Int=0) {
    val colors =
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.tertiary,
        )
    var startAnimatable by remember { mutableStateOf<Animatable<Dp, AnimationVector1D>?>(null) }
    var endAnimatable by remember { mutableStateOf<Animatable<Dp, AnimationVector1D>?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val indicatorColor: Color by animateColorAsState(colors[index % colors.size], label = "")

    Box(
        Modifier
            .tabIndicatorLayout { measurable: Measurable,
                                  constraints: Constraints,
                                  tabPositions: List<TabPosition> ->
                val newStart = tabPositions[index].left
                val newEnd = tabPositions[index].right
                val startAnim =
                    startAnimatable
                        ?: Animatable(newStart, Dp.VectorConverter).also { startAnimatable = it }

                val endAnim =
                    endAnimatable
                        ?: Animatable(newEnd, Dp.VectorConverter).also { endAnimatable = it }

                if (endAnim.targetValue != newEnd) {
                    coroutineScope.launch {
                        endAnim.animateTo(
                            newEnd,
                            animationSpec =
                            if (endAnim.targetValue < newEnd) {
                                spring(dampingRatio = 1f, stiffness = 1000f)
                            } else {
                                spring(dampingRatio = 1f, stiffness = 50f)
                            }
                        )
                    }
                }

                if (startAnim.targetValue != newStart) {
                    coroutineScope.launch {
                        startAnim.animateTo(
                            newStart,
                            animationSpec =
                            // Handle directionality here, if we are moving to the right, we
                            // want the right side of the indicator to move faster, if we are
                            // moving to the left, we want the left side to move faster.
                            if (startAnim.targetValue < newStart) {
                                spring(dampingRatio = 1f, stiffness = 50f)
                            } else {
                                spring(dampingRatio = 1f, stiffness = 1000f)
                            }
                        )
                    }
                }

                val indicatorEnd = endAnim.value.roundToPx()
                val indicatorStart = startAnim.value.roundToPx()

                // Apply an offset from the start to correctly position the indicator around the tab
                val placeable =
                    measurable.measure(
                        constraints.copy(
                            maxWidth = indicatorEnd - indicatorStart,
                            minWidth = indicatorEnd - indicatorStart,
                        )
                    )
                layout(constraints.maxWidth, constraints.maxHeight) {
                    placeable.place(indicatorStart, 0)
                }
            }
            .padding(5.dp)
            .fillMaxSize()
            .drawWithContent {
                drawRoundRect(
                    color = indicatorColor,
                    cornerRadius = CornerRadius(5.dp.toPx()),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
    )
}

@Preview(showBackground = true)
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AnimationIndicatorContainerTabsPreivew() {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Tab 1", "Tab 2", "Tab 3")

    Column {
        AppSecondaryTabRow(
            selectedTabIndex = state,
            indicator = { AnimatedIndicatorWithModifier(state) }
        ) {
            titles.forEachIndexed { index, title ->
                Tab(selected = state == index, onClick = { state = index }, text = { Text(title) })
            }
        }
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "Fancy transition tab ${state + 1} selected",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}



