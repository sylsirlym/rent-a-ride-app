package com.skills.rentaride.utils

import android.app.Application
import android.util.Log

class MyApplication : Application() {
    val TAG = "MyApplication"
    var globalVar = ""
    fun setMsisdn(msisdn: String) {
        Log.i(TAG,"Setting MSISDN" )
        globalVar = msisdn
        Log.i(TAG, "Set MSISDN $globalVar")
    }

    fun getMsisdn() : String {
        Log.i(TAG,"Getting MSISDN" )
        return globalVar
    }
}