package com.example.database.dao.project_management

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.project.relationships.ProjectWithTemplateAndStatuses
import kotlinx.coroutines.flow.Flow

@Dao
interface Projects {

    //Insert
    //..............................................................................................
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProject(project: ProjectsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProjects(projects: List<ProjectsEntity>)
    //..............................................................................................


    //Upsert
    //..............................................................................................
    @Upsert
    suspend fun insertOrReplaceProject(project: ProjectsEntity)
    //..............................................................................................


    //Update
    //..............................................................................................
    @Update
    suspend fun updateProject(project: ProjectsEntity)
    //..............................................................................................


    //Select
    //..............................................................................................
    @Query("SELECT * FROM projects")
    fun projectsList(): Flow<List<ProjectsEntity>>
    //..............................................................................................


    //Delete
    //..............................................................................................
    @Delete
    fun deleteProject(project: ProjectsEntity)
    //..............................................................................................


    //Other
    //..............................................................................................
    @Query("SELECT count(*) FROM projects")
    fun countProjects(): Flow<Int>
    //..............................................................................................



    //Select Transactions
    //..............................................................................................
    @Transaction
    @Query("SELECT * FROM projects WHERE id = :projectId")
    fun getProjectWithTemplateAndStatuses(projectId: Int): Flow<ProjectWithTemplateAndStatuses?>
    //..............................................................................................


}