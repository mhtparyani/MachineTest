package com.turtlemint.mymachinetest.controller.service

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface IssuesService {
    @GET("square/okhttp/issues")
    fun fetchIssues(): Single<ResponseBody?>

    @GET()
    fun fetchComments(@Url url: String): Single<ResponseBody?>
}