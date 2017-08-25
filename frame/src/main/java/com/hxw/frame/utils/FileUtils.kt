package com.hxw.frame.utils

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * Created by hxw on 2017/8/19.
 */
object FileUtils {

    /**
     * 返回缓存文件夹
     */
    fun getCacheFile(context: Context): File {
        return if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            var file = context.externalCacheDir//获取系统管理的外部存储目录
//            var file=context.cacheDir//临时缓存文件保存到的内部目录
            if (file == null) {
                file = File(getCacheFilePath(context))
                makeDirs(file)
            }
            file
        } else {
            context.cacheDir
        }
    }

    /**
     * 获取自定义缓存文件地址
     */
    fun getCacheFilePath(context: Context) = Environment.getExternalStorageDirectory().path + context.packageName

    /**
     * 创建未存在的文件夹
     */
    fun makeDirs(file: File):File{
        if (!isFileExists(file)){
            file.mkdirs()
        }
        return file
    }

    /**
     * 判断文件是否存在
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    fun isFileExists(file: File?) = file != null && file.exists()
}