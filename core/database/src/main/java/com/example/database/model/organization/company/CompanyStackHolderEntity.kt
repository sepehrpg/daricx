package com.example.database.model.organization.company

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_stack_holder")
data class CompanyStackHolderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "user_id") val userId:Int,
    @ColumnInfo(name = "company_id") val companyId:Int,
    @ColumnInfo(name = "company_role_id") val companyRoleId:Int,
)