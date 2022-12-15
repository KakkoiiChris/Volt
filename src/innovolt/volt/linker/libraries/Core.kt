package innovolt.volt.linker.libraries

import innovolt.volt.lexer.Location
import innovolt.volt.linker.Link
import innovolt.volt.linker.Linker
import innovolt.volt.parser.Expr
import innovolt.volt.runtime.Result
import innovolt.volt.util.Source
import innovolt.volt.util.VoltError
import kotlin.concurrent.thread
import kotlin.system.exitProcess

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    CoreLink.kt
 *
 * Created: Saturday, November 12, 2022, 23:41:12
 *
 * @author Christian Bryce Alexander
 */
object Core : Link {
    override val name = "core"
    
    override val source = Source.readLocal("/core.volt")
    
    override fun getLinks(linker: Linker) = with(linker) {
        addFunction(".read") { _, _ ->
            Result.String(readln())
        }
        
        addFunction(".write", 1) { _, data ->
            println(data.args[0])
            
            Result.Unit
        }
        
        addFunction(".time") { _, _ ->
            Result.Number(System.nanoTime() / 1E9)
        }
        
        addFunction(".add", 2) {_,data->
            val (list, element) = data.args
            
            list as? Result.List?:VoltError.invalidLinkArgument("add", "list", "List")
            
            list.value.add(element)
            
            Result.Unit
        }
        
        addFunction(".pause", 1) { _, data ->
            val (seconds) = data.args
            
            seconds as? Result.Number ?: VoltError.invalidLinkArgument("pause", "seconds", "Number")
            
            Thread.sleep((seconds.value * 1000).toLong())
            
            Result.Unit
        }
        
        addFunction(".run", 1) { runtime, data ->
            val (handler) = data.args
            
            handler as? Result.Function ?: VoltError.invalidLinkArgument("run", "handler", "Function")
            
            val invoke = Expr.Invoke(Location.none, Expr.Value(Location.none, handler), emptyList())
            
            thread {
                runtime.visit(invoke)
            }
            
            Result.Unit
        }
        
        addFunction(".exit", 1) { _, data ->
            val (code) = data.args
            
            code as? Result.Number ?: VoltError.invalidLinkArgument("exit", "code", "Number")
            
            exitProcess(code.value.toInt())
        }
    }
    
    override fun finalize() {
    }
}