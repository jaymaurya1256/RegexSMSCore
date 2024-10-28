package com.example.kuchbhi.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

private const val TAG = "PreferenceHelper"
class PreferenceHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun getStringList(key: String): List<String> {
        Log.d(TAG, "getStringList: ${sharedPreferences.getStringSet(key, null)?.toList()}")
        return sharedPreferences.getStringSet(key, null)?.toList() ?: emptyList()
    }

    fun saveStringList(key: String, value: List<String>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet(key, value.toSet())
        Log.d(TAG, "saveStringList: $value")
        editor.apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
