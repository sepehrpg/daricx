package com.example.database.model.pm.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "assigned")
data class AssignedEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "activity_id") val activityId:Int,
    @ColumnInfo(name = "task_id") val taskId:Int,
    @ColumnInfo(name = "user_id") val userId:Int,
    @ColumnInfo(name = "user_role_id") val userRoleId:Int,
    @ColumnInfo(name = "employee_role_id") val employeeRoleId:Int,
    @ColumnInfo(name = "team_role_id") val teamRoleId:Int,
    @ColumnInfo(name = "project_role_id") val projectRoleId:Int,
)