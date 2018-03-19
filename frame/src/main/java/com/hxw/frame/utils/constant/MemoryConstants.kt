package com.hxw.frame.utils.constant

import android.support.annotation.IntDef

/**
 * 存储相关常量
 * @author hxw
 * @date 2017/9/1
 */
object MemoryConstants {
    /**
     * Byte与Byte的倍数
     */
    const val BYTE = 1
    /**
     * KB与Byte的倍数
     */
    const val KB = 1_024
    /**
     * MB与Byte的倍数
     */
    const val MB = 1_048_576
    /**
     * GB与Byte的倍数
     */
    const val GB = 1_073_741_824

    @IntDef(BYTE, KB, MB, GB)
    @Retention(AnnotationRetention.SOURCE)
    annotation class Unit
}
