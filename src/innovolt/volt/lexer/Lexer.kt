package innovolt.volt.lexer

import innovolt.volt.runtime.Null
import innovolt.volt.util.Source
import innovolt.volt.util.VoltError

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Lexer.kt
 *
 * Created: Monday, November 07, 2022, 00:08:27
 *
 * @author Christian Bryce Alexander
 */
class Lexer(private val source: Source) : Iterator<Token> {
    companion object {
        private const val NUL = '\u0000'
    }
    
    private val keywords = Token.Type.Keyword.values().associateBy { it.name.lowercase() }
    
    private val literals = listOf(true, false, Null, Unit).associateBy { it.toString() }
    
    private var position = 0
    private var row = 1
    private var column = 1
    
    override fun hasNext() =
        position <= source.text.length
    
    override fun next(): Token {
        while (!atEndOfFile()) {
            if (match { it.isWhitespace() }) {
                skipWhitespace()
                
                continue
            }
            
            if (match("//")) {
                skipLineComment()
                
                continue
            }
            
            if (match("/*")) {
                skipBlockComment()
                
                continue
            }
            
            return when {
                match { it.isDigit() }     -> number()
                
                match { it.isWordStart() } -> word()
                
                match { it in "'\"" }      -> string()
                
                else                       -> operator()
            }
        }
        
        return Token(here(), Token.Type.Symbol.END_OF_FILE)
    }
    
    private fun here() =
        Location(source.name, row, column)
    
    private fun peek(offset: Int = 0) =
        if (position + offset < source.text.length)
            source.text[position + offset]
        else
            NUL
    
    private fun look(size: Int) =
        buildString {
            repeat(size) { offset ->
                append(peek(offset))
            }
        }
    
    private fun match(char: Char, offset: Int = 0) =
        peek(offset) == char
    
    private fun match(offset: Int = 0, predicate: (Char) -> Boolean) =
        predicate(peek(offset))
    
    private fun match(string: String) =
        look(string.length) == string
    
    private fun step(count: Int = 1) {
        repeat(count) {
            if (match('\n')) {
                row++
                column = 1
            }
            else {
                column++
            }
            
            position++
        }
    }
    
    private fun skip(char: Char, offset: Int = 0): Boolean {
        if (match(char, offset)) {
            step()
            
            return true
        }
        
        return false
    }
    
    private fun skip(string: String): Boolean {
        if (match(string)) {
            step(string.length)
            
            return true
        }
        
        return false
    }
    
    private fun mustSkip(char: Char, offset: Int = 0) {
        if (!skip(char, offset)) {
            VoltError.invalidCharacter(peek(), char, here())
        }
    }
    
    @Suppress("SameParameterValue")
    private fun mustSkip(string: String) {
        if (!skip(string)) {
            VoltError.invalidString(look(string.length), string, here())
        }
    }
    
    private fun atEndOfFile() =
        peek() == NUL
    
    private fun Char.isWordStart() =
        isLetter() || this == '_'
    
    private fun Char.isWord() =
        isLetterOrDigit() || this == '_'
    
    private fun skipWhitespace() {
        while (match { it.isWhitespace() }) {
            step()
        }
    }
    
    private fun skipLineComment() {
        mustSkip("//")
        
        while (!skip('\n')) {
            step()
        }
    }
    
    private fun skipBlockComment() {
        mustSkip("/*")
        
        while (!skip("*/")) {
            step()
        }
    }
    
    private fun StringBuilder.take() {
        append(peek())
        step()
    }
    
    private fun number(): Token {
        val location = here()
        
        val result = buildString {
            do {
                take()
            }
            while (match { it.isDigit() })
            
            if (match('.')) {
                do {
                    take()
                }
                while (match { it.isDigit() })
            }
            
            if (match('E') || match('e')) {
                take()
                
                do {
                    take()
                }
                while (match { it.isDigit() })
            }
        }
        
        val type = Token.Type.Value(result.toDouble())
        
        return Token(location, type)
    }
    
    private fun word(): Token {
        val location = here()
        
        val result = buildString {
            do {
                take()
            }
            while (match { it.isWord() })
        }
        
        val keyword = keywords[result]
        
        if (keyword != null) {
            return Token(location, keyword)
        }
        
        val literal = literals[result]
        
        if (literal != null) {
            return Token(location, Token.Type.Value(literal))
        }
        
        return Token(location, Token.Type.Identifier(result))
    }
    
    private fun unicode(size: Int): Char {
        val result = buildString {
            repeat(size) {
                if (!match { it.isDigit() || it in "ABCDEFabcdef" }) {
                    VoltError.invalidHexadecimal(peek(), here())
                }
                
                take()
            }
        }
        
        return result.toInt(16).toChar()
    }
    
    private fun escape(delimiter: Char): Char {
        mustSkip('\\')
        
        return when {
            skip('0')       -> '\u0000'
            
            skip('n')       -> '\n'
            
            skip('t')       -> '\t'
            
            skip('x')       -> unicode(2)
            
            skip('u')       -> unicode(4)
            
            skip('U')       -> unicode(8)
            
            skip('(')       -> {
                val name = buildString {
                    while (!match(")")) {
                        take()
                    }
                    
                    mustSkip(')')
                }
                
                Character.codePointOf(name).toChar()
            }
            
            skip('\\')      -> '\\'
            
            skip(delimiter) -> delimiter
            
            else            -> VoltError.invalidEscape(peek(), here())
        }
    }
    
    private fun string(): Token {
        val location = here()
        
        val delimiter = peek()
        
        mustSkip(delimiter)
        
        val result = buildString {
            while (!match(delimiter)) {
                if (match(NUL)) {
                    VoltError.unclosedString(location)
                }
                
                if (skip('\\')) {
                    append(escape(delimiter))
                }
                else {
                    take()
                }
            }
        }
        
        mustSkip(delimiter)
        
        val type = Token.Type.Value(result)
        
        return Token(location, type)
    }
    
    private fun operator(): Token {
        val location = here()
        
        val type = when {
            skip('=') -> when {
                skip('=') -> Token.Type.Symbol.DOUBLE_EQUAL
                
                else      -> Token.Type.Symbol.EQUAL_SIGN
            }
            
            skip('+') -> when {
                skip('+') -> Token.Type.Symbol.DOUBLE_PLUS
                
                skip('=') -> Token.Type.Symbol.PLUS_EQUAL
                
                else      -> Token.Type.Symbol.PLUS
            }
            
            skip('-') -> when {
                skip('-') -> Token.Type.Symbol.DOUBLE_DASH
                
                skip('=') -> Token.Type.Symbol.DASH_EQUAL
                
                skip('>') -> Token.Type.Symbol.ARROW
                
                else      -> Token.Type.Symbol.DASH
            }
            
            skip('*') -> when {
                skip('=') -> Token.Type.Symbol.STAR_EQUAL
                
                else      -> Token.Type.Symbol.STAR
            }
            
            skip('/') -> when {
                skip('=') -> Token.Type.Symbol.SLASH_EQUAL
                
                else      -> Token.Type.Symbol.SLASH
            }
            
            skip('%') -> when {
                skip('=') -> Token.Type.Symbol.PERCENT_EQUAL
                
                else      -> Token.Type.Symbol.PERCENT
            }
            
            skip('<') -> when {
                skip('>') -> Token.Type.Symbol.LESS_GREATER
                
                skip('=') -> Token.Type.Symbol.LESS_EQUAL_SIGN
                
                else      -> Token.Type.Symbol.LESS_SIGN
            }
            
            skip('>') -> when {
                skip('=') -> Token.Type.Symbol.GREATER_EQUAL_SIGN
                
                else      -> Token.Type.Symbol.GREATER_SIGN
            }
            
            skip('^') -> when {
                skip('=') -> Token.Type.Symbol.CARET_EQUAL
                
                else      -> Token.Type.Symbol.CARET
            }
            
            skip('&') -> Token.Type.Symbol.AMPERSAND
            
            skip('#') -> Token.Type.Symbol.POUND
            
            skip('$') -> Token.Type.Symbol.DOLLAR
            
            skip('?') -> Token.Type.Symbol.QUESTION
            
            skip('.') -> Token.Type.Symbol.DOT
            
            skip('@') -> Token.Type.Symbol.AT
            
            skip('(') -> Token.Type.Symbol.LEFT_PAREN
            
            skip(')') -> Token.Type.Symbol.RIGHT_PAREN
            
            skip('[') -> Token.Type.Symbol.LEFT_SQUARE
            
            skip(']') -> Token.Type.Symbol.RIGHT_SQUARE
            
            skip('{') -> Token.Type.Symbol.LEFT_BRACE
            
            skip('}') -> Token.Type.Symbol.RIGHT_BRACE
            
            skip(',') -> Token.Type.Symbol.COMMA
            
            skip(':') -> when {
                skip(':') -> Token.Type.Symbol.DOUBLE_COLON
                else      -> Token.Type.Symbol.COLON
            }
            
            skip(';') -> Token.Type.Symbol.SEMICOLON
            
            else      -> VoltError.illegalCharacter(peek(), here())
        }
        
        return Token(location, type)
    }
}