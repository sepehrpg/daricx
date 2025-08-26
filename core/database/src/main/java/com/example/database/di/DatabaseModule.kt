package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.DatabaseMigrations.migration6to7
import com.example.database.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): RoomDb = Room.databaseBuilder(
        context,
        RoomDb::class.java,
        "app_db",
    ).addCallback(createDatabaseCallback(context))
     //.fallbackToDestructiveMigration()
     .setQueryCallback(createQueryCallback(), Executors.newSingleThreadExecutor())
        .addMigrations(migration6to7)
     .build()
}




