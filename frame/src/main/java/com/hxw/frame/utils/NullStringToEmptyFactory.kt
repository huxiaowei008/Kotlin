package com.hxw.frame.utils

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * gson中null的String转换为""的工具
 * Created by hxw on 2017/9/1.
 */
class NullStringToEmptyFactory<T> : TypeAdapterFactory {

    @SuppressWarnings("unchecked")
    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T>? {
        val rawType = type?.rawType
        if (rawType == String::class) {
            return StringNullAdapter() as TypeAdapter<T>
        }
        return null
    }
}

class StringNullAdapter : TypeAdapter<String>() {
    override fun read(reader: JsonReader?): String {
        if (reader?.peek() == JsonToken.NULL) {
            reader.nextNull()
            return ""
        }
        return this.read(reader)
    }

    override fun write(writer: JsonWriter?, value: String?) {
        if (value == null) {
            writer?.nullValue()
        } else {
            this.write(writer, value)
        }
    }

}