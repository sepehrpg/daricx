package com.example.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 *
 * Collapsing header layout
 *
 * @param modifier
 * @param headerMaxHeight
 * @param headerMinHeight
 * @param header
 * @param body
 * @receiver
 * @receiver
 */
@Composable
fun CollapsingHeaderLayout(
    modifier: Modifier = Modifier,
    headerMaxHeight: Dp,
    headerMinHeight: Dp = 0.dp,
    header: @Composable (modifier: Modifier) -> Unit,
    body: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val maxHeightPx = with(density) { headerMaxHeight.toPx() }
    val minHeightPx = with(density) { headerMinHeight.toPx() }

    val headerHeightPx = remember { mutableStateOf(maxHeightPx) }

    val connection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                val oldHeight = headerHeightPx.value
                val newHeight = (oldHeight + delta).coerceIn(minHeightPx, maxHeightPx)
                headerHeightPx.value = newHeight
                val consumed = newHeight - oldHeight
                return Offset(0f, consumed)
            }
        }
    }

    val collapseFraction = if (maxHeightPx - minHeightPx == 0f) {
        1f
    } else {
        ((headerHeightPx.value - minHeightPx) / (maxHeightPx - minHeightPx)).coerceIn(0f, 1f)
    }

    val animatedAlpha by animateFloatAsState(
        targetValue = collapseFraction * collapseFraction * collapseFraction , // Power For More Speed
        label = "headerAlphaAnimation"
    )
    // ------------------------------------

    Column(
        modifier = modifier.nestedScroll(connection)
    ) {
        val currentHeightDp = with(density) { headerHeightPx.value.toDp() }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(currentHeightDp)
                .graphicsLayer { alpha = animatedAlpha }
        ) {
            header(Modifier)
        }

        body()
    }
}



@Preview(showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun CollapsingHeaderLayoutPreview() {
    CollapsingHeaderLayout(
        modifier = Modifier.fillMaxSize(),
        headerMaxHeight = 200.dp,
        headerMinHeight = 56.dp,
        header = { modifier ->
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.Cyan.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Header Content",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        body = {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(50) { index ->
                    Text(
                        text = "Scrollable item #${index + 1}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .padding(16.dp)
                    )
                }
            }
        }
    )
}