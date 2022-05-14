package com.turtlemint.mymachinetest.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turtlemint.mymachinetest.data.CommentsList
import com.turtlemint.mymachinetest.data.IssueList
import com.turtlemint.mymachinetest.data.IssuesListRepository
import com.turtlemint.mymachinetest.util.AppConstants
import com.turtlemint.mymachinetest.util.NetworkUtil
import com.turtlemint.mymachinetest.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val repository: IssuesListRepository
) : ViewModel() {

    var commentList: SingleLiveEvent<List<CommentsList>> = SingleLiveEvent()

    @SuppressLint("CheckResult")
    fun getData(context: Context, url: String, issueId: Int) {
        viewModelScope.launch {
            if (NetworkUtil.getConnectivityStatusString(context)) {
                repository.fetchComments(url).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { response ->
                        val issuesResponseJson = AppConstants.getJsonArrayResponseFromRaw(response)
                        var commentsList = ArrayList<CommentsList>()
                        (0 until issuesResponseJson!!.length()).forEach {
                            val jsonObject = issuesResponseJson.getJSONObject(it)
                            val userJson = jsonObject.getJSONObject("user")
                            val issue = CommentsList(
                                id = jsonObject.getInt("id"),
                                issueId= issueId,
                                updatedAt = jsonObject.getString("updated_at"),
                                userName = userJson.getString("login"),
                                avatar = userJson.getString("avatar_url"),
                                description = jsonObject.getString("body"),
                            )
                            commentsList.add(issue)

                        }
                        commentList.value = commentsList
                    }


            } else {
                repository.fetchCommentsDB(issueId).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { issues ->
                        commentList.value = issues.reversed()
                    }
            }
        }
    }

    fun insertNewDataToDB(issueId: Int) {
        viewModelScope.launch {
            repository.deleteAllComments(issueId)
            repository.insertComments(commentList.value!!)
        }
    }

}