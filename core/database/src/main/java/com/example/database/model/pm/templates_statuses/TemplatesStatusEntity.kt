package com.example.database.model.pm.templates_statuses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "templates_status",
    foreignKeys = [
        ForeignKey(
            entity = TemplatesEntity::class,
            parentColumns = ["id"],
            childColumns = ["template_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TemplatesStatusEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int = 0,
    @ColumnInfo(name = "order") var order:Int,

    @ColumnInfo(name = "template_id") val templateId:Int = 0,

    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "color") val color:String? = null ,
    @ColumnInfo(name = "image") val image:String? = null ,
)