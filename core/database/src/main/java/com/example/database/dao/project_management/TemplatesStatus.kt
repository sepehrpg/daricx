package com.example.database.dao.project_management

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.database.model.pm.templates_statuses.TemplatesStatusEntity
import kotlinx.coroutines.flow.Flow

interface TemplatesStatus {

    //Insert
    //..............................................................................................
    @Insert
    suspend fun insertTemplateStatus(templatesStatus: TemplatesStatusEntity)

    @Insert
    suspend fun insertTemplatesStatus(templatesStatus: List<TemplatesStatusEntity>)
    //..............................................................................................


    //Upsert
    //..............................................................................................
    @Upsert
    suspend fun upsertTemplateStatus(templatesStatus: TemplatesStatusEntity)
    //..............................................................................................


    //Update
    //..............................................................................................
    @Update
    suspend fun updateTemplateStatus(templatesStatus: TemplatesStatusEntity)

    @Update
    suspend fun updateTemplateStatuses(templatesStatus: List<TemplatesStatusEntity>)
    //..............................................................................................


    //Select
    //..............................................................................................
    @Query("SELECT * FROM templates_status")
    fun templatesStatusList(): Flow<List<TemplatesStatusEntity>>
    //..............................................................................................


    //Delete
    //..............................................................................................
    @Delete
    fun deleteTemplateStatus(templatesStatus: TemplatesStatusEntity)

    @Query("DELETE FROM templates_status WHERE template_id = :templateId")
    suspend fun deleteTemplateStatusWithTemplateId(templateId: Int)
    //..............................................................................................


    //Other
    //..............................................................................................
    @Query("SELECT count(*) FROM templates_status")
    fun countTemplatesStatus(): Flow<Int>

    @Query("SELECT count(*) FROM templates_status WHERE template_id=:templateId")
    fun countTemplatesStatusInTemplateId(templateId:Int): Flow<Int>

    @Query("SELECT MAX(`order`) FROM templates_status WHERE template_id=:templateId")
    fun getMaxOrder(templateId:Int): Flow<Int?>
    //..............................................................................................

}