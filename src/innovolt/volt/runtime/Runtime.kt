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
    
    fun run(program: Program): Result<*> {
        try {
            for (stmt in program) {
                visit(stmt)
            }
        }
        catch (`return`: Redirect.Return) {
            return `return`.value ?: TODO()
        }
        catch(_:Redirect.Break) {
            TODO()
        }
        catch(_:Redirect.Continue) {
            TODO()
        }
        
        return Result.Unit
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
                    visit(expr.right) as? Result.Boolean ?: TODO()
                }
            }
            
            Expr.Binary.Operator.OR            -> {
                val left = visit(expr.left) as? Result.Boolean ?: TODO()
                
                if (left.value) {
                    Result.Boolean(true)
                }
                else {
                    visit(expr.right) as? Result.Boolean ?: TODO()
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
                    
                    else              -> Result.Boolean(false)
                }
                
                is Result.Number  -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Boolean(left.value == right.value)
                    
                    else             -> Result.Boolean(false)
                }
                
                is Result.String  -> when (val right = visit(expr.right)) {
                    is Result.String -> Result.Boolean(left.value == right.value)
                    
                    else             -> Result.Boolean(false)
                }
                
                else              -> Result.Boolean(false)
            }
            
            Expr.Binary.Operator.NOT_EQUAL     -> when (val left = visit(expr.left)) {
                is Result.Boolean -> when (val right = visit(expr.right)) {
                    is Result.Boolean -> Result.Boolean(left.value != right.value)
                    
                    else              -> Result.Boolean(true)
                }
                
                is Result.Number  -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Boolean(left.value != right.value)
                    
                    else             -> Result.Boolean(true)
                }
                
                is Result.String  -> when (val right = visit(expr.right)) {
                    is Result.String -> Result.Boolean(left.value != right.value)
                    
                    else             -> Result.Boolean(true)
                }
                
                else              -> Result.Boolean(true)
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
        val callable = visit(expr.target).value as? Callable ?: TODO()
        
        if (callable.params.size != expr.arguments.size) TODO()
        
        val variables = callable.params.zip(expr.arguments.map { visit(it) })
        
        return when (callable) {
            is VoltFunction -> invokeFunction(callable, variables)
            
            is VoltClass    -> invokeClass(callable, variables)
        }
    }
    
    private fun invokeFunction(function: VoltFunction, variables: List<Pair<Expr.Name, Result<*>>>): Result<*> {
        try {
            memory.push(Memory.Scope(function.scope))
            
            for ((name, value) in variables) {
                memory[name.value] = value
            }
            
            visit(function.body)
        }
        catch (`return`: Redirect.Return) {
            return `return`.value
        }
        finally {
            memory.pop()
        }
        
        return Result.Unit
    }
    
    private fun invokeClass(`class`: VoltClass, variables: List<Pair<Expr.Name, Result<*>>>): Result.Class {
        TODO()
    }
    
    override fun visitListLiteralExpr(expr: Expr.ListLiteral): Result<*> {
        val list = VoltList()
        
        for (element in expr.elements) {
            list += visit(element)
        }
        
        return Result.List(list)
    }
    
    override fun visitMapLiteralExpr(expr: Expr.MapLiteral): Result<*> {
        val map = VoltMap()
        
        for ((key, value) in expr.pairs) {
            map[key.value] = visit(value)
        }
        
        return Result.Map(map)
    }
    
    override fun visitLambdaExpr(expr: Expr.Lambda) =
        Result.Function(VoltFunction(expr.function, memory.peek()))
    
    override fun visitNameExpr(expr: Expr.Name) =
        memory[expr.value]
    
    override fun visitValueExpr(expr: Expr.Value) =
        Result.of(expr.value)
    
    override fun visitEmptyStmt(stmt: Stmt.Empty) =
        Unit
    
    override fun visitBlockStmt(stmt: Stmt.Block) {
        try {
            memory.push()
            
            for (subStmt in stmt.stmts) {
                visit(subStmt)
            }
        }
        finally {
            memory.pop()
        }
    }
    
    override fun visitIfStmt(stmt: Stmt.If) {
        val condition = visit(stmt.condition) as? Result.Boolean ?: TODO()
        
        if (condition.value) {
            visit(stmt.body)
        }
        else {
            visit(stmt.`else`)
        }
    }
    
    override fun visitWhileStmt(stmt: Stmt.While) {
        while (true) {
            val condition = visit(stmt.condition) as? Result.Boolean ?: TODO()
            
            if (!condition.value) break
            
            try {
                visit(stmt.body)
            }
            catch (`break`: Redirect.Break) {
                break
            }
            catch (`continue`: Redirect.Continue) {
                continue
            }
        }
    }
    
    override fun visitDoStmt(stmt: Stmt.Do) {
        while (true) {
            try {
                visit(stmt.body)
            }
            catch (`break`: Redirect.Break) {
                break
            }
            catch (`continue`: Redirect.Continue) {
                continue
            }
            
            val condition = visit(stmt.condition) as? Result.Boolean ?: TODO()
            
            if (!condition.value) break
        }
    }
    
    override fun visitForStmt(stmt: Stmt.For) {
        val iterable = visit(stmt.iterable).iterable() ?: TODO()
        
        try {
            memory.push()
            
            for (result in iterable) {
                memory[stmt.pointer.value] = result
    
                try {
                    visit(stmt.body)
                }
                catch (`break`: Redirect.Break) {
                    break
                }
                catch (`continue`: Redirect.Continue) {
                    continue
                }
            }
        }
        finally {
            memory.pop()
        }
    }
    
    private fun Result<*>.iterable() =
        when (this) {
            is Result.String -> value.toCharArray().map { Result.String(it.toString()) }
            
            is Result.List   -> value
            
            is Result.Map    -> value.values.toList()
            
            else             -> null
        }
    
    override fun visitTryStmt(stmt: Stmt.Try) {
        try {
            visit(stmt.body)
        }
        catch (`throw`: Redirect.Throw) {
            try {
                memory.push()
                
                memory[stmt.error.value] = `throw`.value
                
                visit(stmt.catchBody)
            }
            finally {
                memory.pop()
            }
        }
        finally {
            visit(stmt.finallyBody)
        }
    }
    
    override fun visitBreakStmt(stmt: Stmt.Break) {
        throw Redirect.Break(stmt.location)
    }
    
    override fun visitContinueStmt(stmt: Stmt.Continue) {
        throw Redirect.Continue(stmt.location)
    }
    
    override fun visitThrowStmt(stmt: Stmt.Throw) {
        val value = visit(stmt.value)
        
        throw Redirect.Throw(stmt.location, value)
    }
    
    override fun visitReturnStmt(stmt: Stmt.Return) {
        val value = visit(stmt.value)
        
        throw Redirect.Return(stmt.location, value)
    }
    
    override fun visitFunctionStmt(stmt: Stmt.Function) {
        memory[stmt.name.value] = Result.Function(VoltFunction(stmt, memory.peek()))
    }
    
    override fun visitClassStmt(stmt: Stmt.Class) {
        memory[stmt.name.value] = Result.Class(VoltClass(stmt, memory.peek()))
    }
    
    override fun visitExpressionStmt(stmt: Stmt.Expression) {
        visit(stmt.expr)
    }
}