package innovolt.volt.runtime

import innovolt.volt.lexer.Location

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
    class Break(origin: Location) : Redirect(origin)
    
    class Continue(origin: Location) : Redirect(origin)
    
    class Throw(origin: Location, val value: Result<*>) : Redirect(origin)
    
    class Return(origin: Location, val value: Result<*>) : Redirect(origin)
}