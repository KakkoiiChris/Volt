package innovolt.volt.runtime

import innovolt.volt.linker.Linker
import innovolt.volt.parser.Stmt

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    FunctionInstance.kt
 *
 * Created: Thursday, November 10, 2022, 15:32:02
 *
 * @author Christian Bryce Alexander
 */
class VoltFunction(
    val function: Stmt.Function,
    val scope: Memory.Scope,
    val link: Linker.Function?,
) : Callable {
    val location get() = function.location
    val path get() = function.path
    val name get() = function.name
    override val params get() = function.params
    val body get() = function.body

    override fun toString() =
        "function ${function.path}(${params.joinToString(separator = ", ")})"
}