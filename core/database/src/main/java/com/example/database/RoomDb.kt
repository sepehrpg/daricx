package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.FavoriteCoinDao
import com.example.database.model.FavoriteCoinEntity


@Database(
    entities = [FavoriteCoinEntity::class],
    version = 1,
    exportSchema = true
)
abstract class RoomDb : RoomDatabase() {
    abstract fun favoriteCoinDao(): FavoriteCoinDao
}


