package com.hxw.frame.utils

import android.content.Context
import android.os.Environment
import java.io.Closeable
import java.io.File
import java.io.IOException

/**
 * 文件相关工具类
 * @author hxw
 * @date 2017/8/19
 */
class FileUtils {
}

/**
 * 判断文件是否存在
 * @return {@code true}: 存在<br>{@code false}: 不存在
 */

fun File?.isFileExists() = this != null && this.exists()

/**
 * 创建未存在的文件夹
 */
fun File.makeDirs(): File {
    if (!this.isFileExists()) {
        this.mkdirs()
    }
    return this
}

/**
 * 获取自定义缓存文件地址
 */
fun Context.getCacheFilePath() = Environment.getExternalStorageDirectory().path + this.packageName

/**
 * 返回缓存文件夹
 */
fun Context.getCacheFile(): File {
    return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        var file = this.externalCacheDir//获取系统管理的外部存储目录
//            var file=context.cacheDir//临时缓存文件保存到的内部目录
        if (file == null) {
            file = File(this.getCacheFilePath())
            file.makeDirs()
        }
        file
    } else {
        this.cacheDir
    }
}

fun Closeable?.closeIO() {
    if (this != null) {
        try {
            this.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}