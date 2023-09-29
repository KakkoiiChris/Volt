package innovolt.volt.runtime

import kotlin.math.floor

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Result.kt
 *
 * Created: Thursday, November 10, 2022, 15:03:49
 *
 * @author Christian Bryce Alexander
 */
interface Result<X> {
    companion object {
        fun of(x: Any) =
            when (x) {
                is Result<*>               -> x

                is kotlin.Boolean          -> Boolean(x)

                is Double                  -> Number(x)

                is kotlin.String           -> String(x)

                is VoltList                -> List(x)

                is VoltMap                 -> Map(x)

                is VoltFunction            -> Function(x)

                is VoltClass               -> Class(x)

                is VoltInstance            -> Instance(x)

                innovolt.volt.runtime.Null -> Null

                kotlin.Unit                -> Unit

                else                       -> null
            }
    }

    val value: X

    class Boolean(override val value: kotlin.Boolean) : Result<kotlin.Boolean> {
        override fun toString() = value.toString()
    }

    class Number(override val value: Double) : Result<Double> {
        override fun toString() =
            if (value == floor(value))
                value.toInt().toString()
            else
                value.toString()
    }

    class String(override val value: kotlin.String) : Result<kotlin.String> {
        override fun toString() = value
    }

    class List(override val value: VoltList) : Result<VoltList> {
        override fun toString() = value.joinToString(prefix = "[ ", separator = ", ", postfix = " ]")
    }

    class Map(override val value: VoltMap) : Result<VoltMap> {
        override fun toString() = value.entries.joinToString(prefix = "[ ", separator = ", ", postfix = " ]") { (k, v) -> "$k : $v" }
    }

    class Function(override val value: VoltFunction) : Result<VoltFunction> {
        override fun toString() = value.toString()
    }

    class Class(override val value: VoltClass) : Result<VoltClass> {
        override fun toString() = value.toString()
    }

    class Instance(override val value: VoltInstance) : Result<VoltInstance> {
        override fun toString() = value.toString()
    }

    class ClassLink(override val value: Any) : Result<Any> {
        override fun toString() = value.toString()
    }

    object Null : Result<innovolt.volt.runtime.Null> {
        override val value = innovolt.volt.runtime.Null

        override fun toString() = value.toString()
    }

    object Unit : Result<kotlin.Unit> {
        override val value = kotlin.Unit

        override fun toString() = value.toString()
    }
}