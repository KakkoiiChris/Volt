package innovolt.volt.linker

import innovolt.volt.runtime.Result
import innovolt.volt.util.Source

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
        
        functions[".write"] = Link.Function.create(1) { _, data ->
            println(data.args[0])
            
            Result.Unit
        }
        
        return functions
    }
    
    override fun getClasses(): Map<String, Link.Class> {
        return emptyMap()
    }
    
    override fun finalize() {
    }
}