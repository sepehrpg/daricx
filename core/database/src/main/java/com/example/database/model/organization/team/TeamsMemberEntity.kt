package com.example.database.model.organization.team
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams_member")
data class TeamsMemberEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int,

    @ColumnInfo(name = "teams_id") val teamsId:Int,
    @ColumnInfo(name = "user_id") val userId:Int,
    @ColumnInfo(name = "employee_id") val employeeId:Int,
    @ColumnInfo(name = "company_id") val companyId:Int,
    @ColumnInfo(name = "added_by_user_id") val addedByUserId:Int,
    @ColumnInfo(name = "teams_role_id") val teamRoleId:Int,


    @ColumnInfo(name = "description") val description:String,
    @ColumnInfo(name = "goals") val goals:String,
    @ColumnInfo(name = "teams_member_code") val teamMemberCode:String,
)