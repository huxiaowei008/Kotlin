package com.hxw.frame.integration

import android.content.Context
import android.content.pm.PackageManager
import timber.log.Timber
import java.lang.reflect.InvocationTargetException

/**
 * Manifest解析工具
 * @author hxw
 * @date 2017/8/19
 */
class ManifestParser(private val context: Context) {

    private val TAG = "ManifestParser"
    private val MODULE_VALUE = "ConfigModule"


    fun parse(): MutableList<ConfigModule> {
        Timber.tag(TAG).d("Loading Config modules")
        val modules = mutableListOf<ConfigModule>()
        try {
            val appInfo = context.packageManager
                    .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            if (appInfo.metaData == null) {
                Timber.tag(TAG).d("Got null app info metadata")
                return modules
            }

            Timber.tag(TAG).v("Got app info metadata: ${appInfo.metaData}")
            for (key: String in appInfo.metaData.keySet()) {
                if (MODULE_VALUE == appInfo.metaData.get(key)) {
                    modules.add(parseModule(key))
                    Timber.tag(TAG).d("Loaded Config module: $key")
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
            clazz.getDeclaredConstructor().newInstance()
            // These can't be combined until API minimum is 19.
        } catch (e: InstantiationException) {
            throwInstantiateGlideModuleException(clazz, e)
        } catch (e: IllegalAccessException) {
            throwInstantiateGlideModuleException(clazz, e)
        } catch (e: NoSuchMethodException) {
            throwInstantiateGlideModuleException(clazz, e)
        } catch (e: InvocationTargetException) {
            throwInstantiateGlideModuleException(clazz, e)
        }
        if (module !is ConfigModule) {
            throw RuntimeException("Expected instance of ConfigModule, but found: $module")
        }
        return module
    }

    private fun throwInstantiateGlideModuleException(clazz: Class<*>, e: Exception) {
        throw RuntimeException("Unable to instantiate ConfigModule implementation for " + clazz, e)
    }
}