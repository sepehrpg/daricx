package com.example.designsystem.component.tabs


import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

/**
 * A single tab used inside a [AppTabRow], [AppPrimaryTabRow], or [AppSecondaryTabRow].
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


@Preview
@Composable
fun AppTabPreview() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    Column(Modifier.padding(16.dp)) {
        AppTab(
            selected = selectedTabIndex == 0,
            onClick = { selectedTabIndex = 1 },
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
private fun AppTabPreview2() {
    var selected by remember { mutableStateOf(false) }
    AppTab(
        selected = selected,
        onClick = { selected = !selected }
    ) {
        Text("Tab", Modifier.padding(8.dp))
    }
}







