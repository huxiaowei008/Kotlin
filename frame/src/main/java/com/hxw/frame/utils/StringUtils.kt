package com.hxw.frame.utils

import android.text.TextUtils
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource

/**
 * String相关工具类
 * Created by hxw on 2017/8/19.
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
}