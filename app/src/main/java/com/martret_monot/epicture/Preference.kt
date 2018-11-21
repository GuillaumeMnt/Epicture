package com.martret_monot.epicture

import android.content.Context
import android.content.SharedPreferences

class Preference constructor(context: Context){

    val PREFERENCE_ACCESS: String = "TOKEN"

    /// Account
    val ACCESS_TOKEN: String = "access_token"
    val REFRESH_TOKEN: String = "refresh_token"
    val ACCOUNT_USERNAME: String = "account_username"

    /// Imgur Client
    val SECTION: String = "section_request"
    val SORT: String = "sort_request"

    val preference = context.getSharedPreferences(PREFERENCE_ACCESS, Context.MODE_PRIVATE)
    val edit: SharedPreferences.Editor = preference.edit()

    fun getAccessToken(): String {

        return preference.getString(ACCESS_TOKEN, "")
    }

    fun getRefreshToken(): String {

        return preference.getString(REFRESH_TOKEN, "")
    }

    fun getAccountUsername(): String {

        return preference.getString(ACCOUNT_USERNAME, "")
    }

    fun getRequestSection(): String {

        return preference.getString(SECTION, "hot")
    }

    fun getRequestSort(): String {

        return preference.getString(SORT, "viral")
    }

    fun setAccessToken(newValue: String) {
        edit.putString(ACCESS_TOKEN, newValue)
        edit.commit()
    }

    fun setRefreshToken(newValue: String) {
        edit.putString(REFRESH_TOKEN, newValue)
        edit.commit()
    }

    fun setAccountUsername(newValue: String) {
        edit.putString(ACCOUNT_USERNAME, newValue)
        edit.commit()
    }

    fun setRequestSection(newValue: String?) {

        if (newValue == "hot" || newValue == "top" || newValue == "user") {
            edit.putString(SECTION, newValue)
            edit.commit()
        }
    }

    fun setRequestSort(newValue: String?) {

        if (newValue == "viral" || newValue == "top" || newValue == "time" || newValue == "rising") {
            edit.putString(SORT, newValue)
            edit.commit()
        }
    }

    fun clear(context: Context) {
        edit.clear().commit()
        val packageName = context.packageName
        val runtime = Runtime.getRuntime()
        runtime.exec("pm clear $packageName")
    }
}