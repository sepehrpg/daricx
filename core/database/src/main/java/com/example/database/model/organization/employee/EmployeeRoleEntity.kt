package com.example.database.model.organization.employee

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_role")
data class CompanyRoleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "name") val name:String,
)