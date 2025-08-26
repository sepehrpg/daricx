package com.example.database.model.pm.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project_role")
data class ProjectRoleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,
    @ColumnInfo(name = "name") val name:String,
)