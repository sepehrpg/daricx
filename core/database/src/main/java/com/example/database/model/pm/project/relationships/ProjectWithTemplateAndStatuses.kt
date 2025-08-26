package com.example.database.model.pm.project.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.templates_statuses.TemplatesEntity
import com.example.database.model.pm.templates_statuses.relationships.TemplateWithStatuses


data class ProjectWithTemplateAndStatuses(
    @Embedded val project: ProjectsEntity,

    @Relation(
        parentColumn = "template_id",
        entityColumn = "id",
        entity = TemplatesEntity::class
    )
    val templateWithStatuses: TemplateWithStatuses?
)

