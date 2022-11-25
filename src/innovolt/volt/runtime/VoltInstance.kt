package innovolt.volt.runtime

import innovolt.volt.lexer.Location
import innovolt.volt.parser.Expr
import innovolt.volt.util.toName
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
    override fun toString(): String {
        val target = Expr.GetMember(Location.none, Result.Instance(this).toValue(), "toString".toName())
        
        val invoke = Expr.Invoke(Location.none, target, emptyList())
        
        val result = runtime.visit(invoke)
        
        return (result as? Result.String ?: TODO()).value
    }
}