package com.example.database.model.users

import androidx.room.Embedded
import androidx.room.Relation


data class UserWithRole(
    @Embedded
    val users: UsersEntity,

    @Relation(parentColumn = "user_role_id", entityColumn = "id")
    val role: UserRoleEntity
)