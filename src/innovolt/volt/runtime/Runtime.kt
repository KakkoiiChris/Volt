package innovolt.volt.runtime

import innovolt.volt.parser.Expr
import innovolt.volt.parser.Stmt

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Runtime.kt
 *
 * Created: Thursday, November 10, 2022, 15:02:26
 *
 * @author Christian Bryce Alexander
 */
class Runtime : Expr.Visitor<Result<*>>, Stmt.Visitor<Unit> {
    override fun visitEmptyExpr(expr: Expr.Empty): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitAssignExpr(expr: Expr.Assign): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitTernaryExpr(expr: Expr.Ternary): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitBinaryExpr(expr: Expr.Binary): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitPrefixExpr(expr: Expr.Prefix): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitGetMemberExpr(expr: Expr.GetMember): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitSetMemberExpr(expr: Expr.SetMember): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitGetIndexExpr(expr: Expr.GetIndex): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitSetIndexExpr(expr: Expr.SetIndex): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitInvokeExpr(expr: Expr.Invoke): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitListLiteralExpr(expr: Expr.ListLiteral): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitMapLiteralExpr(expr: Expr.MapLiteral): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitLambdaExpr(expr: Expr.Lambda): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitNameExpr(expr: Expr.Name): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitValueExpr(expr: Expr.Value): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitEmptyStmt(stmt: Stmt.Empty) {
        TODO("Not yet implemented")
    }
    
    override fun visitBlockStmt(stmt: Stmt.Block) {
        TODO("Not yet implemented")
    }
    
    override fun visitIfStmt(stmt: Stmt.If) {
        TODO("Not yet implemented")
    }
    
    override fun visitWhileStmt(stmt: Stmt.While) {
        TODO("Not yet implemented")
    }
    
    override fun visitDoStmt(stmt: Stmt.Do) {
        TODO("Not yet implemented")
    }
    
    override fun visitForStmt(stmt: Stmt.For) {
        TODO("Not yet implemented")
    }
    
    override fun visitTryStmt(stmt: Stmt.Try) {
        TODO("Not yet implemented")
    }
    
    override fun visitBreakStmt(stmt: Stmt.Break) {
        TODO("Not yet implemented")
    }
    
    override fun visitContinueStmt(stmt: Stmt.Continue) {
        TODO("Not yet implemented")
    }
    
    override fun visitThrowStmt(stmt: Stmt.Throw) {
        TODO("Not yet implemented")
    }
    
    override fun visitReturnStmt(stmt: Stmt.Return) {
        TODO("Not yet implemented")
    }
    
    override fun visitFunctionStmt(stmt: Stmt.Function) {
        TODO("Not yet implemented")
    }
    
    override fun visitClassStmt(stmt: Stmt.Class) {
        TODO("Not yet implemented")
    }
    
    override fun visitExpressionStmt(stmt: Stmt.Expression) {
        TODO("Not yet implemented")
    }
}