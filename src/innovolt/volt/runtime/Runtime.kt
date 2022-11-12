package innovolt.volt.runtime

import innovolt.volt.parser.Expr
import innovolt.volt.parser.Program
import innovolt.volt.parser.Stmt
import kotlin.math.pow

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
    private val memory = Memory()
    
    fun start() {
        memory.push()
    }
    
    fun stop() {
        memory.pop()
    }
    
    fun run(program: Program): Any {
        for (stmt in program) {
            visit(stmt)
        }
        
        return Unit
    }
    
    override fun visitEmptyExpr(expr: Expr.Empty) =
        Result.Unit
    
    override fun visitAssignExpr(expr: Expr.Assign): Result<*> {
        val value = visit(expr.value)
        
        memory[expr.name.value] = value
        
        return value
    }
    
    override fun visitTernaryExpr(expr: Expr.Ternary): Result<*> {
        val condition = visit(expr.condition) as? Result.Boolean ?: TODO()
        
        return visit(if (condition.value) expr.yes else expr.no)
    }
    
    override fun visitBinaryExpr(expr: Expr.Binary) =
        when (expr.operator) {
            Expr.Binary.Operator.ADD           -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Number(left.value + right.value)
                    
                    else             -> TODO()
                }
                
                else             -> TODO()
            }
            
            Expr.Binary.Operator.SUBTRACT      -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Number(left.value - right.value)
                    
                    else             -> TODO()
                }
                
                else             -> TODO()
            }
            
            Expr.Binary.Operator.MULTIPLY      -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Number(left.value * right.value)
                    
                    else             -> TODO()
                }
                
                else             -> TODO()
            }
            
            Expr.Binary.Operator.DIVIDE        -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Number(left.value / right.value)
                    
                    else             -> TODO()
                }
                
                else             -> TODO()
            }
            
            Expr.Binary.Operator.MODULUS       -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Number(left.value % right.value)
                    
                    else             -> TODO()
                }
                
                else             -> TODO()
            }
            
            Expr.Binary.Operator.EXPONENTIATE  -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Number(left.value.pow(right.value))
                    
                    else             -> TODO()
                }
                
                else             -> TODO()
            }
            
            Expr.Binary.Operator.CONCATENATE   -> {
                val left = visit(expr.left)
                val right = visit(expr.right)
                
                Result.String("$left$right")
            }
            
            Expr.Binary.Operator.AND           -> {
                val left = visit(expr.left) as? Result.Boolean ?: TODO()
                
                if (!left.value) {
                    Result.Boolean(false)
                }
                else {
                    visit(expr.right) as Result.Boolean ?: TODO()
                }
            }
            
            Expr.Binary.Operator.OR            -> {
                val left = visit(expr.left) as? Result.Boolean ?: TODO()
                
                if (left.value) {
                    Result.Boolean(true)
                }
                else {
                    visit(expr.right) as Result.Boolean ?: TODO()
                }
            }
            
            Expr.Binary.Operator.LESS          -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Boolean(left.value < right.value)
                    
                    else             -> TODO()
                }
                
                else             -> TODO()
            }
            
            Expr.Binary.Operator.LESS_EQUAL    -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Boolean(left.value <= right.value)
                    
                    else             -> TODO()
                }
                
                else             -> TODO()
            }
            
            Expr.Binary.Operator.GREATER       -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Boolean(left.value > right.value)
                    
                    else             -> TODO()
                }
                
                else             -> TODO()
            }
            
            Expr.Binary.Operator.GREATER_EQUAL -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Boolean(left.value >= right.value)
                    
                    else             -> TODO()
                }
                
                else             -> TODO()
            }
            
            Expr.Binary.Operator.EQUAL         -> when (val left = visit(expr.left)) {
                is Result.Boolean -> when (val right = visit(expr.right)) {
                    is Result.Boolean -> Result.Boolean(left.value == right.value)
                    
                    else              -> TODO()
                }
                
                is Result.Number  -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Boolean(left.value == right.value)
                    
                    else             -> TODO()
                }
                
                is Result.String  -> when (val right = visit(expr.right)) {
                    is Result.String -> Result.Boolean(left.value == right.value)
                    
                    else             -> TODO()
                }
                
                else              -> TODO()
            }
            
            Expr.Binary.Operator.NOT_EQUAL     -> when (val left = visit(expr.left)) {
                is Result.Boolean -> when (val right = visit(expr.right)) {
                    is Result.Boolean -> Result.Boolean(left.value != right.value)
                    
                    else              -> TODO()
                }
                
                is Result.Number  -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Boolean(left.value != right.value)
                    
                    else             -> TODO()
                }
                
                is Result.String  -> when (val right = visit(expr.right)) {
                    is Result.String -> Result.Boolean(left.value != right.value)
                    
                    else             -> TODO()
                }
                
                else              -> TODO()
            }
        }
    
    override fun visitPrefixExpr(expr: Expr.Prefix) =
        when (expr.operator) {
            Expr.Prefix.Operator.NEGATE -> when (val right = visit(expr.right)) {
                is Result.Number -> Result.Number(-right.value)
                
                is Result.String -> Result.String(right.value.reversed())
                
                else             -> TODO()
            }
            
            Expr.Prefix.Operator.NOT    -> when (val right = visit(expr.right)) {
                is Result.Boolean -> Result.Boolean(!right.value)
                
                else              -> TODO()
            }
            
            Expr.Prefix.Operator.SIZE   -> when (val right = visit(expr.right)) {
                is Result.String -> Result.Number(right.value.length.toDouble())
                
                is Result.List   -> Result.Number(right.value.size.toDouble())
                
                is Result.Map    -> Result.Number(right.value.size.toDouble())
                
                else             -> Result.Number(1.0)
            }
            
            Expr.Prefix.Operator.STRING -> Result.String(visit(expr.right).toString())
        }
    
    override fun visitGetMemberExpr(expr: Expr.GetMember): Result<*> {
        val target = visit(expr.target) as? Result.Instance ?: TODO()
        
        return target.value[expr.member.value]
    }
    
    override fun visitSetMemberExpr(expr: Expr.SetMember): Result<*> {
        val target = visit(expr.target) as? Result.Instance ?: TODO()
        
        val value = visit(expr.value)
        
        target.value[expr.member.value] = value
        
        return value
    }
    
    override fun visitGetIndexExpr(expr: Expr.GetIndex) =
        when (val target = visit(expr.target)) {
            is Result.String -> when (val index = visit(expr.index)) {
                is Result.Number -> Result.String(target.value[index.value.toInt()].toString())
                
                else             -> TODO()
            }
            
            is Result.List   -> when (val index = visit(expr.index)) {
                is Result.Number -> target.value[index.value.toInt()]
                
                else             -> TODO()
            }
            
            is Result.Map    -> when (val index = visit(expr.index)) {
                is Result.String -> target.value[index.value] ?: Result.Null
                
                else             -> TODO()
            }
            
            else             -> TODO()
        }
    
    override fun visitSetIndexExpr(expr: Expr.SetIndex) =
        when (val target = visit(expr.target)) {
            is Result.List -> when (val index = visit(expr.index)) {
                is Result.Number -> {
                    val value = visit(expr.value)
                    
                    target.value[index.value.toInt()] = value
                    
                    value
                }
                
                else             -> TODO()
            }
            
            is Result.Map  -> when (val index = visit(expr.index)) {
                is Result.String -> {
                    val value = visit(expr.value)
                    
                    target.value[index.value] = value
                    
                    value
                }
                
                else             -> TODO()
            }
            
            else           -> TODO()
        }
    
    override fun visitInvokeExpr(expr: Expr.Invoke): Result<*> {
        TODO("Not yet implemented")
    }
    
    override fun visitListLiteralExpr(expr: Expr.ListLiteral): Result<*> {
        val list = ListInstance()
        
        for (element in expr.elements) {
            list += visit(element)
        }
        
        return Result.List(list)
    }
    
    override fun visitMapLiteralExpr(expr: Expr.MapLiteral): Result<*> {
        val map = MapInstance()
        
        for ((key, value) in expr.pairs) {
            map[key.value] = visit(value)
        }
        
        return Result.Map(map)
    }
    
    override fun visitLambdaExpr(expr: Expr.Lambda) =
        Result.Function(FunctionInstance(expr.function, memory.peek()))
    
    override fun visitNameExpr(expr: Expr.Name) =
        memory[expr.value]
    
    override fun visitValueExpr(expr: Expr.Value) =
        Result.of(expr.value)
    
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