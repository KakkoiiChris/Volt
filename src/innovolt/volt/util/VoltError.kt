package innovolt.volt.util

import innovolt.volt.lexer.Location
import innovolt.volt.lexer.Token
import kotlin.reflect.KClass

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
        private fun forLexer(message: String, location: Location): Nothing =
            throw VoltError("Lexer", message, location)
        
        fun invalidCharacter(invalid: Char, expected: Char, location: Location): Nothing =
            forLexer("Character '$invalid' is invalid; expected $expected", location)
        
        fun invalidString(invalid: String, expected: String, location: Location): Nothing =
            forLexer("String '$invalid' is invalid; expected $expected", location)
        
        fun invalidHexadecimal(invalid: Char, location: Location): Nothing =
            forLexer("Character '$invalid' is not a valid hexadecimal digit", location)
        
        fun invalidEscape(invalid: Char, location: Location): Nothing =
            forLexer("Character escape '\\$invalid' is invalid", location)
        
        fun unclosedString(location: Location): Nothing =
            forLexer("String is unclosed", location)
        
        fun illegalCharacter(illegal: Char, location: Location): Nothing =
            forLexer("Character '$illegal' is illegal", location)
        
        private fun forParser(message: String, location: Location): Nothing =
            throw VoltError("Parser", message, location)
        
        fun invalidTokenType(invalid: Token.Type, expected: Token.Type, location: Location): Nothing =
            forParser("Token type '$invalid' is invalid; expected $expected", location)
        
        fun invalidAssignmentTarget(location: Location): Nothing =
            forParser("Can only assign to names, indexers, and member accessors", location)
        
        fun invalidTerminal(invalid: Token.Type, location: Location): Nothing =
            forParser("Terminal expression beginning with token type '$invalid' is invalid; expected a value, a name, a '(', a '[', or a '{'", location)
        
        fun invalidLambdaParameter(location: Location): Nothing =
            forParser("Lambda parameter must be a valid name", location)
        
        fun <X : Token.Type> invalidTokenType(invalid: Token.Type, expected: KClass<X>, location: Location): Nothing =
            forParser("Token type '$invalid is invalid; expected $expected", location)
        
        private fun forRuntime(message: String, location: Location): Nothing =
            throw VoltError("Runtime", message, location)
        
        fun unhandledBreak(location: Location):Nothing=
            forRuntime("Unhandled break statement", location)
    
        fun unhandledContinue(location: Location):Nothing=
            forRuntime("Unhandled break statement", location)
        
        private fun forLinker(message: String, location: Location): Nothing =
            throw VoltError("Runtime", message, location)
    }
}