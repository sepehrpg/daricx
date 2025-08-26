package com.example.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.theme.PrimaryColor

@Composable
fun AppBottomSheetDragHandle(
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    title: String,
    cancel: String = "Cancel",
    done: String = "Done",
    content: @Composable (() -> Unit)? = null
) {
    Column(
        Modifier
            .fillMaxWidth().padding(top = 20.dp, bottom = 0.dp)
            .background(Color.White)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
            AppText(cancel, color = Color.Gray, fontSize = 14.sp, modifier = Modifier.clickableWithNoRipple {
                onCancelClick()
            })
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                AppText(
                    title,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
            AppText(done, color = PrimaryColor, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickableWithNoRipple {
                onDoneClick()
            })
        }

        if(content!=null){
            content()
        }
        else{
            Spacer(Modifier.height(10.dp))
            HorizontalDivider(color = Color(0xFFEEEEEE))
            Spacer(Modifier.height(10.dp))
        }

    }
}