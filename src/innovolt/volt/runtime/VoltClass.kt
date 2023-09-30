package innovolt.volt.runtime

import innovolt.volt.parser.Stmt

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    VoltClass.kt
 *
 * Created: Saturday, November 12, 2022, 00:19:02
 *
 * @author Christian Bryce Alexander
 */
class VoltClass(
    val `class`: Stmt.Class,
    val scope: Memory.Scope,
) : Callable {
    val location get() = `class`.location
    val name get() = `class`.name
    override val params get() = `class`.params
    val init get() = `class`.init

    override fun toString() =
        "class ${`class`.name}(${params.joinToString(separator = ", ")})"
}