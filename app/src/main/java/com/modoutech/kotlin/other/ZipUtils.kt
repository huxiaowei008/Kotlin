package com.modoutech.kotlin.other

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.zip.GZIPInputStream
import java.util.zip.Inflater

/**
 * 压缩包工具
 * Created by hxw on 2017/8/18.
 */
object ZipUtils {

    /**
     * zlib decompress 2 String
     * 解压zlib
     */
    fun decompressToStringForZlib(bytesToDecompress: ByteArray, charset: Charset = Charsets.UTF_8): String {
        val bytesDecompressed = decompressForZlib(bytesToDecompress)

        return try {
            String(bytesDecompressed)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            ""
        }


    }

    /**
     * zlib decompress 2 byte
     * 解压zlib
     */
    fun decompressForZlib(bytesToDecompress: ByteArray): ByteArray {
        var returnValues = ByteArray(0)
        val inflater = Inflater()
        inflater.reset()
        inflater.setInput(bytesToDecompress, 0, bytesToDecompress.size)
        val o = ByteArrayOutputStream(bytesToDecompress.size)
        val buf = ByteArray(bytesToDecompress.size)
        try {
            while (!inflater.finished()) {
                val numberOfBytesDecompressedThisTime = inflater.inflate(buf)
                o.write(buf, 0, numberOfBytesDecompressedThisTime)
            }
            returnValues = o.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            o.close()
        }
        inflater.end()
        return returnValues
    }

    /**
     * gzip decompress 2 string
     * 解压gzip
     */
    fun decompressForGzip(compressed: ByteArray, charset: Charset = Charsets.UTF_8): String {
        val BUFFER_SIZE = compressed.size
        val bis = ByteArrayInputStream(compressed)
        val gis = GZIPInputStream(bis, BUFFER_SIZE)
        try {

            val str = StringBuilder()
            val data = ByteArray(BUFFER_SIZE)
            var bytesRead = 0
            do {
                str.append(String(data, 0, bytesRead, charset))
                bytesRead = gis.read(data)
            } while (bytesRead != -1)
            return str.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            bis.close()
            gis.close()
        }
        return ""
    }
}