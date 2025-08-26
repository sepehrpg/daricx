package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    divider:Boolean = false,
    modifier: Modifier = Modifier,
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
) {
    Column(modifier = modifier) {
        TopAppBar(
            title = { Text(title, color = Color.DarkGray, fontWeight = FontWeight.Bold) },
            actions = actions,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                scrolledContainerColor = Color.White
            )
        )

        if(divider){
            HorizontalDivider(
                color = Color.LightGray.copy(alpha = 0.5f),
                thickness = 1.dp
            )
        }

    }
}



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