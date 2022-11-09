package innovolt.volt.parser

import innovolt.volt.lexer.Lexer
import innovolt.volt.lexer.Location
import innovolt.volt.lexer.Token
import innovolt.volt.util.VoltError

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Parser.kt
 *
 * Created: Tuesday, November 08, 2022, 21:15:11
 *
 * @author Christian Bryce Alexander
 */
class Parser(private val lexer: Lexer) {
    private var token = lexer.next()
    
    fun parse(): Program {
        val stmts = mutableListOf<Stmt>()
        
        while (!atEndOfFile()) {
            stmts += stmt()
        }
        
        return Program(stmts)
    }
    
    private fun here() =
        token.location
    
    private fun match(type: Token.Type) =
        token.type == type
    
    private fun match(predicate: (Token.Type) -> Boolean) =
        predicate(token.type)
    
    private fun step() {
        if (lexer.hasNext()) {
            token = lexer.next()
        }
    }
    
    private fun skip(type: Token.Type) =
        if (match(type)) {
            step()
            
            true
        }
        else false
    
    private fun skip(predicate: (Token.Type) -> Boolean) =
        if (match(predicate)) {
            step()
            
            true
        }
        else false
    
    private fun mustSkip(type: Token.Type) {
        if (!skip(type)) {
            VoltError.forParser("Token type '${token.type}' is invalid; expected $type!", here())
        }
    }
    
    private fun atEndOfFile() =
        match(Token.Type.Symbol.END_OF_FILE)
    
    private fun stmt() =
        when {
            match(Token.Type.Symbol.SEMICOLON)  -> emptyStmt()
            
            match(Token.Type.Symbol.LEFT_BRACE) -> blockStmt()
            
            match(Token.Type.Keyword.IF)        -> ifStmt()
            
            match(Token.Type.Keyword.WHILE)     -> whileStmt()
            
            match(Token.Type.Keyword.DO)        -> doStmt()
            
            match(Token.Type.Keyword.FOR)       -> forStmt()
            
            match(Token.Type.Keyword.TRY)       -> tryStmt()
            
            match(Token.Type.Keyword.BREAK)     -> breakStmt()
            
            match(Token.Type.Keyword.CONTINUE)  -> continueStmt()
            
            match(Token.Type.Keyword.THROW)     -> throwStmt()
            
            match(Token.Type.Keyword.RETURN)    -> returnStmt()
            
            match(Token.Type.Keyword.FUNCTION)  -> functionStmt()
            
            match(Token.Type.Keyword.CLASS)     -> classStmt()
            
            else                                -> expressionStmt()
        }
    
    private fun emptyStmt(): Stmt.Empty {
        val location = here()
        
        mustSkip(Token.Type.Symbol.SEMICOLON)
        
        return Stmt.Empty(location)
    }
    
    private fun blockStmt(): Stmt.Block {
        val location = here()
        
        mustSkip(Token.Type.Symbol.LEFT_BRACE)
        
        val stmts = mutableListOf<Stmt>()
        
        while (!skip(Token.Type.Symbol.RIGHT_BRACE)) {
            stmts += stmt()
        }
        
        return Stmt.Block(location, stmts)
    }
    
    private fun ifStmt(): Stmt.If {
        val location = here()
        
        mustSkip(Token.Type.Keyword.IF)
        mustSkip(Token.Type.Symbol.LEFT_PAREN)
        
        val condition = expr()
        
        mustSkip(Token.Type.Symbol.RIGHT_PAREN)
        
        val body = stmt()
        
        val `else` = if (skip(Token.Type.Keyword.ELSE)) stmt() else Stmt.Empty(Location.none)
        
        return Stmt.If(location, condition, body, `else`)
    }
    
    private fun whileStmt(): Stmt.While {
        val location = here()
        
        mustSkip(Token.Type.Keyword.WHILE)
        mustSkip(Token.Type.Symbol.LEFT_PAREN)
        
        val condition = expr()
        
        mustSkip(Token.Type.Symbol.RIGHT_PAREN)
        
        val body = stmt()
        
        return Stmt.While(location, condition, body)
    }
    
    private fun doStmt(): Stmt.Do {
        val location = here()
        
        mustSkip(Token.Type.Keyword.DO)
        
        val body = stmt()
        
        mustSkip(Token.Type.Keyword.WHILE)
        mustSkip(Token.Type.Symbol.LEFT_PAREN)
        
        val condition = expr()
        
        mustSkip(Token.Type.Symbol.RIGHT_PAREN)
        mustSkip(Token.Type.Symbol.SEMICOLON)
        
        return Stmt.Do(location, body, condition)
    }
    
    private fun forStmt(): Stmt.For {
        val location = here()
        
        mustSkip(Token.Type.Keyword.FOR)
        mustSkip(Token.Type.Symbol.LEFT_PAREN)
        
        val pointer = nameExpr()
        
        mustSkip(Token.Type.Symbol.COLON)
        
        val iterable = expr()
        
        mustSkip(Token.Type.Symbol.RIGHT_PAREN)
        
        val body = stmt()
        
        return Stmt.For(location, pointer, iterable, body)
    }
    
    private fun tryStmt(): Stmt.Try {
        val location = here()
        
        mustSkip(Token.Type.Keyword.TRY)
        
        val body = stmt()
        
        var error = Expr.Name.none
        var catchBody: Stmt = Stmt.Empty(Location.none)
        
        if (skip(Token.Type.Keyword.CATCH)) {
            mustSkip(Token.Type.Symbol.LEFT_PAREN)
            
            error = nameExpr()
            
            mustSkip(Token.Type.Symbol.RIGHT_PAREN)
            
            catchBody = stmt()
        }
        
        val finallyBody = if (skip(Token.Type.Keyword.FINALLY)) stmt() else Stmt.Empty(Location.none)
        
        return Stmt.Try(location, body, error, catchBody, finallyBody)
    }
    
    private fun breakStmt(): Stmt.Break {
        val location = here()
        
        mustSkip(Token.Type.Keyword.BREAK)
        mustSkip(Token.Type.Symbol.SEMICOLON)
        
        return Stmt.Break(location)
    }
    
    private fun continueStmt(): Stmt.Continue {
        val location = here()
        
        mustSkip(Token.Type.Keyword.CONTINUE)
        mustSkip(Token.Type.Symbol.SEMICOLON)
        
        return Stmt.Continue(location)
    }
    
    private fun throwStmt(): Stmt.Throw {
        val location = here()
        
        mustSkip(Token.Type.Keyword.THROW)
        
        val value = expr()
        
        mustSkip(Token.Type.Symbol.SEMICOLON)
        
        return Stmt.Throw(location, value)
    }
    
    private fun returnStmt(): Stmt.Return {
        val location = here()
        
        mustSkip(Token.Type.Keyword.RETURN)
        
        val value = if (!skip(Token.Type.Symbol.SEMICOLON)) expr() else Expr.Empty(Location.none)
        
        return Stmt.Return(location, value)
    }
    
    private fun functionStmt(): Stmt.Function {
        val location = here()
        
        mustSkip(Token.Type.Keyword.FUNCTION)
        
        val name = nameExpr()
        
        val params = mutableListOf<Expr.Name>()
        
        if (skip(Token.Type.Symbol.LEFT_PAREN) && !skip(Token.Type.Symbol.RIGHT_PAREN)) {
            do {
                params += nameExpr()
            }
            while (skip(Token.Type.Symbol.COMMA))
            
            mustSkip(Token.Type.Symbol.RIGHT_PAREN)
        }
        
        val body = if (match(Token.Type.Symbol.ARROW)) {
            val returnLocation = here()
            
            mustSkip(Token.Type.Symbol.ARROW)
            
            val value = expr()
            
            mustSkip(Token.Type.Symbol.SEMICOLON)
            
            Stmt.Return(returnLocation, value)
        }
        else stmt()
        
        return Stmt.Function(location, name, params, body)
    }
    
    private fun classStmt(): Stmt.Class {
        val location = here()
        
        mustSkip(Token.Type.Keyword.CLASS)
        
        val name = nameExpr()
        
        val params = mutableListOf<Expr.Name>()
        
        if (skip(Token.Type.Symbol.LEFT_PAREN) && !skip(Token.Type.Symbol.RIGHT_PAREN)) {
            do {
                params += nameExpr()
            }
            while (skip(Token.Type.Symbol.COMMA))
            
            mustSkip(Token.Type.Symbol.RIGHT_PAREN)
        }
        
        val init = blockStmt()
        
        return Stmt.Class(location, name, params, init)
    }
    
    private fun expressionStmt(): Stmt.Expression {
        val location = here()
        
        val expr = expr()
        
        mustSkip(Token.Type.Symbol.SEMICOLON)
        
        return Stmt.Expression(location, expr)
    }
}