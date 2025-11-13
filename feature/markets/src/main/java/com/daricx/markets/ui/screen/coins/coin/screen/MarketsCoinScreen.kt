package com.daricx.markets.ui.screen.coins.coin.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.designsystem.component.text.AppText
import com.example.designsystem.theme.AppThemedPreview
import com.example.designsystem.theme.ThemePreviews

@Composable
fun MarketsCoinScreen(){
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        AppText("Markets Coin Screen")
    }
}


@ThemePreviews
@Composable
private fun MarketsCoinScreenPreview(){
    AppThemedPreview {
        MarketsCoinScreen()
    }
}