package innovolt.volt.runtime

import innovolt.volt.lexer.Location
import innovolt.volt.parser.Expr
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
abstract class VoltValue<X>(val value: X) {
    companion object {
        fun of(x: Any): VoltValue<*>? =
            when (x) {
                is VoltValue<*>   -> x

                is kotlin.Boolean -> Boolean(x)

                is Double         -> Number(x)

                is kotlin.String  -> String(x)

                is VoltList       -> List(x)

                is VoltMap        -> Map(x)

                is VoltFunction   -> Function(x)

                is VoltClass      -> Class(x)

                is VoltInstance   -> Instance(x)

                VoltNull          -> Null

                kotlin.Unit       -> Unit

                else              -> null
            }
    }

    open val size get() = Number(1.0)

    fun map(transform: (X) -> Any) =
        of(transform(value))!!

    fun toValue() =
        Expr.Value(Location.none, this)

    open fun toBoolean() = true

    override fun toString() = value.toString()

    class Boolean(value: kotlin.Boolean) : VoltValue<kotlin.Boolean>(value) {
        companion object {
            val `true` = Boolean(true)
            val `false` = Boolean(false)
        }

        override fun toBoolean() = value
    }

    class Number(value: Double) : VoltValue<Double>(value) {
        override fun toBoolean() = value != 0.0

        override fun toString() =
            if (value == floor(value))
                value.toInt().toString()
            else
                value.toString()
    }

    class String(value: kotlin.String) : VoltValue<kotlin.String>(value) {
        override val size get() = Number(value.length.toDouble())

        override fun toBoolean() = value.isNotEmpty()
    }

    class List(value: VoltList) : VoltValue<VoltList>(value) {
        override val size get() = Number(value.size.toDouble())

        override fun toBoolean() = value.isNotEmpty()
    }

    class Map(value: VoltMap) : VoltValue<VoltMap>(value) {
        override val size get() = Number(value.size.toDouble())

        override fun toBoolean() = value.isNotEmpty()
    }

    class Function(value: VoltFunction) : VoltValue<VoltFunction>(value)

    class Class(value: VoltClass) : VoltValue<VoltClass>(value)

    class Instance(value: VoltInstance) : VoltValue<VoltInstance>(value)

    class ClassLink(value: Any) : VoltValue<Any>(value)

    data object Null : VoltValue<VoltNull>(VoltNull) {
        override fun toBoolean() = false
    }

    data object Unit : VoltValue<kotlin.Unit>(kotlin.Unit)
}