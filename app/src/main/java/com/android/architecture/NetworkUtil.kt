package com.android.architecture

import android.content.Context
import android.net.ConnectivityManager


class NetworkUtil {
    fun getConnectivityStatusString(context: Context):Boolean? {
        var status: Boolean? = null
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        if (activeNetwork != null) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                status = true
                return status
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                status = true
                return status
            }
        } else {
            status = false
            return status
        }
        return status
    }
}