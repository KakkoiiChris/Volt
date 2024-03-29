package innovolt.volt.lexer

import innovolt.volt.runtime.VoltValue

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Token.kt
 *
 * Created: Friday, November 04, 2022, 00:40:34
 *
 * @author Christian Bryce Alexander
 */
data class Token(val location: Location, val type: Type) {
    sealed interface Type {
        enum class Keyword : Type {
            IMPORT,
            CLASS,
            FUNCTION,
            IF,
            ELSE,
            WHILE,
            DO,
            FOR,
            TRY,
            CATCH,
            FINALLY,
            BREAK,
            CONTINUE,
            THROW,
            RETURN,
            OR,
            AND,
            NOT
        }
        
        enum class Symbol(val value: String) : Type {
            EQUAL_SIGN("="),
            PLUS_EQUAL("+="),
            DASH_EQUAL("-="),
            STAR_EQUAL("*="),
            SLASH_EQUAL("/="),
            PERCENT_EQUAL("%="),
            CARET_EQUAL("^="),
            QUESTION("?"),
            DOUBLE_EQUAL("=="),
            LESS_GREATER("<>"),
            LESS_SIGN("<"),
            LESS_EQUAL_SIGN("<="),
            GREATER_SIGN(">"),
            GREATER_EQUAL_SIGN(">="),
            AMPERSAND("&"),
            PLUS("+"),
            DASH("-"),
            STAR("*"),
            SLASH("/"),
            PERCENT("%"),
            CARET("^"),
            POUND("#"),
            DOLLAR("$"),
            DOUBLE_PLUS("++"),
            DOUBLE_DASH("--"),
            DOT("."),
            COLON(":"),
            DOUBLE_COLON("::"),
            ARROW("->"),
            AT("@"),
            LEFT_PAREN("("),
            RIGHT_PAREN(")"),
            LEFT_SQUARE("["),
            RIGHT_SQUARE("]"),
            LEFT_BRACE("{"),
            RIGHT_BRACE("}"),
            COMMA(","),
            SEMICOLON(";"),
            END_OF_FILE("0");
        }
        
        data class Value(val value: VoltValue<*>) : Type
        
        data class Identifier(val value: String) : Type
    }
}