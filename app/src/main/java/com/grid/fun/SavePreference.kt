package com.grid.`fun`

import android.content.Context
import android.content.SharedPreferences

class SavePreference(context: Context) {

    private val sharedPreference: SharedPreferences =  context.getSharedPreferences("GAME_DATA",Context.MODE_PRIVATE)

    fun saveInt(key: String?, value: Int) {
        val editor = sharedPreference.edit()
        editor.putInt(key.toString(),value)
        editor.apply()
    }

    fun getInteger(key: String?): Int {
        return sharedPreference.getInt(key, 0)
    }
}