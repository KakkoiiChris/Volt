package innovolt.volt.runtime

import innovolt.volt.lexer.Location
import innovolt.volt.linker.Linker
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
class VoltFunction(
    val location: Location,
    val name: Expr.Name,
    override val params: List<Expr.Name>,
    val body: Stmt,
    val scope: Memory.Scope,
    val link: Linker.Function?,
) : Callable {
    constructor(
        function: Stmt.Function,
        scope: Memory.Scope,
        link: Linker.Function?,
    ) : this(
        function.location,
        function.name,
        function.params,
        function.body,
        scope,
        link
    )
}