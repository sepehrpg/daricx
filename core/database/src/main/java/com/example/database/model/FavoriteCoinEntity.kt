package com.example.database.model



import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_coins")
data class FavoriteCoinEntity(
    @PrimaryKey
    val id: String,
    val symbol: String?,
    val name: String?,
    val imageUrl: String?,
)











