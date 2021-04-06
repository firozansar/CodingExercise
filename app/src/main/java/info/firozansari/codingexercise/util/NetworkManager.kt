package info.firozansari.codingexercise.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkManager(private val appContext: Context) {

    fun isOnline(): Boolean {
        val connMgr = appContext.applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connMgr.activeNetworkInfo
        return networkInfo?.isConnected == true
    }
}