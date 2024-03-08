package innovolt.volt.runtime

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    MapInstance.kt
 *
 * Created: Thursday, November 10, 2022, 15:04:57
 *
 * @author Christian Bryce Alexander
 */
class VoltMap : MutableMap<String, VoltValue<*>> by mutableMapOf() {
    override fun toString() =
        entries
            .joinToString(
                prefix = "[ ",
                separator = ", ",
                postfix = " ]"
            ) { (k, v) -> "$k : $v" }
}