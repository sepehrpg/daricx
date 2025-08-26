package com.example.database.model.pm.task.activity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "task_id") val taskId:Int,
    @ColumnInfo(name = "user_id") val userId:Int,

    @ColumnInfo(name = "priority") val priority:Int,

    @ColumnInfo(name = "activity_name") val activityName:String,
    @ColumnInfo(name = "descriptions") val descriptions:String,
    @ColumnInfo(name = "planned_start_date") val plannedStartDate:String,
    @ColumnInfo(name = "planned_end_date") val plannedEndDate:String,
    @ColumnInfo(name = "actual_start_date") val actualStartDate:String,
    @ColumnInfo(name = "actual_end_date") val actualEndDate:String,


    @ColumnInfo(name = "planned_budget") val plannedBudget:Int,
    @ColumnInfo(name = "actual_budget") val actualBudget:Int,

    )