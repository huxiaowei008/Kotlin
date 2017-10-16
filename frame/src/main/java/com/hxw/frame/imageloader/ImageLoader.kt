package com.hxw.frame.imageloader

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.widget.ImageView
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 图片加载
 * @author hxw
 * @date 2017/8/19
 */
@Singleton
class ImageLoader @Inject constructor(private val mLoader: IImageLoader) {

    fun displayUri(@NonNull img: ImageView, @NonNull uri: String) = mLoader.displayUri(img, uri)

    fun displayRes(@NonNull img: ImageView, @DrawableRes res: Int) = mLoader.displayRes(img, res)

    fun clear(context: Context) = mLoader.clear(context)

}