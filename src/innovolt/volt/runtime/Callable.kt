package innovolt.volt.runtime

import innovolt.volt.parser.Expr

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Callable.kt
 *
 * Created: Saturday, November 12, 2022, 00:20:41
 *
 * @author Christian Bryce Alexander
 */
sealed interface Callable {
    val params: List<Expr.Name>
}