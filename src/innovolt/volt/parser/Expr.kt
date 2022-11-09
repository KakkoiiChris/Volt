package innovolt.volt.parser

import innovolt.volt.lexer.Location
import innovolt.volt.lexer.Token

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Expr.kt
 *
 * Created: Monday, November 07, 2022, 22:35:49
 *
 * @author Christian Bryce Alexander
 */
sealed interface Expr {
    val location: Location
    
    fun <X> accept(visitor: Visitor<X>): X
    
    interface Visitor<X> {
        fun visitEmptyExpr(expr: Empty): X
        
        fun visitAssignExpr(expr: Assign): X
        
        fun visitTernaryExpr(expr: Ternary): X
        
        fun visitBinaryExpr(expr: Binary): X
        
        fun visitPrefixExpr(expr: Prefix): X
        
        fun visitPostfixExpr(expr: Postfix): X
        
        fun visitGetMemberExpr(expr: GetMember): X
        
        fun visitSetMemberExpr(expr: SetMember): X
        
        fun visitGetIndexExpr(expr: GetIndex): X
        
        fun visitSetIndexExpr(expr: SetIndex): X
        
        fun visitInvokeExpr(expr: Invoke): X
        
        fun visitListLiteralExpr(expr: ListLiteral): X
        
        fun visitMapLiteralExpr(expr: MapLiteral): X
        
        fun visitLambdaExpr(expr: Lambda): X
        
        fun visitNameExpr(expr: Name): X
        
        fun visitValueExpr(expr: Value): X
    }
    
    class Empty(override val location: Location) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitEmptyExpr(this)
    }
    
    class Assign(override val location: Location, val target: Expr, val value: Expr) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitAssignExpr(this)
    }
    
    class Ternary(override val location: Location, val condition: Expr, val yes: Expr, val no: Expr) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitTernaryExpr(this)
    }
    
    class Binary(override val location: Location, val operator: Operator, val left: Expr, val right: Expr) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitBinaryExpr(this)
        
        enum class Operator(val type: Token.Type) {
            ADD(Token.Type.Symbol.PLUS),
            SUBTRACT(Token.Type.Symbol.DASH),
            MULTIPLY(Token.Type.Symbol.STAR),
            DIVIDE(Token.Type.Symbol.SLASH),
            MODULUS(Token.Type.Symbol.PERCENT),
            POWER(Token.Type.Symbol.CARET),
            CONCATENATE(Token.Type.Symbol.AMPERSAND),
            AND(Token.Type.Keyword.AND),
            OR(Token.Type.Keyword.OR),
            LESS(Token.Type.Symbol.LESS_SIGN),
            LESS_EQUAL(Token.Type.Symbol.LESS_EQUAL_SIGN),
            GREATER(Token.Type.Symbol.GREATER_SIGN),
            GREATER_EQUAL(Token.Type.Symbol.GREATER_EQUAL_SIGN),
            EQUAL(Token.Type.Symbol.DOUBLE_EQUAL),
            NOT_EQUAL(Token.Type.Symbol.LESS_GREATER);
            
            companion object{
                fun byType(type:Token.Type)=
                    values().find { it.type==type }
            }
        }
    }
    
    class Prefix(override val location: Location, val operator: Binary.Operator, val right: Expr) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitPrefixExpr(this)
        
        enum class Operator(val type: Token.Type) {
            NEGATE(Token.Type.Symbol.DASH),
            NOT(Token.Type.Keyword.NOT),
            SIZE(Token.Type.Symbol.POUND),
            STRING(Token.Type.Symbol.DOLLAR),
            INCREMENT(Token.Type.Symbol.DOUBLE_PLUS),
            DECREMENT(Token.Type.Symbol.DOUBLE_DASH);
    
            companion object{
                fun byType(type:Token.Type)=
                    Binary.Operator.values().find { it.type==type }
            }
        }
    }
    
    class Postfix(override val location: Location, val operator: Binary.Operator, val left: Expr) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitPostfixExpr(this)
        
        enum class Operator(val type: Token.Type) {
            INCREMENT(Token.Type.Symbol.DOUBLE_PLUS),
            DECREMENT(Token.Type.Symbol.DOUBLE_DASH);
    
            companion object{
                fun byType(type:Token.Type)=
                    Binary.Operator.values().find { it.type==type }
            }
        }
    }
    
    class GetMember(override val location: Location, val target: Expr, val member: Name) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitGetMemberExpr(this)
    }
    
    class SetMember(override val location: Location, val target: Expr, val member: Name, val value: Expr) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitSetMemberExpr(this)
    }
    
    class GetIndex(override val location: Location, val target: Expr, val index: Expr) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitGetIndexExpr(this)
    }
    
    class SetIndex(override val location: Location, val target: Expr, val index: Expr, val value: Expr) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitSetIndexExpr(this)
    }
    
    class Invoke(override val location: Location, val target: Expr, val args: List<Expr>) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitInvokeExpr(this)
    }
    
    class ListLiteral(override val location: Location, val elements: List<Expr>) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitListLiteralExpr(this)
    }
    
    class MapLiteral(override val location: Location, val pairs: List<Pair<Name, Expr>>) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitMapLiteralExpr(this)
    }
    
    class Lambda(override val location: Location, val function: Stmt.Function) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitLambdaExpr(this)
    }
    
    class Name(override val location: Location, val value: String) : Expr {
        companion object {
            val none = Name(Location.none, "")
        }
        
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitNameExpr(this)
    }
    
    class Value(override val location: Location, val value: Any) : Expr {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitValueExpr(this)
    }
}