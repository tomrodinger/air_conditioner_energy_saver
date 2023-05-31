package com.app.imotion.settings

import android.content.Context

/**
 * Created by hani.fakhouri on 2023-05-31.
 */

private const val KEY_ON_BOARDING_DONE = "0"

class AppSettings(private val context: Context) {

    private val preferences = context.getSharedPreferences(
        "com.app.imotion.settings.preferences",
        Context.MODE_PRIVATE
    )

    var onBoardingDone: Boolean
        get() = preferences.getBoolean(KEY_ON_BOARDING_DONE, false)
        set(value) {
            preferences.edit().putBoolean(KEY_ON_BOARDING_DONE, value).apply()
        }

    companion object : SingletonHolder<AppSettings, Context>(::AppSettings)

}

open class SingletonHolder<out T : Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T {
        val checkInstance = instance
        if (checkInstance != null) {
            return checkInstance
        }

        return synchronized(this) {
            val checkInstanceAgain = instance
            if (checkInstanceAgain != null) {
                checkInstanceAgain
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}