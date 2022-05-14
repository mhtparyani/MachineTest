package com.turtlemint.mymachinetest.data

import com.turtlemint.mymachinetest.controller.service.IssuesService
import io.reactivex.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import javax.inject.Inject

class IssuesListRepository @Inject constructor(
    private val api: IssuesService,
    private val db: IssuesListDatabase
): APIHelper {

    private val issueDao = db.issuesDao()
    private val commentsDao = db.commentsDao()
    override suspend fun fetchDataDB(): Single<List<IssueList>> = issueDao.getAllIssues()

    override suspend fun fetchData(): Single<ResponseBody?> = api.fetchIssues()
    override suspend fun insertData(issues: List<IssueList>) = withContext(Dispatchers.IO) {
        issueDao.insertIssues(issues)
    }

    override suspend fun deleteAll() = issueDao.deleteAllIssues()


    override suspend fun fetchComments(url: String): Single<ResponseBody?> = api.fetchComments(url)

    override suspend fun fetchCommentsDB(issueId: Int): Single<List<CommentsList>> =commentsDao.getAllComments(issueId)

    override suspend fun insertComments(comments: List<CommentsList>) = withContext(Dispatchers.IO){
        commentsDao.insertComments(comments)
    }

    override suspend fun deleteAllComments(issueId: Int) = commentsDao.deleteAllComments(issueId)


}