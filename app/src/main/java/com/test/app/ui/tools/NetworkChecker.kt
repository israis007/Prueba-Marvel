package com.test.app.ui.tools

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.test.app.AppTest

class NetworkChecker {
    companion object {
        fun isConnected(): Boolean {
            val conman = AppTest.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = conman.activeNetwork
            return if (conman.activeNetwork != null){
                val netcap = conman.getNetworkCapabilities(network)
                (netcap?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true || netcap?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true)
            } else
                false
        }
    }
}