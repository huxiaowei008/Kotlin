package com.hxw.frame.utils.constant

import android.support.annotation.IntDef

/**
 * 时间相关常量
 * Created by hxw on 2017/9/1.
 */
object TimeConstants {
    /**
     * 毫秒与毫秒的倍数
     */
    const val MSEC = 1L
    /**
     * 秒与毫秒的倍数
     */
    const val SEC = 1_000L
    /**
     * 分与毫秒的倍数
     */
    const val MIN = 60_000L
    /**
     * 时与毫秒的倍数
     */
    const val HOUR = 3_600_000L
    /**
     * 天与毫秒的倍数
     */
    const val DAY = 86_400_000L

    @IntDef(MSEC, SEC, MIN, HOUR, DAY)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Unit
}