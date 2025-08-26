package com.example.database.model.pm.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TasksEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int = 0,

    @ColumnInfo(name = "user_id") val userId:Int?= null,
    @ColumnInfo(name = "project_id") val projectId:Int?= null,
    @ColumnInfo(name = "template_status_id") val templateStatusId:Int?= null,


    @ColumnInfo(name = "priority") val priority:Int?= null,
    @ColumnInfo(name = "percent_complete") val percentComplete:Double?= null,

    @ColumnInfo(name = "task_name") val taskName:String,
    @ColumnInfo(name = "descriptions") val descriptions:String?= null,
    @ColumnInfo(name = "planned_start_date") val plannedStartDate:String?= null,
    @ColumnInfo(name = "planned_end_date") val plannedEndDate:String?= null,
    @ColumnInfo(name = "actual_start_date") val actualStartDate:String?= null,
    @ColumnInfo(name = "actual_end_date") val actualEndDate:String?= null,


    @ColumnInfo(name = "planned_budget") val plannedBudget:Int?= null,
    @ColumnInfo(name = "actual_budget") val actualBudget:Int?= null,
)