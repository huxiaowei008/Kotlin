package com.hxw.frame.base

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by hxw on 2017/8/28.
 */
object DelegatesExt {
    fun <T> notNullSingleValue() = NotNullSingleValueVar<T>()

}

class NotNullSingleValueVar<T>() : ReadWriteProperty<Any?, T> {

    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
            value ?: throw IllegalStateException("${property.name} not initialized")

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value
        else throw IllegalStateException("${property.name} already initialized")
    }
}