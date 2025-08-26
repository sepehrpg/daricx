package com.example.database.model.organization.company

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "company")
data class CompanyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "added_by_user_id") val addedByUserId:Int,

    @ColumnInfo(name = "phone") val phone:Int,
    @ColumnInfo(name = "mobile") val mobile:Int,

    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "address") val address:String,
    @ColumnInfo(name = "company_code") val companyCode:String,

    @ColumnInfo(name = "logo") val logo:String,

    @ColumnInfo(name = "location") val location:Double,


    @ColumnInfo(name = "doc") val doc:Any,
)