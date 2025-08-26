package com.example.database.model.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UsersEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "user_role_id") val userRoleId:Int,
    @ColumnInfo(name = "company_id") val companyId:Int? = null,

    @ColumnInfo(name = "mobile") val mobile:Int,
    @ColumnInfo(name = "phone") val phone:Int,

    @ColumnInfo(name = "username") val username:String,
    @ColumnInfo(name = "email") val email:String,
    @ColumnInfo(name = "firstname") val firstName:String,
    @ColumnInfo(name = "lastname") val lastName:String,
    @ColumnInfo(name = "user_code") val userCode:String,

    @ColumnInfo(name = "image") val image:String,
    @ColumnInfo(name = "password") val password:String,

    @ColumnInfo(name = "location") val location:Double,
)