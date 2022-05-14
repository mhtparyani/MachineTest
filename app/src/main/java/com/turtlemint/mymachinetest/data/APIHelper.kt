package com.turtlemint.mymachinetest.data

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call

interface APIHelper {

    suspend fun fetchData(): Single<ResponseBody?>

    suspend fun fetchDataDB(): Single<List<IssueList>>

    suspend fun insertData(issues: List<IssueList>)
    suspend fun deleteAll()

    suspend fun fetchComments(url:String): Single<ResponseBody?>

    suspend fun fetchCommentsDB(issueId: Int): Single<List<CommentsList>>

    suspend fun insertComments(issues: List<CommentsList>)
    suspend fun deleteAllComments(issueId: Int)
}