package com.hxw.frame.imageloader.glide

import android.util.Log
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.HttpException
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.util.ContentLengthInputStream
import com.bumptech.glide.util.Synthetic
import okhttp3.Call
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import java.io.InputStream

/**
 * Fetches an {@link InputStream} using the okhttp3library.
 * @author hxw
 * @date 2017/9/15
 */
class OkHttpStreamFetcher(private val client: Call.Factory, private val url: GlideUrl) : DataFetcher<InputStream> {

    private val TAG = "OkHttpFetcher"
    @Synthetic
    var stream: InputStream? = null
    @Synthetic
    var responseBody: ResponseBody? = null
    @Volatile private var call: Call? = null
    override fun loadData(priority: Priority?, callback: DataFetcher.DataCallback<in InputStream>) {
        val requestBuilder = Request.Builder().url(url.toStringUrl())
        for ((key, value) in url.headers) {
            requestBuilder.addHeader(key, value)
        }
        val request = requestBuilder.build()

        call = client.newCall(request)
        call!!.enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                if (Log.isLoggable(TAG, Log.DEBUG)) {
                    Log.d(TAG, "OkHttp failed to obtain result", e)
                }
                callback.onLoadFailed(e)
            }

            override fun onResponse(call: Call, response: Response) {
                responseBody = response.body()
                if (response.isSuccessful) {
                    stream = ContentLengthInputStream.obtain(responseBody?.byteStream(),
                            responseBody!!.contentLength())
                    callback.onDataReady(stream)
                } else {
                    callback.onLoadFailed(HttpException(response.message(), response.code()))
                }
            }
        })
    }

    override fun cleanup() {
        try {
            stream?.close()
        } catch (e: IOException) {

        }
        responseBody?.close()

    }

    override fun cancel() {
        val local = call
        local?.cancel()
    }

    override fun getDataClass(): Class<InputStream> = InputStream::class.java

    override fun getDataSource(): DataSource = DataSource.REMOTE


}