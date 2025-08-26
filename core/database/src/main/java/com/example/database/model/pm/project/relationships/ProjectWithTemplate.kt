package com.example.database.model.pm.project.relationships

import androidx.room.Embedded
import androidx.room.Relation
import com.example.database.model.pm.project.ProjectsEntity
import com.example.database.model.pm.templates_statuses.TemplatesEntity


data class ProjectWithTemplate(
    @Embedded val project: ProjectsEntity,

    @Relation(
        parentColumn = "template_id",
        entityColumn = "id"
    )
    val template: TemplatesEntity?
)