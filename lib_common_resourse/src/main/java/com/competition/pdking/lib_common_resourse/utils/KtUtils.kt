package com.competition.pdking.lib_common_resourse.utils

import android.content.res.Resources
import kotlin.math.roundToInt


/**
 * 密度值(dp)转换像素值(px)
 *
 * @param dp dp值
 * @return 像素值
 */
fun dp2px(dp: Float): Int = (dp * Resources.getSystem().displayMetrics.density).roundToInt()

/**
 * 像素值(px)转换密度值(dp)
 *
 * @param px px值
 * @return 密度值
 */
fun px2dp(px: Float): Int = (px / Resources.getSystem().displayMetrics.density).roundToInt()

/**
 * 缩放密度值(sp)转换像素值(px)
 *
 * @param dp dp值
 * @return 像素值
 */
fun sp2px(dp: Float): Int = (dp * Resources.getSystem().displayMetrics.scaledDensity).toInt()