package innovolt.volt.runtime

import innovolt.volt.linker.Linker
import innovolt.volt.parser.Expr
import innovolt.volt.parser.Program
import innovolt.volt.parser.Stmt
import innovolt.volt.util.VoltError
import innovolt.volt.util.toName
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
class Runtime(private val linker: Linker = Linker()) : Expr.Visitor<Result<*>>, Stmt.Visitor<Unit> {
    private val memory = Memory()
    
    fun start() {
        memory.push()
        
        val source = linker.import("core".toName())
        
        for (stmt in source.compile()) {
            visit(stmt)
        }
    }
    
    fun stop() {
        linker.wrapUp()

        memory.pop()
    }
    
    fun run(program: Program): Result<*> {
        try {
            for (stmt in program) {
                visit(stmt)
            }
        }
        catch (`return`: Redirect.Return) {
            return `return`.value
        }
        catch (`break`: Redirect.Break) {
            VoltError.unhandledBreak(`break`.origin)
        }
        catch (`continue`: Redirect.Continue) {
            VoltError.unhandledContinue(`continue`.origin)
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
        val condition = visit(expr.condition) as? Result.Boolean ?: VoltError.invalidCondition(expr.condition.location)
        
        return visit(if (condition.value) expr.yes else expr.no)
    }
    
    override fun visitBinaryExpr(expr: Expr.Binary) =
        when (expr.operator) {
            Expr.Binary.Operator.ADD           -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Number(left.value + right.value)
                    
                    else             -> VoltError.invalidRightOperand(right, expr.operator, expr.right.location)
                }
                
                else             -> VoltError.invalidLeftOperand(left, expr.operator, expr.left.location)
            }
            
            Expr.Binary.Operator.SUBTRACT      -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Number(left.value - right.value)
                    
                    else             -> VoltError.invalidRightOperand(right, expr.operator, expr.right.location)
                }
                
                else             -> VoltError.invalidLeftOperand(left, expr.operator, expr.left.location)
            }
            
            Expr.Binary.Operator.MULTIPLY      -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Number(left.value * right.value)
                    
                    else             -> VoltError.invalidRightOperand(right, expr.operator, expr.right.location)
                }
                
                else             -> VoltError.invalidLeftOperand(left, expr.operator, expr.left.location)
            }
            
            Expr.Binary.Operator.DIVIDE        -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Number(left.value / right.value)
                    
                    else             -> VoltError.invalidRightOperand(right, expr.operator, expr.right.location)
                }
                
                else             -> VoltError.invalidLeftOperand(left, expr.operator, expr.left.location)
            }
            
            Expr.Binary.Operator.MODULUS       -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Number(left.value % right.value)
                    
                    else             -> VoltError.invalidRightOperand(right, expr.operator, expr.right.location)
                }
                
                else             -> VoltError.invalidLeftOperand(left, expr.operator, expr.left.location)
            }
            
            Expr.Binary.Operator.EXPONENTIATE  -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Number(left.value.pow(right.value))
                    
                    else             -> VoltError.invalidRightOperand(right, expr.operator, expr.right.location)
                }
                
                else             -> VoltError.invalidLeftOperand(left, expr.operator, expr.left.location)
            }
            
            Expr.Binary.Operator.CONCATENATE   -> {
                val left = visit(expr.left)
                val right = visit(expr.right)
                
                Result.String("$left$right")
            }
            
            Expr.Binary.Operator.AND           -> {
                val left = visit(expr.left)
                
                left as? Result.Boolean ?: VoltError.invalidLeftOperand(left, expr.operator, expr.left.location)
                
                if (!left.value) {
                    Result.Boolean(false)
                }
                else {
                    val right = visit(expr.right)
                    
                    right as? Result.Boolean ?: VoltError.invalidRightOperand(right, expr.operator, expr.right.location)
                }
            }
            
            Expr.Binary.Operator.OR            -> {
                val left = visit(expr.left)
                
                left as? Result.Boolean ?: VoltError.invalidLeftOperand(left, expr.operator, expr.left.location)
                
                if (left.value) {
                    Result.Boolean(true)
                }
                else {
                    val right = visit(expr.right)
                    
                    right as? Result.Boolean ?: VoltError.invalidRightOperand(right, expr.operator, expr.right.location)
                }
            }
            
            Expr.Binary.Operator.LESS          -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Boolean(left.value < right.value)
                    
                    else             -> VoltError.invalidRightOperand(right, expr.operator, expr.right.location)
                }
                
                else             -> VoltError.invalidLeftOperand(left, expr.operator, expr.left.location)
            }
            
            Expr.Binary.Operator.LESS_EQUAL    -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Boolean(left.value <= right.value)
                    
                    else             -> VoltError.invalidRightOperand(right, expr.operator, expr.right.location)
                }
                
                else             -> VoltError.invalidLeftOperand(left, expr.operator, expr.left.location)
            }
            
            Expr.Binary.Operator.GREATER       -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Boolean(left.value > right.value)
                    
                    else             -> VoltError.invalidRightOperand(right, expr.operator, expr.right.location)
                }
                
                else             -> VoltError.invalidLeftOperand(left, expr.operator, expr.left.location)
            }
            
            Expr.Binary.Operator.GREATER_EQUAL -> when (val left = visit(expr.left)) {
                is Result.Number -> when (val right = visit(expr.right)) {
                    is Result.Number -> Result.Boolean(left.value >= right.value)
                    
                    else             -> VoltError.invalidRightOperand(right, expr.operator, expr.right.location)
                }
                
                else             -> VoltError.invalidLeftOperand(left, expr.operator, expr.left.location)
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
                
                else             -> VoltError.invalidOperand(right, expr.operator, expr.right.location)
            }
            
            Expr.Prefix.Operator.NOT    -> when (val right = visit(expr.right)) {
                is Result.Boolean -> Result.Boolean(!right.value)
                
                else              -> VoltError.invalidOperand(right, expr.operator, expr.right.location)
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
        val target = visit(expr.target)
        
        target as? Result.Instance ?: VoltError.nonAccessedValue(target, expr.target.location)
        
        return target.value[expr.member.value]
    }
    
    override fun visitSetMemberExpr(expr: Expr.SetMember): Result<*> {
        val target = visit(expr.target)
        
        target as? Result.Instance ?: VoltError.nonAccessedValue(target, expr.target.location)
        
        val value = visit(expr.value)
        
        target.value[expr.member.value] = value
        
        return value
    }
    
    override fun visitGetIndexExpr(expr: Expr.GetIndex) =
        when (val target = visit(expr.target)) {
            is Result.String -> when (val index = visit(expr.index)) {
                is Result.Number -> Result.String(target.value[index.value.toInt()].toString())
                
                else             -> VoltError.invalidIndex(target, index, expr.index.location)
            }
            
            is Result.List   -> when (val index = visit(expr.index)) {
                is Result.Number -> target.value[index.value.toInt()]
                
                else             -> VoltError.invalidIndex(target, index, expr.index.location)
            }
            
            is Result.Map    -> when (val index = visit(expr.index)) {
                is Result.String -> target.value[index.value] ?: Result.Null
                
                else             -> VoltError.invalidIndex(target, index, expr.index.location)
            }
            
            else             -> VoltError.nonIndexedValue(target, expr.target.location)
        }
    
    override fun visitSetIndexExpr(expr: Expr.SetIndex) =
        when (val target = visit(expr.target)) {
            is Result.List -> when (val index = visit(expr.index)) {
                is Result.Number -> {
                    val value = visit(expr.value)
                    
                    target.value[index.value.toInt()] = value
                    
                    value
                }
                
                else             -> VoltError.invalidIndex(target, index, expr.index.location)
            }
            
            is Result.Map  -> when (val index = visit(expr.index)) {
                is Result.String -> {
                    val value = visit(expr.value)
                    
                    target.value[index.value] = value
                    
                    value
                }
                
                else             -> VoltError.invalidIndex(target, index, expr.index.location)
            }
            
            else           -> VoltError.nonIndexedValue(target, expr.target.location)
        }
    
    override fun visitInvokeExpr(expr: Expr.Invoke): Result<*> {
        val target = visit(expr.target)
        
        val callable = target.value as? Callable ?: VoltError.nonInvokedValue(target, expr.target.location)
        
        if (callable.params.size != expr.arguments.size) VoltError.argumentResolution(callable, expr.target.location)
        
        val variables = callable.params.zip(expr.arguments.map { visit(it) })
        
        return when (callable) {
            is VoltFunction -> invokeFunction(callable, variables)
            
            is VoltClass    -> invokeClass(callable, variables)
        }
    }
    
    private fun invokeFunction(function: VoltFunction, variables: List<Pair<Expr.Name, Result<*>>>): Result<*> {
        val link = function.link
        
        if (link != null) {
            val args = variables.map { it.second }
            
            if (!function.link.resolve(args)) VoltError.argumentLinkResolution(function, function.location)
            
            val scope = function.scope
            
            val instance = if (scope is VoltInstance)
                Result.Instance(scope)
            else
                null
            
            return link(this, instance, args)
        }
        
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
    
    private fun invokeClass(`class`: VoltClass, variables: List<Pair<Expr.Name, Result<*>>>): Result.Instance {
        val instance = VoltInstance(`class`, this)
        
        try {
            memory.push(instance)
            
            for ((name, value) in variables) {
                memory[name.value] = value
            }
            
            for (stmt in `class`.init.stmts) {
                visit(stmt)
            }
        }
        finally {
            memory.pop()
        }
        
        return Result.Instance(instance)
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
        Result.Function(VoltFunction(expr.function, memory.peek(), null))
    
    override fun visitNameExpr(expr: Expr.Name) =
        memory[expr.value]
    
    override fun visitValueExpr(expr: Expr.Value) =
        Result.of(expr.value) ?: error("BROKEN VALUE '${expr.value}'")
    
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
        val condition = visit(stmt.condition) as? Result.Boolean ?: VoltError.invalidCondition(stmt.condition.location)
        
        if (condition.value) {
            visit(stmt.body)
        }
        else {
            visit(stmt.`else`)
        }
    }
    
    override fun visitWhileStmt(stmt: Stmt.While) {
        while (true) {
            val condition = visit(stmt.condition) as? Result.Boolean ?: VoltError.invalidCondition(stmt.condition.location)
            
            if (!condition.value) break
            
            try {
                visit(stmt.body)
            }
            catch (`break`: Redirect.Break) {
                if (`break`.label != stmt.label) {
                    throw `break`
                }
                
                break
            }
            catch (`continue`: Redirect.Continue) {
                if (`continue`.label != stmt.label) {
                    throw `continue`
                }
                
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
                if (`break`.label != stmt.label) {
                    throw `break`
                }
                
                break
            }
            catch (`continue`: Redirect.Continue) {
                if (`continue`.label != stmt.label) {
                    throw `continue`
                }
                
                continue
            }
            
            val condition = visit(stmt.condition) as? Result.Boolean ?: VoltError.invalidCondition(stmt.condition.location)
            
            if (!condition.value) break
        }
    }
    
    override fun visitForStmt(stmt: Stmt.For) {
        val iterable = visit(stmt.iterable)
        
        val list = iterable.iterable() ?: VoltError.nonIterableValue(iterable, stmt.iterable.location)
        
        try {
            memory.push()
            
            for (result in list) {
                memory[stmt.pointer.value] = result
                
                try {
                    visit(stmt.body)
                }
                catch (`break`: Redirect.Break) {
                    if (`break`.label != stmt.label) {
                        throw `break`
                    }
                    
                    break
                }
                catch (`continue`: Redirect.Continue) {
                    if (`continue`.label != stmt.label) {
                        throw `continue`
                    }
                    
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
        throw Redirect.Break(stmt.location, stmt.label)
    }
    
    override fun visitContinueStmt(stmt: Stmt.Continue) {
        throw Redirect.Continue(stmt.location, stmt.label)
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
        var link: Linker.Function? = null
        
        if (stmt.isLinked) {
            link = linker.getFunction(stmt.path) ?: VoltError.noLink(stmt, stmt.location)
        }
        
        memory[stmt.name.value] = Result.Function(VoltFunction(stmt, memory.peek(), link))
    }
    
    override fun visitClassStmt(stmt: Stmt.Class) {
        memory[stmt.name.value] = Result.Class(VoltClass(stmt, memory.peek()))
    }
    
    override fun visitImportStmt(stmt: Stmt.Import) {
        val source = linker.import(stmt.name)
        
        for (subStmt in source.compile()) {
            visit(subStmt)
        }
    }
    
    override fun visitExpressionStmt(stmt: Stmt.Expression) {
        visit(stmt.expr)
    }
}