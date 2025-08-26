package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.TargetedFlingBehavior
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.theme.PrimaryColor


@Composable
fun AppHorizontalPagerPrimary(
    state: PagerState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    pageSize: PageSize = PageSize.Fill,
    beyondViewportPageCount: Int = PagerDefaults.BeyondViewportPageCount,
    pageSpacing: Dp = 0.dp,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    flingBehavior: TargetedFlingBehavior = PagerDefaults.flingBehavior(state = state),
    userScrollEnabled: Boolean = true,
    reverseLayout: Boolean = false,
    key: ((index: Int) -> Any)? = null,
    pageNestedScrollConnection: NestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
        state,
        Orientation.Horizontal
    ),
    snapPosition: SnapPosition = SnapPosition.Start,
    pageContent: @Composable PagerScope.(page: Int) -> Unit
) {
    HorizontalPager(
        state = state,
        modifier = modifier,
        contentPadding = contentPadding,
        pageSize = pageSize,
        beyondViewportPageCount = beyondViewportPageCount,
        pageSpacing = pageSpacing,
        verticalAlignment = verticalAlignment,
        flingBehavior = flingBehavior,
        userScrollEnabled = userScrollEnabled,
        reverseLayout = reverseLayout,
        key = key,
        pageNestedScrollConnection = pageNestedScrollConnection,
        snapPosition = snapPosition,
        pageContent = pageContent,
    )


}


@Composable
fun AppPagerIndicatorPrimary(
    state: PagerState,
    parentModifier : Modifier = Modifier
        .clip(RoundedCornerShape(20.dp))
        .border(1.dp, color = Color.LightGray, shape = RoundedCornerShape(20.dp))
        .background(Color.White)
        .wrapContentHeight()
        .padding(bottom = 8.dp, top = 8.dp, start = 7.dp, end = 7.dp),
    childModifier: Modifier = Modifier
        .padding(vertical = 2.dp, horizontal = 2.dp)
        .clip(CircleShape)
        .size(5.dp),
    selectedColor: Color = Color(0xFF673AB7),
    unSelectedColor: Color = Color.LightGray,
){
    Row(
        parentModifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(state.pageCount) { iteration ->
            val color = if (state.currentPage == iteration) selectedColor else unSelectedColor
            Box(
                modifier = childModifier.background(color)
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewAppHorizontalPagerPrimary() {
    val pagerState = rememberPagerState(pageCount = { 5 }) // تعداد صفحات
    AppHorizontalPagerPrimary(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(end = 60.dp),
        pageSpacing = 5.dp,
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (page % 2 == 0) Color.Blue else Color.Green
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Page $page",
                color = Color.White,
                fontSize = 24.sp
            )
        }
    }
    Box(Modifier.fillMaxSize().padding(vertical = 10.dp), contentAlignment = Alignment.BottomCenter){
        AppPagerIndicatorPrimary(pagerState)
    }
}





@Composable
fun PlayGroundPager() {
    val pagerState = rememberPagerState(pageCount = {
        10
    })
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(end = 60.dp),
    ) { page ->
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(if (page % 2 == 0) Color.Cyan else Color.Magenta),
            contentAlignment = Alignment.TopStart
        ) {
            Text(
                text = "Page $page",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlayGroundPagerPreview() {
    PlayGroundPager()
}


















/**
 * Example :
 * HorizontalPager(
 *         state = pagerState,
 *         pageSize = threePagesPerViewport,
 *         contentPadding = PaddingValues(horizontal = 2.dp),
 *         pageSpacing = 2.dp
 *     )
 */
private val threePagesPerViewport = object : PageSize {
    override fun Density.calculateMainAxisPageSize(
        availableSpace: Int,
        pageSpacing: Int
    ): Int {
        return (availableSpace - 2 * pageSpacing) / 3 //ex: if you want 4 per page : change 2 with 3 and 3 with 4
    }
}
