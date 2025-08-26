package com.example.database.model.pm.project

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.database.utils.ProjectAccessConverter


@Entity(tableName = "projects")
data class ProjectsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Int? = null,


    @ColumnInfo(name = "project_access_id") val projectAccess:ProjectAccess = ProjectAccess.PRIVATE,
    @ColumnInfo(name = "project_access_name")  var projectAccessName:String? = projectAccess.name(null),
    @ColumnInfo(name = "template_id") val templateId:Int? = null,
    @ColumnInfo(name = "user_id") val userId:Int? = null,


    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "description") val description:String? = null,

    @ColumnInfo(name = "actual_start_date") val actualStartDate:String? = null,
    @ColumnInfo(name = "actual_end_date") val actualEndDate:String? = null,
    @ColumnInfo(name = "planned_start_date") val plannedStartDate:String? = null,
    @ColumnInfo(name = "planned_end_date") val plannedEndDate:String? = null,

    @ColumnInfo(name = "image") val image:String? = null,
    @ColumnInfo(name = "color") val color:String? = null,
)

enum class ProjectAccess(val id: Int) {
    PRIVATE(1) {
        override fun name(context: Context?): String {
            return "Private"
        }

        override fun descriptions(context: Context): String {
            return "Collaborate by inviting specific people via link or direct invitations. Only invited users can view and access the project."
        }
    },

    COMPANY(2) {
        override fun name(context: Context?): String {
            return "Company"
        }

        override fun descriptions(context: Context): String {
            return "A shared project visible to all team members within your company."
        }
    };

    abstract fun name(context: Context?): String
    abstract fun descriptions(context: Context): String
}


