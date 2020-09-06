package com.axiom.telecom.utils

import android.content.Context
import android.net.ConnectivityManager
import com.axiom.telecom.application.MyApp

object NetworkUtil {

    fun isNetworkConnected() : Boolean {
        var cm = MyApp._INSTANCE.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected;
    }

}