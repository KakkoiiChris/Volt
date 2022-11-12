package innovolt.volt.runtime

import innovolt.volt.lexer.Location
import innovolt.volt.parser.Expr
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
class FunctionInstance(val location: Location, val name: Expr.Name, val params: List<Expr.Name>, val body: Stmt, val scope: Memory.Scope) {
    constructor(function: Stmt.Function, scope: Memory.Scope) : this(function.location, function.name, function.params, function.body, scope)
}