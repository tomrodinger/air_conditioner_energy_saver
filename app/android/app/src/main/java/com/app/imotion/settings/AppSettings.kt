package com.app.imotion.settings

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by hani.fakhouri on 2023-05-31.
 */

private const val KEY_ON_BOARDING_DONE = "0"

class AppSettings @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val preferences = context.getSharedPreferences(
        "com.app.imotion.settings.preferences",
        Context.MODE_PRIVATE
    )

    var onBoardingDone: Boolean
        get() = preferences.getBoolean(KEY_ON_BOARDING_DONE, false)
        set(value) {
            preferences.edit().putBoolean(KEY_ON_BOARDING_DONE, value).apply()
        }

}