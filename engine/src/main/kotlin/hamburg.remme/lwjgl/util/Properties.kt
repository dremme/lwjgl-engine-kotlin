package hamburg.remme.lwjgl.util

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private object UninitializedValue

internal fun <T> lateVal() = LateVal<T>()

internal class LateVal<T> : ReadWriteProperty<Any, T> {

    private var value: Any? = UninitializedValue

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        if (value === UninitializedValue) throw RuntimeException("LateVal not initialized.")
        @Suppress("UNCHECKED_CAST")
        return value as T
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        if (this.value !== UninitializedValue) throw RuntimeException("LateVal already initialized.")
        this.value = value
    }


}
