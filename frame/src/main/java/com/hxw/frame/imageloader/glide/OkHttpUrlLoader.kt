package com.hxw.frame.imageloader.glide

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import okhttp3.Call
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * A simple model loader for fetching media over http/https using OkHttp.
 * Created by hxw on 2017/9/15.
 */
class OkHttpUrlLoader(private val client: Call.Factory) : ModelLoader<GlideUrl, InputStream> {

    override fun handles(model: GlideUrl?): Boolean = true


    override fun buildLoadData(model: GlideUrl, width: Int, height: Int, options: Options?): ModelLoader.LoadData<InputStream>? =
            ModelLoader.LoadData(model, OkHttpStreamFetcher(client, model))

    /**
     * The default factory for {@link OkHttpUrlLoader}s.
     * Constructor for a new Factory that runs requests using given client.
     *
     * @param client this is typically an instance of {@code OkHttpClient}.
     */
    class Factory(private val client: Call.Factory) : ModelLoaderFactory<GlideUrl, InputStream> {
        companion object {
            @Volatile
            private var internalClient: Call.Factory? = null

            private fun internalClient(): Call.Factory {
                if (internalClient == null) {
                    synchronized(Factory::class.java) {
                        if (internalClient == null) {
                            internalClient = OkHttpClient()
                        }
                    }
                }
                return internalClient!!
            }
        }

        /**
         * Constructor for a new Factory that runs requests using a static singleton client.
         */
        constructor() : this(internalClient())


        override fun teardown() {
            // Do nothing, this instance doesn't own the client.
        }

        override fun build(multiFactory: MultiModelLoaderFactory?): ModelLoader<GlideUrl, InputStream> =
                OkHttpUrlLoader(client)

    }
}