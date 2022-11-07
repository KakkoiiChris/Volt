package innovolt.volt.util

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
class VoltError(stage: String, msg: String, loc: Location) : Exception("Error @ $stage: $msg ($loc)") {
    companion object {
        fun forLexer(msg: String, loc: Location): Nothing =
            throw HyperError("Lexer", msg, loc)
        
        fun forParser(msg: String, loc: Location): Nothing =
            throw HyperError("Parser", msg, loc)
        
        fun forScript(msg: String, loc: Location): Nothing =
            throw HyperError("Script", msg, loc)
        
        fun failure(msg: String): Nothing =
            throw HyperError("All", msg, Location.none)
    }
}