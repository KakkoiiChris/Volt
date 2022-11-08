package innovolt.volt.parser

import innovolt.volt.lexer.Location

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Stmt.kt
 *
 * Created: Monday, November 07, 2022, 23:15:02
 *
 * @author Christian Bryce Alexander
 */
sealed interface Stmt {
    val location: Location
    
    fun <X> accept(visitor: Visitor<X>): X
    
    interface Visitor<X> {
        fun visitEmptyStmt(stmt: Empty): X
        
        fun visitBlockStmt(stmt: Block): X
        
        fun visitIfStmt(stmt: If): X
        
        fun visitWhileStmt(stmt: While): X
        
        fun visitDoStmt(stmt: Do): X
        
        fun visitForStmt(stmt: For): X
        
        fun visitTryStmt(stmt: Try): X
        
        fun visitBreakStmt(stmt: Break): X
        
        fun visitContinueStmt(stmt: Continue): X
        
        fun visitThrowStmt(stmt: Throw): X
        
        fun visitReturnStmt(stmt: Return): X
        
        fun visitFunctionStmt(stmt: Function): X
        
        fun visitClassStmt(stmt: Class): X
        
        fun visitExpressionStmt(stmt: Expression): X
    }
    
    class Empty(override val location: Location) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitEmptyStmt(this)
    }
    
    class Block(override val location: Location, val stmts: List<Stmt>) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitBlockStmt(this)
    }
    
    class If(override val location: Location, val condition: Expr, val body: Stmt, val `else`: Stmt) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitIfStmt(this)
    }
    
    class While(override val location: Location, val condition: Expr, val body: Stmt) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitWhileStmt(this)
    }
    
    class Do(override val location: Location, val body: Stmt, val condition: Expr) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitDoStmt(this)
    }
    
    class For(override val location: Location, val pointer: Expr.Name, val iterable: Expr, val body: Stmt) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitForStmt(this)
    }
    
    class Try(override val location: Location, val body: Stmt, val error: Expr.Name, val catchBody: Stmt, val finallyBody: Stmt) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitTryStmt(this)
    }
    
    class Break(override val location: Location) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitBreakStmt(this)
    }
    
    class Continue(override val location: Location) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitContinueStmt(this)
    }
    
    class Throw(override val location: Location, val value: Expr) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitThrowStmt(this)
    }
    
    class Return(override val location: Location, val value: Expr) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitReturnStmt(this)
    }
    
    class Function(override val location: Location, val name: Expr.Name, val params: List<Expr.Name>, val body: Stmt) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitFunctionStmt(this)
    }
    
    class Class(override val location: Location, val name: Expr.Name, val params: List<Expr.Name>, val init: Block) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitClassStmt(this)
    }
    
    class Expression(override val location: Location, val expr: Expr) : Stmt {
        override fun <X> accept(visitor: Visitor<X>): X =
            visitor.visitExpressionStmt(this)
    }
}