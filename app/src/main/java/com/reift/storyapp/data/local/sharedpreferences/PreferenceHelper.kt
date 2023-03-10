package com.reift.storyapp.data.local.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(val context: Context) {

    var sharedPreferences = context.getSharedPreferences(PREF_USER, 0)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun add(key: String, value: String){
        editor.putString(key, value)
        editor.apply()
    }

    fun add(key: String, value: Boolean){
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clear(){
        editor.clear()
        editor.apply()
    }

    companion object {
        const val PREF_USER = "pref_user_database"
    }

}