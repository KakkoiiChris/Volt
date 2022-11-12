package innovolt.volt.runtime

import innovolt.volt.lexer.Location
import innovolt.volt.parser.Expr
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
    val location: Location,
    val name: Expr.Name,
    override val params: List<Expr.Name>,
    val init: Stmt.Block,
    val scope: Memory.Scope,
) : Callable {
    constructor(`class`: Stmt.Class, scope: Memory.Scope) : this(`class`.location, `class`.name, `class`.params, `class`.init, scope)
}