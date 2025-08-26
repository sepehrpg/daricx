package com.example.database.model.pm.project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "client_partner")
data class ClientPartnerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "user_id") val userId:Int? = null,

    @ColumnInfo(name = "cp_name") val cpName:String,
    @ColumnInfo(name = "cp_address") val cpAddress:String,
    @ColumnInfo(name = "description") val description:String,


    @ColumnInfo(name = "cp_details") val cpDetails:Any,
)