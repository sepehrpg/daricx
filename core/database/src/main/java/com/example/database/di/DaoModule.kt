
package com.example.database.di
import com.example.database.RoomDb
import com.example.database.dao.FavoriteCoinDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun provideFavoriteCoinDao(db: RoomDb): FavoriteCoinDao = db.favoriteCoinDao()

}
