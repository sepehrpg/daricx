package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.RoomDb
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single



@Module
class RoomDatabaseModule {

    @Single
    fun provideRoomDb(
        context: Context,
    ): RoomDb = Room.databaseBuilder(
        context,
        RoomDb::class.java,
        "app_database",
    ).build()
}



