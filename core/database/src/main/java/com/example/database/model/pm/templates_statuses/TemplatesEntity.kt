package com.example.database.model.pm.templates_statuses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "templates")
data class TemplatesEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")  val id:Int=0,
    @ColumnInfo(name = "user_id") val userId:Int?= null,

    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "description") val description:String? = null,
)