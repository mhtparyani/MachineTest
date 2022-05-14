package com.turtlemint.mymachinetest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.turtlemint.mymachinetest.data.IssueList
import com.turtlemint.mymachinetest.data.IssuesDao

@Database(entities = [IssueList::class, CommentsList::class], version = 1)
abstract class IssuesListDatabase : RoomDatabase() {
    abstract fun issuesDao(): IssuesDao
    abstract fun commentsDao(): CommentsDao
}
