package com.example.database.model.pm.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "on_project")
data class OnProjectEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "project_id") val projectId:Int,
    @ColumnInfo(name = "client_partner_id") val clientPartnerId:Int? = null,
    @ColumnInfo(name = "user_id") val userId:Int? = null,

    @ColumnInfo(name = "description") val description:String,

    @ColumnInfo(name = "start_date") val startDate:String,
    @ColumnInfo(name = "end_date") val endDate:String,

    @ColumnInfo(name = "is_client") val isClient:String,
    @ColumnInfo(name = "is_partner") val isPartner:String,
)