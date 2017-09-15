package com.hxw.frame.imageloader.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.hxw.frame.base.delegate.AppDelegate
import com.hxw.frame.utils.FileUtils
import java.io.File
import java.io.InputStream

/**
 * glide的配置类
 * Created by hxw on 2017/9/15.
 */
@GlideModule
class GlideConfiguration : AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean = false

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setDiskCache {
            // Careful: the external cache directory doesn't enforce permissions
            //图片磁盘缓存文件最大值为100Mb
            val IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024
            val cacheDirectory = File(AppDelegate.appComponent.cacheFile(), "Glide")
            DiskLruCacheWrapper.get(FileUtils.makeDirs(cacheDirectory),
                    IMAGE_DISK_CACHE_MAX_SIZE)
        }

        val calculator = MemorySizeCalculator.Builder(context).build()

        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize

        val customMemoryCacheSize = 1.2 * defaultMemoryCacheSize
        val customBitmapPoolSize = 1.2 * defaultBitmapPoolSize

        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize.toInt()))
                .setBitmapPool(LruBitmapPool(customBitmapPoolSize.toInt()))
//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);//Bitmap格式变化,RGB_565或ARGB_8888
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        //Glide默认使用HttpURLConnection做网络请求,
        //用了OkHttpUrlLoader.Factory()后会换成OKhttp请求，在这放入我们自己创建的Okhttp
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader
                .Factory(AppDelegate.appComponent.okHttpClient()))
    }

}