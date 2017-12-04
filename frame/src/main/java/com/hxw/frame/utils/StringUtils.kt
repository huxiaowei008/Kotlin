package com.hxw.frame.utils

import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import java.net.MalformedURLException
import java.net.URL
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * String相关工具类
 * @author hxw
 * @date 2017/8/19
 */
object StringUtils {

    private val LINE_SEPARATOR = System.getProperty("line.separator")
    /**
     * json 格式化
     */
    fun jsonFormat(json: String): String {
        if (TextUtils.isEmpty(json)) {
            return "json 数据为空!"
        }

        return try {
            when {
                json.startsWith("{") -> {
                    val jsonObject = JSONObject(json)
                    jsonObject.toString(4)
                }
                json.startsWith("[") -> {
                    val jsonArray = JSONArray(json)
                    jsonArray.toString(4)
                }
                else -> json
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            e.cause?.message + LINE_SEPARATOR + json
        }

    }

    /**
     * xml 格式化
     */
    fun xmlFormat(xml: String): String {
        if (TextUtils.isEmpty(xml)) {
            return "xml 数据为空!"
        }
        return try {
            val xmlInput = StreamSource(StringReader(xml))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
            transformer.transform(xmlInput, xmlOutput)
            xmlOutput.writer.toString().replaceFirst(">", ">" + LINE_SEPARATOR)
        } catch (e: TransformerException) {
            e.printStackTrace()
            e.cause?.message + LINE_SEPARATOR + xml
        }
    }

    /**
     * 解析url的参数以map返回
     */
    fun urlRequestFormat(urlString: String): Map<String, String> =
            try {
                val mapRequest = hashMapOf<String, String>()
                val url = URL(urlString)
                val query = url.query
                val arrSplit = query.split("&")

                arrSplit
                        .asSequence()
                        .map {
                            //解析出键值
                            it.split("=")
                        }
                        .forEach {
                            if (!it[0].isNullOrEmpty() && !it[1].isNullOrEmpty()) {
                                mapRequest.put(it[0], it[1])
                            } else if (!it[0].isNullOrEmpty()) {
                                mapRequest.put(it[0], "")
                            }
                        }

                mapRequest
            } catch (e: MalformedURLException) {
                e.printStackTrace()
                hashMapOf()
            }

    /**
     * String数组变String,用|隔开
     */
    fun arrayStringToString(array: ArrayList<String>): String {
        val regularEx = "|"
        var str = ""
        for ((index, value) in array.withIndex()) {
            str += value
            if (index != array.lastIndex) {
                str += regularEx
            }
        }
        return str
    }

}