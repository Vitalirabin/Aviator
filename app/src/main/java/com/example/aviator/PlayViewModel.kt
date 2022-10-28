package com.example.aviator

import android.content.Context
import androidx.lifecycle.ViewModel

private const val SHARED_QUERY = "shared_prefs"
private const val SCORE = "score"
private const val MUTE = "mute"
private const val VIBRO = "vibro"


class PlayViewModel : ViewModel() {

    fun getScore(context: Context?): Int? {
        val sharedPrefs = context?.getSharedPreferences(SHARED_QUERY, Context.MODE_PRIVATE)
        if (sharedPrefs?.getInt(SCORE, 0) == 0) {
            sharedPrefs.edit()?.putInt(SCORE, 10000)?.apply()
        }
        return sharedPrefs?.getInt(SCORE, 10000)
    }

    fun setScore(context: Context?, score: Int) {
        val sharedPrefs = context?.getSharedPreferences(SHARED_QUERY, Context.MODE_PRIVATE)
        sharedPrefs?.edit()?.putInt(SCORE, score)?.apply()
    }

    fun getMute(context: Context?): Boolean? {
        return context?.getSharedPreferences(SHARED_QUERY, Context.MODE_PRIVATE)
            ?.getBoolean(MUTE, true)
    }

    fun getVibro(context: Context): Boolean {
        return context.getSharedPreferences(SHARED_QUERY, Context.MODE_PRIVATE)
            .getBoolean(VIBRO, true)
    }
}