package com.hxw.frame.imageloader.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.hxw.frame.base.delegate.AppDelegate
import java.io.InputStream

/**
 * Glide的配置类
 * @author hxw
 * @date 2017/9/25
 */
@Excludes(com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule::class)
@GlideModule
class GlideConfiguration : AppGlideModule() {

    override fun isManifestParsingEnabled(): Boolean = false

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        //图片磁盘缓存文件最大值为100Mb
        val maxSize: Long = 100 * 1024 * 1024
        val calculator = MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2f)
                .setBitmapPoolScreens(3f)
                .build()

        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))//内存缓存
                .setBitmapPool(LruBitmapPool(calculator.bitmapPoolSize.toLong()))//Bitmap 池
                .setDiskCache(DiskLruCacheFactory(AppDelegate.FRAME_COMPONENT.cacheFile().parent,
                        "Glide", maxSize))//磁盘缓存
                .setDiskCacheExecutor(GlideExecutor
                        .newDiskCacheExecutor(GlideExecutor.UncaughtThrowableStrategy.THROW))//未捕获异常策略 (UncaughtThrowableStrategy)
                .setSourceExecutor(GlideExecutor
                        .newSourceExecutor(GlideExecutor.UncaughtThrowableStrategy.THROW))//未捕获异常策略 (UncaughtThrowableStrategy)

//        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);//Bitmap格式变化,RGB_565或ARGB_8888
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        //Glide默认使用HttpURLConnection做网络请求,
        //用了OkHttpUrlLoader.Factory()后会换成OKHttp请求，在这放入我们自己创建的OkHttp
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader
                .Factory(AppDelegate.FRAME_COMPONENT.okHttpClient()))
    }

}