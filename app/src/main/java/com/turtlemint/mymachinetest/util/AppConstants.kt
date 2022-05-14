package com.turtlemint.mymachinetest.util

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

class AppConstants {
    companion object {
        const val SERVER_VAL = "https://api.github.com/repos/"
        const val COMM_URL = "url"
        const val USER_NAME = "username"
        const val USER_AVATAR = "avatar"
        const val USER_DES = "description"
        const val ISSUE_ID = "issueid"
        const val TITLE = "title"

        private const val TIMEOUT = 60 * 1000

        fun buildRetrofit(defaultRequest: Boolean): Retrofit? {
            val okHttpClient = OkHttpClient()
            return if (!defaultRequest) {
                Retrofit.Builder()
                    .baseUrl(SERVER_VAL)
                    .client(
                        okHttpClient.newBuilder()
                            .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                            .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                            .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS).build()
                    )
                    .addConverterFactory(
                        GsonConverterFactory.create(
                            CustomGsonBuilder.getInstance().create()
                        )
                    )
                    .build()
            } else {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                Retrofit.Builder()
                    .baseUrl(SERVER_VAL)
                    .client(
                        okHttpClient.newBuilder()
                            .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                            .readTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
                            .writeTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS).build()
                    )
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
        }
        fun getJsonResponseFromRaw(response: ResponseBody?): JSONObject? {
            return try {
                JSONObject(getStringResponseFromRaw(response))
            } catch (ex: Exception) {
                null
            }
        }

        fun getJsonArrayResponseFromRaw(response: ResponseBody?): JSONArray? {
            return try {
                JSONArray(getStringResponseFromRaw(response))
            } catch (ex: Exception) {
                null
            }
        }

        private fun getStringResponseFromRaw(response: ResponseBody?): String {
            var reader: BufferedReader? = null
            val sb = StringBuilder()
            try {
                reader = BufferedReader(InputStreamReader(response?.byteStream()))
                var line: String?
                try {
                    while (reader.readLine().also { line = it } != null) {
                        sb.append(line)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return sb.toString()
        }
    }
}