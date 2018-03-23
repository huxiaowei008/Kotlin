package com.hxw.frame.utils

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

}

/**
 * json 格式化
 */
fun String.jsonFormat(): String {
    if (this.isEmpty()) {
        return "json 数据为空!"
    } else {
        return try {
            when {
                this.startsWith("{") -> {
                    val jsonObject = JSONObject(this)
                    jsonObject.toString(4)
                }
                this.startsWith("[") -> {
                    val jsonArray = JSONArray(this)
                    jsonArray.toString(4)
                }
                else -> this
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            e.cause?.message + System.getProperty("line.separator") + this
        }
    }
}

/**
 * xml 格式化
 */
fun String.xmlFormat(): String {
    if (this.isEmpty()) {
        return "xml 数据为空!"
    } else {
        return try {
            val xmlInput = StreamSource(StringReader(this))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
            transformer.transform(xmlInput, xmlOutput)
            xmlOutput.writer.toString().replaceFirst(">", ">" + System.getProperty("line.separator"))
        } catch (e: TransformerException) {
            e.printStackTrace()
            e.cause?.message + System.getProperty("line.separator") + this
        }
    }
}

/**
 * 解析url的参数以map返回
 */
fun String.urlRequestFormat(): Map<String, String> =
        try {
            val mapRequest = hashMapOf<String, String>()
            val url = URL(this)
            val query = url.query
            val arrSplit = query.split("&")
            arrSplit
                    .asSequence()
                    .map {
                        //解析出键值
                        it.split("=")
                    }
                    .forEach {
                        mapRequest.put(it[0], it[1])
                    }
            mapRequest
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            hashMapOf()
        } catch (e: IndexOutOfBoundsException) {
            throw RuntimeException("解析后数组越界,url格式有问题")
        }

/**
 * String数组变String,用|隔开
 */
fun ArrayList<String>.arrayStringToString(): String {
    val regularEx = "|"
    var str = ""
    for ((index, value) in this.withIndex()) {
        str += value
        if (index != this.lastIndex) {
            str += regularEx
        }
    }
    return str
}
