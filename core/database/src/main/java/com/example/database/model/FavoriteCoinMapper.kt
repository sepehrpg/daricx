package com.example.database.model
import com.example.model.coins.FavoriteCoin


fun FavoriteCoin.toFavoriteEntity(): FavoriteCoinEntity {
    return FavoriteCoinEntity(
        id = id,
        symbol = symbol,
        name = name,
        imageUrl = imageUrl,
    )
}