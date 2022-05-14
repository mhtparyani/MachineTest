package com.turtlemint.mymachinetest.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.turtlemint.mymachinetest.util.NetworkUtil

object NetworkUtil {
    var TYPE_WIFI = 1
    var TYPE_MOBILE = 2
    var TYPE_NOT_CONNECTED = 0
    private fun getConnectivityStatus(context: Context): Int {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (null != activeNetwork) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) return TYPE_WIFI
            if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE
        }
        return TYPE_NOT_CONNECTED
    }

    fun getConnectivityStatusString(context: Context): Boolean {
        return when (getConnectivityStatus(context)) {
            TYPE_WIFI -> {
                true
            }
            TYPE_MOBILE -> {
                true
            }
            TYPE_NOT_CONNECTED -> {
                false
            }
            else -> {
                false
            }
        }
    }
}