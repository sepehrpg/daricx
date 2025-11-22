package com.example.model.coins.mapper

import com.example.model.coins.Coins
import com.example.model.coins.FavoriteCoin



fun Coins.toFavoriteCoin(): FavoriteCoin {
    return FavoriteCoin(
        id = id?:"",
        symbol = symbol,
        name = name,
        imageUrl = image,
    )
}
