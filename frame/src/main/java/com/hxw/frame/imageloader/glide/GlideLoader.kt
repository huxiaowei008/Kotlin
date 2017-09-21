package com.hxw.frame.imageloader.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hxw.frame.R
import com.hxw.frame.imageloader.IImageLoader

/**
 * glide的图片加载实现
 * Created by hxw on 2017/8/19.
 */
class GlideLoader:IImageLoader {
    override fun displayUri(img: ImageView, uri: String) {
            GlideApp.with(img.context)
                    .load(uri)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
//                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(img)
    }

    override fun displayRes(img: ImageView, res: Int) {
            GlideApp.with(img.context)
                    .load(res)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
//                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(img)
    }

    override fun clear(context: Context) {
       GlideApp.get(context).clearMemory()
    }
}