package innovolt.volt.util

import innovolt.volt.lexer.Location
import innovolt.volt.parser.Expr
import innovolt.volt.runtime.Result

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Util.kt
 *
 * Created: Tuesday, November 22, 2022, 22:12:58
 *
 * @author Christian Bryce Alexander
 */

fun String.toName() =
    Expr.Name(Location.none, this)

fun Result<*>.toValue() =
    Expr.Value(Location.none, this)