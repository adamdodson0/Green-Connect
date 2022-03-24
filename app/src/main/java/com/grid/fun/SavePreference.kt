package com.grid.`fun`

import android.content.Context
import android.content.SharedPreferences

/**
 * SavePreference takes the context from the class it is called from. It then
 * creates a sharedPreference variable which holds an int and a key to grab the
 * int value. This is used to hold whether or not the player has beaten the game.
 * If the player has beaten the game a trophy will appear in the second fragment.
 *
 * @author Adam Dodson
 * @version 1.1.0
 * @since 23-03-2022
 */
class SavePreference(context: Context) {

    private val sharedPreference: SharedPreferences =  context.getSharedPreferences("GAME_DATA",Context.MODE_PRIVATE)

    // Saves int value to sharedPreference with a key (String)
    fun saveInt(key: String?, value: Int) {
        val editor = sharedPreference.edit()
        editor.putInt(key.toString(),value)
        editor.apply()
    }

    // Gets integer from sharedPreference using key (string)
    fun getInteger(key: String?): Int {
        return sharedPreference.getInt(key, 0)
    }
}