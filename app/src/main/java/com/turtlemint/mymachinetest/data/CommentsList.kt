package com.turtlemint.mymachinetest.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "comments")
data class CommentsList(
    @PrimaryKey val id: Int =0,
    val issueId: Int=0,
    val userName: String?= null,
    val description: String?= null,
    val avatar: String?= null,
    val updatedAt: String? = null
)
