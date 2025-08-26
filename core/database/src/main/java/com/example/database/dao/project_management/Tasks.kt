package com.example.database.dao.project_management

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.database.model.pm.project.relationships.ProjectWithTemplateAndStatuses
import com.example.database.model.pm.task.TasksEntity
import kotlinx.coroutines.flow.Flow

interface Tasks {

    //Insert
    //..............................................................................................
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(tasksEntity: TasksEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<TasksEntity>)
    //..............................................................................................


    //Upsert
    //..............................................................................................
    @Upsert
    suspend fun insertOrReplaceTask(tasksEntity: TasksEntity)
    //..............................................................................................


    //Update
    //..............................................................................................
    @Update
    suspend fun updateTask(project: TasksEntity)
    //..............................................................................................


    //Select
    //..............................................................................................
    @Query("SELECT * FROM tasks")
    fun tasksList(): Flow<List<TasksEntity>>

    @Query("SELECT * FROM tasks WHERE template_status_id = :templateStatusId or project_id = :projectId")
    fun tasksList(templateStatusId:Int?,projectId:Int?): Flow<List<TasksEntity>>
    //..............................................................................................


    //Delete
    //..............................................................................................
    @Delete
    fun deleteTask(project: TasksEntity)
    //..............................................................................................


    //Other
    //..............................................................................................
    @Query("SELECT count(*) FROM tasks")
    fun countTasks(): Flow<Int>
    //..............................................................................................

}