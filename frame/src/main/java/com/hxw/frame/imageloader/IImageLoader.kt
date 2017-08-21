package com.hxw.frame.imageloader

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.widget.ImageView

/**
 * 图片加载接口
 * Created by hxw on 2017/8/18.
 */
interface IImageLoader {
    /**
     * 用于显示网络图或者文件夹中的图片
     *
     * @param img 目标imageView
     * @param uri 文件路径或者网络地址
     */
    fun displayUri(@NonNull img: ImageView, @NonNull uri: String)

    /**
     * 用于显示资源中的图片
     *
     * @param img 目标imageView
     * @param res 资源ID
     */
    fun displayRes(@NonNull img: ImageView, @DrawableRes res: Int)

    /**
     * 清除内存缓存
     * @param context 上下文
     */
    fun clear(context: Context)
}