
package com.example.database.di
import com.example.database.RoomDb
import com.example.database.dao.ProjectsManagementDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun providesTemplateDao(
        database: RoomDb,
    ): ProjectsManagementDao = database.projectManagementDao()
}
