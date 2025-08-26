package com.example.database.dao

import androidx.room.Dao
import com.example.database.dao.project_management.Projects
import com.example.database.dao.project_management.Tasks
import com.example.database.dao.project_management.Templates
import com.example.database.dao.project_management.TemplatesStatus


@Dao
interface ProjectsManagementDao: Templates,Projects, TemplatesStatus,Tasks

