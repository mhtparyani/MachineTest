package com.turtlemint.mymachinetest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface CommentsDao {

    @Query("SELECT * FROM comments WHERE issueId= :issueId")
    fun getAllComments(issueId: Int): Single<List<CommentsList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun insertComments(comments: List<CommentsList>)

    // Again it will use coroutine to achieve this task
    @Query("DELETE FROM comments WHERE issueId= :issueId")
    suspend fun deleteAllComments(issueId: Int)
}