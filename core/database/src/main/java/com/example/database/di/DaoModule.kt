
package com.example.database.di
import com.example.database.RoomDb
import com.example.database.dao.FavoriteCoinDao
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single


@Module
class DaoModule {

    @Single
    fun provideFavoriteCoinDao(
        db: RoomDb,
    ): FavoriteCoinDao = db.favoriteCoinDao()
}
