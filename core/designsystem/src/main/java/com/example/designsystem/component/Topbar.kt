package com.example.designsystem.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.R
import kotlin.math.roundToInt
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.onGloballyPositioned



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    divider:Boolean = false,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit =
        {
            IconButton(
                onClick = { /* TODO */ },
                modifier = Modifier.width(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.core_designsystem_archive),
                    contentDescription = "Diamond",
                    tint = Color.Gray,
                    modifier = Modifier.size(22.dp)
                )
            }
            IconButton(
                onClick = { /* TODO */ },
                modifier = Modifier.width(40.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.core_designsystem_search),
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(22.dp)
                )
            }
            IconButton(
                onClick = { /* TODO */ },
                modifier = Modifier.width(45.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.core_designsystem_profile),
                    contentDescription = "Notifications",
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
    moreComponent : @Composable (() -> Unit)? = null,
) {


    Column(modifier = modifier) {
        TopAppBar(
            title = { Text(title, color = Color.DarkGray, fontWeight = FontWeight.Bold) },
            actions = actions,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                scrolledContainerColor = Color.White
            ),
            scrollBehavior = scrollBehavior
        )


        if (moreComponent!=null){
            moreComponent.invoke()
        }

        if(divider){
            HorizontalDivider(
                color = Color.LightGray.copy(alpha = 0.5f),
                thickness = 1.dp
            )
        }

    }
}







@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun AppTopBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.White)
            .border(1.dp, Color.LightGray)
    ) {
        AppTopBar(title = "App Title")
    }
}







@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    baseBarHeight: Dp = 56.dp,               // ارتفاع حالت Collapsed
    containerColor: Color = Color.White,
    divider: Boolean = true,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    moreComponent: (@Composable () -> Unit)? = null, // چیزی که می‌خواهی زیر نوار بیاید و با اسکرول جمع شود
) {
    val density = LocalDensity.current
    val baseBarHeightPx = with(density) { baseBarHeight.roundToPx() }
    var extraHeightPx by remember { mutableStateOf(0) } // ارتفاع moreComponent

    // حدّ جمع‌شدن را بر اساس ارتفاع محتوای اضافه تعیین کن
    SideEffect {
        scrollBehavior?.state?.heightOffsetLimit = -extraHeightPx.toFloat()
    }

    // مقدار لحظه‌ای جابجایی ارتفاع (توسط nestedScroll پر می‌شود)
    val heightOffset = scrollBehavior?.state?.heightOffset ?: 0f
    val collapseRangePx = extraHeightPx
    val currentHeightPx = (baseBarHeightPx + extraHeightPx + heightOffset).coerceAtLeast(baseBarHeightPx.toFloat())
    val currentHeightDp = with(density) { currentHeightPx.toDp() }

    // نسبت جمع‌شدن برای افکت‌های بصری (0..1)
    val fraction = if (collapseRangePx == 0) 0f else (-heightOffset / collapseRangePx).coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(currentHeightDp)
            .clipToBounds() // بخش اضافه هنگام جمع‌شدن کلیپ شود
            .background(containerColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            // ردیف اصلی نوار (ارتفاع ثابت)
            Row(
                modifier = Modifier
                    .height(baseBarHeight)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (navigationIcon != null) {
                    Box(Modifier.padding(end = 8.dp)) { navigationIcon() }
                }

                Text(
                    text = title,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )

                Row(content = actions)
            }

            // محتوای اضافه که با اسکرول جمع می‌شود
            if (moreComponent != null) {
                // ثبت ارتفاع واقعیِ moreComponent برای تعیین collapse range
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coords ->
                            val h = coords.size.height
                            if (h != extraHeightPx) extraHeightPx = h
                        }
                        // افکت اختیاری: محو شدن تدریجی با جمع‌شدن
                        .graphicsLayer { alpha = 1f - fraction }
                ) {
                    moreComponent()
                }
            }
        }

        // دیوایدر پایین نوار
        if (divider) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.BottomCenter),
                color = Color.LightGray.copy(alpha = 0.5f),
                thickness = 1.dp
            )
        }
    }
}
