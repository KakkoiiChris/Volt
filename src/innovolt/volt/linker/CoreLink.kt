package innovolt.volt.linker

import innovolt.volt.runtime.Result
import innovolt.volt.util.Source
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
object CoreLink : Link {
    override val name = "core"
    
    override val source = Source.readLocal("/core.volt")
    
    override fun getFunctions(): Map<String, Link.Function> {
        val functions = mutableMapOf<String, Link.Function>()
        
        functions[".read"] = Link.Function.create { _, _ ->
            Result.String(readln())
        }
        
        functions[".write"] = Link.Function.create(1) { _, data ->
            println(data.args[0])
            
            Result.Unit
        }
        
        functions[".time"] = Link.Function.create { _, _ ->
            Result.Number(System.nanoTime() / 1E9)
        }
        
        functions[".pause"] = Link.Function.create(1) { _, data ->
            val (seconds) = data.args
            
            seconds as? Result.Number ?: TODO()
            
            Thread.sleep((seconds.value * 1000).toLong())
            
            Result.Unit
        }
        
        functions[".exit"] = Link.Function.create(1) { _, data ->
            val (code) = data.args
            
            code as? Result.Number ?: TODO()
            
            exitProcess(code.value.toInt())
        }
        
        return functions
    }
    
    override fun getClasses(): Map<String, Link.Class> {
        return emptyMap()
    }
    
    override fun finalize() {
    }
}