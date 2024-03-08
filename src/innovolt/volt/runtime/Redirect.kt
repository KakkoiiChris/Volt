package innovolt.volt.runtime

import innovolt.volt.lexer.Location
import innovolt.volt.parser.Expr

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Redirect.kt
 *
 * Created: Saturday, November 12, 2022, 00:12:52
 *
 * @author Christian Bryce Alexander
 */
sealed class Redirect(val origin: Location) : Throwable() {
    class Break(origin: Location, val label:Expr.Name) : Redirect(origin)
    
    class Continue(origin: Location, val label:Expr.Name) : Redirect(origin)
    
    class Throw(origin: Location, val value: VoltValue<*>) : Redirect(origin)
    
    class Return(origin: Location, val value: VoltValue<*>) : Redirect(origin)
}