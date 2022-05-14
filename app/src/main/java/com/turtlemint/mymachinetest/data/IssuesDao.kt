package com.turtlemint.mymachinetest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface IssuesDao {

    @Query("SELECT * FROM issues")
    fun getAllIssues(): Single<List<IssueList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)

    suspend fun insertIssues(issues: List<IssueList>)

    // Again it will use coroutine to achieve this task
    @Query("DELETE FROM issues")
    suspend fun deleteAllIssues()
}