package innovolt.volt.parser

import innovolt.volt.lexer.Lexer
import innovolt.volt.lexer.Location
import innovolt.volt.lexer.Token
import innovolt.volt.util.VoltError
import kotlin.reflect.KClass

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
    private val path = ClassPath()
    
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
    
    private fun <X : Token.Type> match(`class`: KClass<X>) =
        `class`.isInstance(token.type)
    
    private fun matchAny(vararg types: Token.Type): Boolean {
        for (type in types) {
            if (match(type)) {
                return true
            }
        }
        
        return false
    }
    
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
    
    private fun <X : Token.Type> skip(`class`: KClass<X>) =
        if (match(`class`)) {
            step()
            
            true
        }
        else false
    
    private fun mustSkip(type: Token.Type) {
        if (!skip(type)) {
            VoltError.invalidTokenType(token.type, type, here())
        }
    }
    
    private fun <X : Token.Type> mustSkip(`class`: KClass<X>) {
        if (!skip(`class`)) {
            VoltError.invalidTokenType(token.type, `class`, here())
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
        
        val label = if (skip(Token.Type.Symbol.AT)) nameExpr() else Expr.Name.none
        
        val body = stmt()
        
        return Stmt.While(location, condition, label, body)
    }
    
    private fun doStmt(): Stmt.Do {
        val location = here()
        
        mustSkip(Token.Type.Keyword.DO)
        
        val label = if (skip(Token.Type.Symbol.AT)) nameExpr() else Expr.Name.none
        
        val body = stmt()
        
        mustSkip(Token.Type.Keyword.WHILE)
        mustSkip(Token.Type.Symbol.LEFT_PAREN)
        
        val condition = expr()
        
        mustSkip(Token.Type.Symbol.RIGHT_PAREN)
        mustSkip(Token.Type.Symbol.SEMICOLON)
        
        return Stmt.Do(location, label, body, condition)
    }
    
    private fun forStmt(): Stmt.For {
        val location = here()
        
        mustSkip(Token.Type.Keyword.FOR)
        mustSkip(Token.Type.Symbol.LEFT_PAREN)
        
        val pointer = nameExpr()
        
        mustSkip(Token.Type.Symbol.COLON)
        
        val iterable = expr()
        
        mustSkip(Token.Type.Symbol.RIGHT_PAREN)
        
        val label = if (skip(Token.Type.Symbol.AT)) nameExpr() else Expr.Name.none
        
        val body = stmt()
        
        return Stmt.For(location, pointer, iterable, label, body)
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
        
        val label = if (!skip(Token.Type.Symbol.SEMICOLON)) nameExpr() else Expr.Name.none
        
        return Stmt.Break(location, label)
    }
    
    private fun continueStmt(): Stmt.Continue {
        val location = here()
        
        mustSkip(Token.Type.Keyword.CONTINUE)
        
        val label = if (!skip(Token.Type.Symbol.SEMICOLON)) nameExpr() else Expr.Name.none
        
        return Stmt.Continue(location, label)
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
        
        return Stmt.Function(location, path.toPath(name.value), name, params, body)
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
        
        val init = if (skip(Token.Type.Symbol.SEMICOLON)) Stmt.Block(Location.none, emptyList()) else blockStmt()
        
        return Stmt.Class(location, name, params, init)
    }
    
    private fun expressionStmt(): Stmt.Expression {
        val location = here()
        
        val expr = expr()
        
        mustSkip(Token.Type.Symbol.SEMICOLON)
        
        return Stmt.Expression(location, expr)
    }
    
    private fun expr() =
        assignExpr()
    
    private fun assignExpr(): Expr {
        var expr = ternaryExpr()
        
        if (matchAny(Token.Type.Symbol.EQUAL_SIGN, Token.Type.Symbol.PLUS_EQUAL, Token.Type.Symbol.DASH_EQUAL, Token.Type.Symbol.STAR_EQUAL, Token.Type.Symbol.SLASH_EQUAL, Token.Type.Symbol.PERCENT_EQUAL, Token.Type.Symbol.CARET_EQUAL)) {
            val symbol = token
            
            mustSkip(symbol.type)
            
            expr = desugarAssignment(symbol, expr)
        }
        
        return expr
    }
    
    private fun desugarAssignment(symbol: Token, target: Expr): Expr {
        if (symbol.type == Token.Type.Symbol.EQUAL_SIGN) {
            return when (target) {
                is Expr.Name      -> Expr.Assign(symbol.location, target, ternaryExpr())
                
                is Expr.GetMember -> Expr.SetMember(symbol.location, target.target, target.member, ternaryExpr())
                
                is Expr.GetIndex  -> Expr.SetIndex(symbol.location, target.target, target.index, ternaryExpr())
                
                else              -> VoltError.invalidAssignmentTarget(target.location)
            }
        }
        
        val operator = when (symbol.type) {
            Token.Type.Symbol.PLUS_EQUAL    -> Expr.Binary.Operator.ADD
            
            Token.Type.Symbol.DASH_EQUAL    -> Expr.Binary.Operator.SUBTRACT
            
            Token.Type.Symbol.STAR_EQUAL    -> Expr.Binary.Operator.MULTIPLY
            
            Token.Type.Symbol.SLASH_EQUAL   -> Expr.Binary.Operator.DIVIDE
            
            Token.Type.Symbol.PERCENT_EQUAL -> Expr.Binary.Operator.MODULUS
            
            Token.Type.Symbol.CARET_EQUAL   -> Expr.Binary.Operator.EXPONENTIATE
            
            else                            -> error("/!\\ BROKEN ASSIGNMENT OPERATOR /!\\")
        }
        
        val value = Expr.Binary(symbol.location, operator, target, ternaryExpr())
        
        return when (target) {
            is Expr.Name      -> Expr.Assign(symbol.location, target, value)
            
            is Expr.GetMember -> Expr.SetMember(symbol.location, target.target, target.member, value)
            
            is Expr.GetIndex  -> Expr.SetIndex(symbol.location, target.target, target.index, value)
            
            else              -> VoltError.invalidAssignmentTarget(target.location)
        }
    }
    
    private fun ternaryExpr(): Expr {
        var expr = disjunctionExpr()
        
        if (match(Token.Type.Symbol.QUESTION)) {
            val symbol = token
            
            mustSkip(symbol.type)
            
            val yes = ternaryExpr()
            
            mustSkip(Token.Type.Symbol.COLON)
            
            val no = ternaryExpr()
            
            expr = Expr.Ternary(symbol.location, expr, yes, no)
        }
        
        return expr
    }
    
    private fun disjunctionExpr(): Expr {
        var expr = conjunctionExpr()
        
        while (match(Token.Type.Keyword.OR)) {
            val symbol = token
            
            mustSkip(symbol.type)
            
            expr = Expr.Binary(symbol.location, Expr.Binary.Operator.byType(symbol.type), expr, conjunctionExpr())
        }
        
        return expr
    }
    
    private fun conjunctionExpr(): Expr {
        var expr = equalityExpr()
        
        while (match(Token.Type.Keyword.AND)) {
            val symbol = token
            
            mustSkip(symbol.type)
            
            expr = Expr.Binary(symbol.location, Expr.Binary.Operator.byType(symbol.type), expr, equalityExpr())
        }
        
        return expr
    }
    
    private fun equalityExpr(): Expr {
        var expr = relationalExpr()
        
        while (matchAny(Token.Type.Symbol.DOUBLE_EQUAL, Token.Type.Symbol.LESS_GREATER)) {
            val symbol = token
            
            mustSkip(symbol.type)
            
            expr = Expr.Binary(symbol.location, Expr.Binary.Operator.byType(symbol.type), expr, relationalExpr())
        }
        
        return expr
    }
    
    private fun relationalExpr(): Expr {
        var expr = concatenationExpr()
        
        while (matchAny(Token.Type.Symbol.LESS_SIGN, Token.Type.Symbol.LESS_EQUAL_SIGN, Token.Type.Symbol.GREATER_SIGN, Token.Type.Symbol.GREATER_EQUAL_SIGN)) {
            val symbol = token
            
            mustSkip(symbol.type)
            
            expr = Expr.Binary(symbol.location, Expr.Binary.Operator.byType(symbol.type), expr, concatenationExpr())
        }
        
        return expr
    }
    
    private fun concatenationExpr(): Expr {
        var expr = additiveExpr()
        
        while (match(Token.Type.Symbol.AMPERSAND)) {
            val symbol = token
            
            mustSkip(symbol.type)
            
            expr = Expr.Binary(symbol.location, Expr.Binary.Operator.byType(symbol.type), expr, additiveExpr())
        }
        
        return expr
    }
    
    private fun additiveExpr(): Expr {
        var expr = multiplicativeExpr()
        
        while (matchAny(Token.Type.Symbol.PLUS, Token.Type.Symbol.DASH)) {
            val symbol = token
            
            mustSkip(symbol.type)
            
            expr = Expr.Binary(symbol.location, Expr.Binary.Operator.byType(symbol.type), expr, multiplicativeExpr())
        }
        
        return expr
    }
    
    private fun multiplicativeExpr(): Expr {
        var expr = exponentiationExpr()
        
        while (matchAny(Token.Type.Symbol.STAR, Token.Type.Symbol.SLASH, Token.Type.Symbol.PERCENT)) {
            val symbol = token
            
            mustSkip(symbol.type)
            
            expr = Expr.Binary(symbol.location, Expr.Binary.Operator.byType(symbol.type), expr, exponentiationExpr())
        }
        
        return expr
    }
    
    private fun exponentiationExpr(): Expr {
        var expr = prefixExpr()
        
        while (match(Token.Type.Symbol.CARET)) {
            val symbol = token
            
            mustSkip(symbol.type)
            
            expr = Expr.Binary(symbol.location, Expr.Binary.Operator.byType(symbol.type), expr, exponentiationExpr())
        }
        
        return expr
    }
    
    private fun prefixExpr(): Expr {
        return if (matchAny(Token.Type.Symbol.DASH, Token.Type.Keyword.NOT, Token.Type.Symbol.POUND, Token.Type.Symbol.DOLLAR)) {
            val symbol = token
            
            mustSkip(symbol.type)
            
            Expr.Prefix(symbol.location, Expr.Prefix.Operator.byType(symbol.type), prefixExpr())
        }
        else {
            postfixExpr()
        }
    }
    
    private fun postfixExpr(): Expr {
        var expr = terminalExpr()
        
        while (matchAny(Token.Type.Symbol.DOT, Token.Type.Symbol.LEFT_SQUARE, Token.Type.Symbol.LEFT_PAREN)) {
            val symbol = token
            
            expr = when {
                skip(Token.Type.Symbol.DOT)         -> {
                    val name = nameExpr()
                    
                    Expr.GetMember(symbol.location, expr, name)
                }
                
                skip(Token.Type.Symbol.LEFT_SQUARE) -> {
                    val index = expr()
                    
                    mustSkip(Token.Type.Symbol.RIGHT_SQUARE)
                    
                    Expr.GetIndex(symbol.location, expr, index)
                }
                
                skip(Token.Type.Symbol.LEFT_PAREN)  -> {
                    val arguments = mutableListOf<Expr>()
                    
                    if (!skip(Token.Type.Symbol.RIGHT_PAREN)) {
                        do {
                            arguments += expr()
                        }
                        while (skip(Token.Type.Symbol.COMMA))
                        
                        mustSkip(Token.Type.Symbol.RIGHT_PAREN)
                    }
                    
                    Expr.Invoke(symbol.location, expr, arguments)
                }
                
                else                                -> error("/!\\ BROKEN POSTFIX OPERATOR /!\\")
            }
        }
        
        return expr
    }
    
    private fun terminalExpr(): Expr {
        var expr = when {
            match(Token.Type.Symbol.LEFT_PAREN)  -> parentheticalExpr()
            
            match(Token.Type.Symbol.LEFT_SQUARE) -> listExpr()
            
            match(Token.Type.Symbol.LEFT_BRACE)  -> mapExpr()
            
            match(Token.Type.Identifier::class)  -> nameExpr()
            
            match(Token.Type.Value::class)       -> valueExpr()
            
            else                                 -> VoltError.invalidTerminal(token.type, here())
        }
        
        if (match(Token.Type.Symbol.ARROW)) {
            if (expr !is Expr.Name) VoltError.invalidLambdaParameter(expr.location)
            
            val lambdaLocation = here()
            
            mustSkip(Token.Type.Symbol.ARROW)
            
            val body = if (match(Token.Type.Symbol.LEFT_BRACE)) blockStmt() else Stmt.Return(here(), expr())
            
            expr = Expr.Lambda(lambdaLocation, Stmt.Function(lambdaLocation, "", Expr.Name.none, listOf(expr), body))
        }
        
        return expr
    }
    
    private fun parentheticalExpr(): Expr {
        val location = here()
        
        mustSkip(Token.Type.Symbol.LEFT_PAREN)
        
        if (skip(Token.Type.Symbol.RIGHT_PAREN)) {
            mustSkip(Token.Type.Symbol.ARROW)
            
            val body = if (match(Token.Type.Symbol.LEFT_BRACE)) blockStmt() else Stmt.Return(here(), expr())
            
            return Expr.Lambda(location, Stmt.Function(location, "", Expr.Name.none, emptyList(), body))
        }
        
        var expr = expr()
        
        if (match(Token.Type.Symbol.COMMA)) {
            if (expr !is Expr.Name) VoltError.invalidLambdaParameter(expr.location)
            
            val params = mutableListOf(expr)
            
            while (skip(Token.Type.Symbol.COMMA)) {
                params += nameExpr()
            }
            
            mustSkip(Token.Type.Symbol.RIGHT_PAREN)
            mustSkip(Token.Type.Symbol.ARROW)
            
            val body = if (match(Token.Type.Symbol.LEFT_BRACE)) blockStmt() else Stmt.Return(here(), expr())
            
            expr = Expr.Lambda(location, Stmt.Function(location, "", Expr.Name.none, params, body))
        }
        else {
            mustSkip(Token.Type.Symbol.RIGHT_PAREN)
        }
        
        return expr
    }
    
    private fun listExpr(): Expr {
        val location = here()
        
        mustSkip(Token.Type.Symbol.LEFT_SQUARE)
        
        val elements = mutableListOf<Expr>()
        
        if (!skip(Token.Type.Symbol.RIGHT_SQUARE)) {
            do {
                elements += expr()
            }
            while (skip(Token.Type.Symbol.COMMA))
            
            mustSkip(Token.Type.Symbol.RIGHT_SQUARE)
        }
        
        return Expr.ListLiteral(location, elements)
    }
    
    private fun mapExpr(): Expr {
        val location = here()
        
        mustSkip(Token.Type.Symbol.LEFT_BRACE)
        
        val pairs = mutableListOf<Pair<Expr.Name, Expr>>()
        
        if (!skip(Token.Type.Symbol.RIGHT_BRACE)) {
            do {
                val key = nameExpr()
                
                mustSkip(Token.Type.Symbol.COLON)
                
                val value = expr()
                
                pairs += key to value
            }
            while (skip(Token.Type.Symbol.COMMA))
            
            mustSkip(Token.Type.Symbol.RIGHT_BRACE)
        }
        
        return Expr.MapLiteral(location, pairs)
    }
    
    private fun nameExpr(): Expr.Name {
        val token = token
        
        mustSkip(Token.Type.Identifier::class)
        
        return Expr.Name(token.location, (token.type as Token.Type.Identifier).value)
    }
    
    private fun valueExpr(): Expr.Value {
        val token = token
        
        mustSkip(Token.Type.Value::class)
        
        return Expr.Value(token.location, (token.type as Token.Type.Value).value)
    }
}