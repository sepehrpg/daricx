package com.example.database.dao

import androidx.room.*
import com.example.database.model.FavoriteCoinEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCoinDao {

    @Query("SELECT * FROM favorite_coins")
    fun getFavoriteCoins(): Flow<List<FavoriteCoinEntity>>

    @Query("SELECT id FROM favorite_coins")
    fun getFavoriteIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFavorite(coin: FavoriteCoinEntity)

    @Query("DELETE FROM favorite_coins WHERE id = :coinId")
    suspend fun deleteFavoriteById(coinId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_coins WHERE id = :coinId)")
    suspend fun isFavorite(coinId: String): Boolean
}
