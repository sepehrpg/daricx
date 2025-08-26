package com.example.database.utils

import androidx.room.TypeConverter
import com.example.database.model.pm.project.ProjectAccess

class ProjectAccessConverter {

    @TypeConverter
    fun fromProjectAccess(projectAccess: ProjectAccess): Int {
        return projectAccess.id
    }

    @TypeConverter
    fun toProjectAccess(id: Int): ProjectAccess {
        return ProjectAccess.entries.first { it.id == id }
    }
}