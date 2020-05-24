package com.skills.rentaride.utils

import android.content.Context
import android.content.SharedPreferences
import com.skills.rentaride.R

class SharedPrefManager(context: Context) {

    private var sharedPrefManager: SharedPrefManager? = null
    private var sp: SharedPreferences? = null
    private var context: Context? = null

    init {
        this.context = context
        sp = context.getSharedPreferences(
            context.getString(R.string.shared_pref_name),
            Context.MODE_PRIVATE
        )
    }

    fun getSharedPrefManager(context: Context?): SharedPrefManager? {
        if (sharedPrefManager == null) {
            sharedPrefManager =  SharedPrefManager(context!!)
        }
        return sharedPrefManager
    }

    fun setString(keyResId: String, value: String?) {
        sp!!.edit().putString(keyResId, value).apply()
    }
    fun getString(keyResId: String): String {
        return sp!!.getString(keyResId, "")!!
    }

    fun remove(keyResId: String) {
        sp!!.edit().remove(keyResId).apply()
    }

    fun clear() {
        sp!!.edit().clear().apply()
    }

    fun getBoolean(keyResId: String?): Boolean {
        return sp!!.getBoolean(keyResId, false)
    }
}