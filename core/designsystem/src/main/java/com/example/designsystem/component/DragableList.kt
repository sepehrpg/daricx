package com.example.designsystem.component

import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.channels.Channel






@Composable
fun rememberDragDropState(
    lazyListState: LazyListState,
    onMove: (Int, Int) -> Unit,
    draggableItemsNum: Int
): DragDropState {
    val state =
        remember(lazyListState) {
            DragDropState(
                draggableItemsNum = draggableItemsNum,
                stateList = lazyListState,
                onMove = onMove,
            )
        }
    LaunchedEffect(state) {
        while (true) {
            val diff = state.scrollChannel.receive()
            lazyListState.scrollBy(diff)
        }
    }
    return state
}


fun Modifier.dragContainer(dragDropState: DragDropState): Modifier {
    return this.then(pointerInput(dragDropState) {
        detectDragGesturesAfterLongPress(
            onDrag = { change, offset ->
                change.consume()
                dragDropState.onDrag(offset = offset)
            },
            onDragStart = { offset -> dragDropState.onDragStart(offset) },
            onDragEnd = { dragDropState.onDragInterrupted() },
            onDragCancel = { dragDropState.onDragInterrupted() }
        )
    }
    )
}


inline fun <T : Any> LazyListScope.draggableItems(
    items: List<T>,
    dragDropState: DragDropState,
    crossinline content: @Composable (Modifier, T,Int) -> Unit,
) {
    itemsIndexed(
        items = items,
        contentType = { index, _ -> DraggableItem(index = index) })
    { index, item ->
        val modifier = if (dragDropState.draggingItemIndex == index) {
            Modifier
                .zIndex(1f)
                .graphicsLayer {
                    translationY = dragDropState.delta
                }
        } else {
            Modifier
        }
        content(modifier, item,index)
    }
}



class DragDropState(
    private val draggableItemsNum: Int,
    private val stateList: LazyListState,
    private val onMove: (Int, Int) -> Unit,
) {
    var draggingItemIndex: Int? by mutableStateOf(null)

    var delta by mutableFloatStateOf(0f)

    val scrollChannel = Channel<Float>()

    private var draggingItem: LazyListItemInfo? = null

    internal fun onDragStart(offset: Offset) {
        stateList.layoutInfo.visibleItemsInfo
            .firstOrNull { item -> offset.y.toInt() in item.offset..(item.offset + item.size) }
            ?.also {
                (it.contentType as? DraggableItem)?.let { draggableItem ->
                    draggingItem = it
                    draggingItemIndex = draggableItem.index
                }
            }
    }

    internal fun onDragInterrupted() {
        draggingItem = null
        draggingItemIndex = null
        delta = 0f
    }

    internal fun onDrag(offset: Offset) {
        delta += offset.y

        val currentDraggingItemIndex =
            draggingItemIndex ?: return
        val currentDraggingItem =
            draggingItem ?: return

        val startOffset = currentDraggingItem.offset + delta
        val endOffset =
            currentDraggingItem.offset + currentDraggingItem.size + delta
        val middleOffset = startOffset + (endOffset - startOffset) / 2

        val targetItem =
            stateList.layoutInfo.visibleItemsInfo.find { item ->
                middleOffset.toInt() in item.offset..item.offset + item.size &&
                        currentDraggingItem.index != item.index &&
                        item.contentType is DraggableItem
            }

        if (targetItem != null) {
            val targetIndex = (targetItem.contentType as DraggableItem).index
            onMove(currentDraggingItemIndex, targetIndex)
            draggingItemIndex = targetIndex
            delta += currentDraggingItem.offset - targetItem.offset
            draggingItem = targetItem
        } else {
            val startOffsetToTop = startOffset - stateList.layoutInfo.viewportStartOffset
            val endOffsetToBottom = endOffset - stateList.layoutInfo.viewportEndOffset
            val scroll =
                when {
                    startOffsetToTop < 0 -> startOffsetToTop.coerceAtMost(0f)
                    endOffsetToBottom > 0 -> endOffsetToBottom.coerceAtLeast(0f)
                    else -> 0f
                }

            if (scroll != 0f && currentDraggingItemIndex != 0 && currentDraggingItemIndex != draggableItemsNum - 1) {
                scrollChannel.trySend(scroll)
            }
        }
    }
}

data class DraggableItem(val index: Int)









//Example
//..................................................................................................
@Composable
fun MyList() {
    var list1 by remember { mutableStateOf(List(20) { it }) }
    val list2 by remember { mutableStateOf(List(20) { it + 20 }) }
    val draggableItems by remember {
        derivedStateOf { list1.size }
    }
    val stateList = rememberLazyListState()

    val dragDropState =
        rememberDragDropState(
            lazyListState = stateList,
            draggableItemsNum = draggableItems,
            onMove = { fromIndex, toIndex ->
                list1 = list1.toMutableList().apply { add(toIndex, removeAt(fromIndex))}
            })

    LazyColumn(
        modifier = Modifier.dragContainer(dragDropState),
        state = stateList,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(text = "Title 1", fontSize = 30.sp)
        }

        draggableItems(items = list1, dragDropState = dragDropState) { modifier, item , index ->
            Item(
                modifier = modifier,
                index = item,
            )
        }

        item {
            Text(text = "Title 2", fontSize = 30.sp)
        }

        itemsIndexed(list2, key = { _, item -> item }) { _, item ->
            Item(index = item)
        }

    }
}


@Composable
private fun Item(modifier: Modifier = Modifier, index: Int) {
    Card(
        modifier = modifier
    ) {
        Text(
            "Item $index",
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview(){
    MyList()
}
//..................................................................................................
