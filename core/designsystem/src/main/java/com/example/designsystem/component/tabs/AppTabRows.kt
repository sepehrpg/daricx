package com.example.designsystem.component.tabs


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.component.AppHorizontalDivider
import kotlinx.coroutines.launch

/**
 * A standard tab row container.
 */
@Composable
fun AppTabRow(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    containerColor: Color = TabRowDefaults.primaryContainerColor,
    contentColor: Color = TabRowDefaults.primaryContentColor,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = {
        if (selectedTabIndex < it.size) {
            TabRowDefaults.SecondaryIndicator(
                Modifier.tabIndicatorOffset(it[selectedTabIndex])
            )
        }
    },
    divider: @Composable () -> Unit = { AppHorizontalDivider() },
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
    divider: @Composable () -> Unit = @Composable { AppHorizontalDivider() },
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
    divider: @Composable () -> Unit = @Composable { AppHorizontalDivider() },
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



