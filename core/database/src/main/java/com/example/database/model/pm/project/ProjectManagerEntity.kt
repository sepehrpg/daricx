package com.example.database.model.pm.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "project_manager")
data class ProjectManagerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "project_id") val projectId:Int,
    @ColumnInfo(name = "user_id") val userId:Int,

)