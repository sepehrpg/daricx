package com.example.database.di

import org.koin.core.annotation.Module



@Module(
    includes = [
        RoomDatabaseModule::class,
        DaoModule::class,
    ]
)
class DatabaseCompositionModule