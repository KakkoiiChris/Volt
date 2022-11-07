package innovolt.volt.util

import innovolt.volt.lexer.Location

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    VoltError.kt
 *
 * Created: Friday, November 04, 2022, 00:38:10
 *
 * @author Christian Bryce Alexander
 */
class VoltError(stage: String, msg: String, loc: Location) : Exception("Volt $stage Error: $msg!$loc") {
    companion object {
        fun forLexer(msg: String, loc: Location): Nothing =
            throw VoltError("Lexer", msg, loc)
        
        fun forParser(msg: String, loc: Location): Nothing =
            throw VoltError("Parser", msg, loc)
        
        fun forScript(msg: String, loc: Location): Nothing =
            throw VoltError("Script", msg, loc)
        
        fun failure(msg: String): Nothing =
            throw VoltError("All", msg, Location.none)
    }
}