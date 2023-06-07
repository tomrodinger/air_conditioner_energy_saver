package com.app.imotion.ui.utils

import android.content.Context
import android.util.DisplayMetrics


/**
 * Created by hani.fakhouri on 2023-06-07.
 */
object AndroidUtils {

    fun pxToDp(px: Float, context: Context): Float =
        px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

    fun dpToPx(dp: Float, context: Context): Float =
        dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

}