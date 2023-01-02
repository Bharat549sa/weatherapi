package com.example.weatherapi.utils

import android.content.Context

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

import android.os.Build



object Constants {

    const val APP_ID: String = "603dc85d5ec5fcefebd6665c933e8819"
    const val BASE_URL : String = "https://api.openweathermap.org/data/"
    const val METRIC_UNIT :String = "metric"

fun isNetworkAvailable(context: Context) : Boolean{
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetWork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            activeNetWork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }else {
        // Returns details about the currently active default data network.
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }
}


}