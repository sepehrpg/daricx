package com.example.database.di

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.database.dao.ProjectsManagementDao
import com.example.database.di.DatabaseModule.providesDatabase
import com.example.database.model.pm.templates_statuses.TemplatesEntity
import com.example.database.model.pm.templates_statuses.TemplatesStatusEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.Executors


fun createQueryCallback(): RoomDatabase.QueryCallback {
    return RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
        Timber.d( "SQL Query: $sqlQuery SQL Args: $bindArgs")
    }
}


fun createDatabaseCallback(context: Context): RoomDatabase.Callback {
    return object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Executors.newSingleThreadExecutor().execute {
                val projectDao: ProjectsManagementDao = providesDatabase(context).projectManagementDao()
                initializeProjectDao(projectDao)
            }
        }
    }
}


private fun initializeProjectDao(projectDao: ProjectsManagementDao){
    CoroutineScope(Dispatchers.IO).launch {
        Timber.d("initializeProjectDao")
        projectDao.insertTemplateWithStatuses(
            template = TemplatesEntity(name = "Kanban"),
            statuses = listOf(
                TemplatesStatusEntity(
                    order = 0,
                    name = "OPEN",
                    color = "#ffb6b7b8",
                ),
                TemplatesStatusEntity(
                    order = 1,
                    name = "IN PROGRESS",
                    color = "#ff518df5",
                ),
                TemplatesStatusEntity(
                    order = 2,
                    name = "REVIEW",
                    color = "#ff7751f5",
                ),
                TemplatesStatusEntity(
                    order = 3,
                    name = "DONE",
                    color = "#ff51f5b3",
                ),
                TemplatesStatusEntity(
                    order = 4,
                    name = "CLOSE",
                    color = "#ff48c722",
                ),
            )
        )
        projectDao.insertTemplateWithStatuses(
            template = TemplatesEntity(name = "Scrum"),
            statuses = listOf(
                TemplatesStatusEntity(
                    order = 0,
                    name = "OPEN",
                    color = "#ffa4a6a4",
                ),
                TemplatesStatusEntity(
                    order = 1,
                    name = "PENDING",
                    color = "#ffd1c028",
                ),
                TemplatesStatusEntity(
                    order = 2,
                    name = "IN PROGRESS",
                    color = "#ffcc2f88",
                ),
                TemplatesStatusEntity(
                    order = 3,
                    name = "COMPLETED",
                    color = "#ff3b383a",
                ),
                TemplatesStatusEntity(
                    order = 4,
                    name = "IN REVIEW",
                    color = "#ffde7728",
                ),
                TemplatesStatusEntity(
                    order = 5,
                    name = "ACCEPTED",
                    color = "#ffba3d1e",
                ),
                TemplatesStatusEntity(
                    order = 6,
                    name = "REJECTED",
                    color = "#ff8c19a6",
                ),
                TemplatesStatusEntity(
                    order = 7,
                    name = "BLOCKED",
                    color = "#ff5027b8",
                ),
                TemplatesStatusEntity(
                    order = 8,
                    name = "CLOSED",
                    color = "#ff48c722",
                ),
                //count = 9
            )
        )
    }
}
