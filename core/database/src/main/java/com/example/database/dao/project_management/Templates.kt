package com.example.database.dao.project_management

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.database.model.pm.templates_statuses.relationships.TemplateWithStatuses
import com.example.database.model.pm.templates_statuses.TemplatesEntity
import com.example.database.model.pm.templates_statuses.TemplatesStatusEntity
import kotlinx.coroutines.flow.Flow


interface Templates {

    //Insert
    //..............................................................................................
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplate(templates: TemplatesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplates(templates: List<TemplatesEntity>)
    //..............................................................................................


    //Upsert
    //..............................................................................................
    @Upsert
    suspend fun upsertTemplate(templates: TemplatesEntity)
    //..............................................................................................


    //Update
    //..............................................................................................
    @Update
    suspend fun updateTemplate(templates: TemplatesEntity)
    //..............................................................................................


    //Select
    //..............................................................................................
    @Query("SELECT * FROM templates")
    fun templatesList(): Flow<List<TemplatesEntity>>
    //..............................................................................................


    //Delete
    //..............................................................................................
    @Delete
    fun deleteTemplate(templates: TemplatesEntity)
    //..............................................................................................


    //Other
    //..............................................................................................
    @Query("SELECT count(*) FROM templates")
    fun countTemplates(): Flow<Int>
    //..............................................................................................


    //Insert Transaction
    //..............................................................................................
    @Insert
    suspend fun insertTemplateTransaction(templates: TemplatesEntity): Long

    @Transaction
    suspend fun insertTemplateWithStatuses(
        template: TemplatesEntity,
        statuses: List<TemplatesStatusEntity>
    ) {
        // First insert the template
        val templateId = insertTemplateTransaction(template)

        //deleteTemplateStatus(templateId.toInt())

        // Then update the statuses with the correct templateId
        val updatedStatuses = statuses.map { it.copy(templateId = templateId.toInt()) }

        // Insert the statuses
        insertTemplatesStatusTransaction(updatedStatuses)
    }
    //..............................................................................................


    //Update Transaction
    //..............................................................................................
    @Transaction
    suspend fun updateTemplateWithStatuses(
        template: TemplatesEntity,
        statuses: List<TemplatesStatusEntity>
    ) {
        // First update the template
        updateTemplate(template)

        deleteTemplateStatusTransaction(template.id)

        // Then update the statuses with the correct templateId
        val updatedStatuses = statuses.map { it.copy(templateId = template.id) }

        // Insert the statuses
        insertTemplatesStatusTransaction(updatedStatuses)
    }
    //..............................................................................................


    //Select Transaction (Relational)
    //..............................................................................................
    // Fetch template with its statuses
    @Transaction
    @Query("SELECT * FROM templates WHERE id = :templateId")
    fun getTemplateWithStatus(templateId: Int): Flow<TemplateWithStatuses?>?
    //..............................................................................................



    //External Dao For Transaction
    //..............................................................................................
    @Query("DELETE FROM templates_status WHERE template_id = :templateId")
    suspend fun deleteTemplateStatusTransaction(templateId: Int)

    //Insert Template Status
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplatesStatusTransaction(templatesStatus: List<TemplatesStatusEntity>)
    //..............................................................................................


}


