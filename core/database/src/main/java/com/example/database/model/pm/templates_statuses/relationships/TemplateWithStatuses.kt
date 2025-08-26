package com.example.database.model.pm.templates_statuses.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.database.model.pm.templates_statuses.TemplatesEntity
import com.example.database.model.pm.templates_statuses.TemplatesStatusEntity


data class TemplateWithStatuses(
    @Embedded val template: TemplatesEntity, // The template entity itself
    @Relation(
        parentColumn = "id", // The column in TemplatesEntity
        entityColumn = "template_id" // The column in TemplatesStatusEntity
    )
    val statuses: List<TemplatesStatusEntity> // The related statuses
)

