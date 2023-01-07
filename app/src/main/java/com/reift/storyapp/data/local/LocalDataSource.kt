package com.reift.storyapp.data.local

import com.reift.storyapp.constant.Pref
import com.reift.storyapp.data.local.sharedpreferences.PreferenceHelper

class LocalDataSource(
    val preferenceHelper: PreferenceHelper
) {

    fun isUserLogin(): Boolean {
        return preferenceHelper.getBoolean(Pref.IS_LOGIN)
    }

    fun getAuthToken(): String {
        return preferenceHelper.getString(Pref.AUTH_TOKEN).orEmpty()
    }

    fun login(authToken: String){
        preferenceHelper.add(Pref.IS_LOGIN, true)
        preferenceHelper.add(Pref.AUTH_TOKEN, authToken)
    }

    fun logout(){
        preferenceHelper.clear()
    }
}