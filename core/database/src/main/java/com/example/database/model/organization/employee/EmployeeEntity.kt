package com.example.database.model.organization.employee

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class EmployeeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "user_id") val userId:Int,
    @ColumnInfo(name = "employee_role_id") val employeeRoleId:Int,
    @ColumnInfo(name = "company_id") val companyId:Int,
    @ColumnInfo(name = "added_by_user_id") val addedByUserId:Int,



    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "goals") val goals:String,
    @ColumnInfo(name = "employee_code") val employeeCode:String,
)