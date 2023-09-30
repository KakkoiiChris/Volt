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
abstract class Result<X>(val value: X) {
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

    override fun toString() = value.toString()

    class Boolean(value: kotlin.Boolean) : Result<kotlin.Boolean>(value)

    class Number(value: Double) : Result<Double>(value) {
        override fun toString() =
            if (value == floor(value))
                value.toInt().toString()
            else
                value.toString()
    }

    class String(value: kotlin.String) : Result<kotlin.String>(value)

    class List(value: VoltList) : Result<VoltList>(value)

    class Map(value: VoltMap) : Result<VoltMap>(value)

    class Function(value: VoltFunction) : Result<VoltFunction>(value)

    class Class(value: VoltClass) : Result<VoltClass>(value)

    class Instance(value: VoltInstance) : Result<VoltInstance>(value)

    class ClassLink(value: Any) : Result<Any>(value)

    object Null : Result<innovolt.volt.runtime.Null>(innovolt.volt.runtime.Null)

    object Unit : Result<kotlin.Unit>(kotlin.Unit)
}