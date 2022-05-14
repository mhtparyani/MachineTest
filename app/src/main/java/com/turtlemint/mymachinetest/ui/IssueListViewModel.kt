package com.turtlemint.mymachinetest.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class IssueListViewModel @Inject constructor(
    private val repository: IssuesListRepository
) : ViewModel() {

    var issuesList: SingleLiveEvent<List<IssueList>> = SingleLiveEvent()

    @SuppressLint("CheckResult")
    fun getData(context: Context) {
        viewModelScope.launch {
            if (NetworkUtil.getConnectivityStatusString(context)) {

                repository.fetchData().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { response ->
                        val issuesResponseJson = AppConstants.getJsonArrayResponseFromRaw(response)
                        var issueList = ArrayList<IssueList>()
                        (0 until issuesResponseJson!!.length()).forEach {
                            val jsonObject = issuesResponseJson.getJSONObject(it)
                            val userJson = jsonObject.getJSONObject("user")
                            val issue = IssueList(
                                id = jsonObject.getInt("id"),
                                commentsUrl = jsonObject.getString("comments_url"),
                                updatedAt = jsonObject.getString("updated_at"),
                                userName = userJson.getString("login"),
                                avatar = userJson.getString("avatar_url"),
                                description = jsonObject.getString("body"),
                                title = jsonObject.getString("title")
                            )
                            issueList.add(issue)

                        }
                        issuesList.value = issueList
                    }
            } else {
                repository.fetchDataDB().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { issues ->
                        issuesList.value = issues.reversed()
                    }
            }
        }
    }

    fun insertNewDataToDB(){
        viewModelScope.launch {
            repository.deleteAll()
            repository.insertData(issuesList.value!!)
        }
    }

}