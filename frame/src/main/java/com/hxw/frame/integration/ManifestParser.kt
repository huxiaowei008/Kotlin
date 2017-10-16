package com.hxw.frame.integration

import android.content.Context
import android.content.pm.PackageManager
import timber.log.Timber

/**
 * Manifest解析工具
 * @author hxw
 * @date 2017/8/19
 */
class ManifestParser(private val context: Context) {
    private val TAG = "ManifestParser"
    private val MODULE_VALUE = "ConfigModule"

    fun parse(): MutableList<ConfigModule> {
        val modules = mutableListOf<ConfigModule>()
        try {
            val appInfo = context.packageManager
                    .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            if (appInfo.metaData != null) {
                Timber.tag(TAG).v("Got app info metadata: " + appInfo.metaData)
                for (key: String in appInfo.metaData.keySet()) {
                    if (MODULE_VALUE == appInfo.metaData.get(key)) {
                        modules.add(parseModule(key))
                        Timber.tag(TAG).d("Loaded Config module: $key")
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("Unable to find metadata to parse ConfigModule", e)
        }
        Timber.tag(TAG).d("Finished loading Config modules")
        return modules
    }

    private fun parseModule(className: String): ConfigModule {
        val clazz = try {
            Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException("Unable to find ConfigModule implementation", e)
        }

        val module = try {
            clazz.newInstance()
        } catch (e: InstantiationException) {
            throw RuntimeException("Unable to instantiate ConfigModule implementation for $clazz", e)
            // These can't be combined until API minimum is 19.
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Unable to instantiate ConfigModule implementation for $clazz", e)
        }
        if (module !is ConfigModule) {
            throw RuntimeException("Expected instanceof ConfigModule, but found: $module")
        }
        return module
    }
}