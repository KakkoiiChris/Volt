package innovolt.volt.runtime

import java.util.function.IntFunction

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

    @Deprecated("'fun <T : Any!> toArray(generator: IntFunction<Array<(out) T!>!>!): Array<(out) T!>!' is deprecated. This declaration is redundant in Kotlin and might be removed soon.")
    override fun <T : Any?> toArray(generator: IntFunction<Array<out T?>?>): Array<out T?>? {
        return super.toArray(generator)
    }
}