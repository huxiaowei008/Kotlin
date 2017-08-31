package com.modoutech.kotlin.other

import com.hxw.frame.utils.StringUtils
import okhttp3.*
import okio.Buffer
import timber.log.Timber
import java.io.IOException
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * 请求拦截器,主要打印返回结果
 * Created by hxw on 2017/8/18.
 */
class RequestInterceptor (private val handler: GlobalHttpHandler?) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        //打印请求信息
        Timber.tag(getTag(request, "Request_Info"))
                .w("Params:「 %s 」%nConnection :「 %s 」%nHeaders : %n「 %s 」",
                        if (request.body() != null) parseParams(request.body()!!) else "Null",
                        chain.connection(),
                        request.headers())
        val t1 = System.nanoTime()
        val originalResponse = chain.proceed(request)
        val t2 = System.nanoTime()

        val bodySize = if (originalResponse.body()?.contentLength() != -1L) {
            "${originalResponse.body()?.contentLength()}-byte"
        } else {
            "unknown-length"
        }

        //打印响应时间以及响应头
        Timber.tag(getTag(request, "Response_Info"))
                .w("Received response in [ %d-ms ] , [ %s ]%n%s",
                        TimeUnit.NANOSECONDS.toMillis(t2 - t1),
                        bodySize,
                        originalResponse.headers())

        //打印响应结果
        val bodyString = printResult(request, originalResponse.newBuilder().build())
        handler?.onHttpResultResponse(bodyString, chain, originalResponse)//这里可以比客户端提前一步拿到服务器返回的结果,可以做一些操作,比如token超时,重新获取
        return originalResponse

    }

    private fun getTag(request: Request, tag: String) = String.format("[%s]「 %s 」>>> %s",
            request.method(), request.url().toString(), tag)

    private fun parseParams(body: RequestBody): String {
        if (isParseable(body.contentType())) {
            try {
                val requestBuffer = Buffer()
                body.writeTo(requestBuffer)
                var charset = Charset.forName("UTF-8")
                charset = body.contentType()?.charset(charset)
                return URLDecoder.decode(requestBuffer.readString(charset), convertCharset(charset))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        return "This params isn't parsed"
    }

    private fun isParseable(mediaType: MediaType?) = mediaType?.toString()?.toLowerCase()?.contains("text") ?: false || isJson(mediaType)
            || isXml(mediaType) || isHtml(mediaType) || isForm(mediaType)


    private fun isJson(mediaType: MediaType?) = mediaType?.toString()?.toLowerCase()?.contains("json") ?: false

    private fun isXml(mediaType: MediaType?) = mediaType?.toString()?.toLowerCase()?.contains("xml") ?: false

    private fun isHtml(mediaType: MediaType?) = mediaType?.toString()?.toLowerCase()?.contains("html") ?: false

    private fun isForm(mediaType: MediaType?) = mediaType?.toString()?.toLowerCase()?.contains("x-www-form-urlencoded") ?: false

    private fun convertCharset(charset: Charset): String {
        val s = charset.toString()
        val i = s.indexOf("[")
        if (i == -1) {
            return s
        }
        return s.substring(i + 1, s.length - 1)
    }

    /**
     * 打印响应结果
     */
    private fun printResult(request: Request, response: Response): String {
        //读取服务器返回的结果
        val responseBody = response.body()
        var bodyString = ""
        val mediaType = responseBody?.contentType()
        if (isParseable(mediaType)) {
            try {
                val source = responseBody?.source()
                source!!.request(Long.MAX_VALUE)
                val buffer = source.buffer()
                //获取content的压缩类型
                val encoding = response.headers().get("Content-Encoding")

                val clone = buffer.clone()
                //解析response content
                bodyString = parseContent(responseBody, encoding, clone)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Timber.tag(getTag(request, "Response_Result")).w(
                    when {
                        isJson(mediaType) -> StringUtils.jsonFormat(bodyString)
                        isXml(mediaType) -> StringUtils.xmlFormat(bodyString)
                        else -> bodyString
                    })
        } else {
            Timber.tag(getTag(request, "Response_Result"))
                    .w("This result isn't parsed")
        }

        return bodyString
    }

    private fun parseContent(responseBody: ResponseBody, encoding: String?, clone: Buffer): String {
        val contentType = responseBody.contentType()
        var charset = Charset.forName("UTF-8")
        charset = contentType?.charset(charset)

        return if (encoding != null && encoding.equals("gzip", true)) {//content使用gzip压缩
            ZipUtils.decompressForGzip(clone.readByteArray(), charset)//解压
        } else if (encoding != null && encoding.equals("zlib", true)) {//content使用zlib压缩
            ZipUtils.decompressToStringForZlib(clone.readByteArray(), charset)//解压
        } else {
            clone.readString(charset)
        }
    }
}