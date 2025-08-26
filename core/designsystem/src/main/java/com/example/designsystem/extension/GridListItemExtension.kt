package com.example.designsystem.extension




/*fun LazyListScope.gridItems(
    count: Int,
    nColumns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    language:String="en",
    itemContent: @Composable BoxScope.(Int) -> Unit,
) {
    gridItems(
        data = List(count) { it },
        nColumns = nColumns,
        horizontalArrangement = horizontalArrangement,
        language=language,
        itemContent = itemContent,
    )
}*/



/*
fun <T> LazyListScope.gridItems(
    data: List<T>,
    nColumns: Int,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    language:String="en",
    key: ((item: T) -> Any)? = null,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val rows = if (data.isEmpty()) 0 else 1 + (data.count() - 1) / nColumns
    items(rows) { rowIndex ->
        CompositionLocalProvider(AppDirection.appDirectionLayoutProperty(language)) {
            Row(horizontalArrangement = horizontalArrangement) {
                for (columnIndex in 0 until nColumns) {
                    val itemIndex = rowIndex * nColumns + columnIndex
                    if (itemIndex < data.count()) {
                        val item = data[itemIndex]
                        androidx.compose.runtime.key(key?.invoke(item)) {
                            Box(
                                modifier = Modifier.weight(1f, fill = true),
                                propagateMinConstraints = true,
                            ) {
                                itemContent.invoke(this, item)
                            }
                        }
                    } else {
                        Spacer(Modifier.weight(1f, fill = true))
                    }
                }
            }
        }

    }
}*/
