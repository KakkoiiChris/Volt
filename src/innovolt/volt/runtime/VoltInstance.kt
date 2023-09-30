package innovolt.volt.runtime

import innovolt.volt.util.VoltError
import innovolt.volt.util.toInvoke
import innovolt.volt.util.toValue

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    ClassInstance.kt
 *
 * Created: Thursday, November 10, 2022, 15:05:47
 *
 * @author Christian Bryce Alexander
 */
class VoltInstance(val `class`: VoltClass, val runtime: Runtime) : Memory.Scope(`class`.scope) {
    val link get() = this["\$link"] as? Result.ClassLink

    override fun toString(): String {
        var result = this["toString"]

        val function = when (result) {
            is Result.Function -> result

            else        -> return `class`.name.value
        }

        val target = function.toValue()

        val invoke = target.toInvoke()

        result = runtime.visit(invoke)

        val string = result as? Result.String ?: VoltError.invalidReturnType(result, function.value)

        return string.value
    }
}