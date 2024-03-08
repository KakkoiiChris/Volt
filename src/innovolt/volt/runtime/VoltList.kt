package innovolt.volt.runtime

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    ListInstance.kt
 *
 * Created: Thursday, November 10, 2022, 15:04:45
 *
 * @author Christian Bryce Alexander
 */
class VoltList(list: MutableList<VoltValue<*>> = mutableListOf()) : MutableList<VoltValue<*>> by list {
    override fun toString() =
        joinToString(
            prefix = "[ ",
            separator = ", ",
            postfix = " ]"
        )
}