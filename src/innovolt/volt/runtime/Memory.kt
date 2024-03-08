package innovolt.volt.runtime

import java.util.*

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Memory.kt
 *
 * Created: Thursday, November 10, 2022, 15:40:27
 *
 * @author Christian Bryce Alexander
 */
class Memory : Stack<Memory.Scope>() {
    fun push() {
        if (isNotEmpty()) {
            push(Scope(peek()))
        }
        else {
            push(Scope())
        }
    }
    
    operator fun get(key: String) =
        peek()[key]
    
    operator fun set(key: String, value: VoltValue<*>) {
        peek()[key] = value
    }
    
    open class Scope(private val parent: Scope? = null) {
        private val vars = mutableMapOf<String, VoltValue<*>>()
        
        operator fun get(key: String): VoltValue<*> {
            var here: Scope? = this
            
            while (here != null && here.vars[key] == null) {
                here = here.parent
            }
            
            return here?.vars?.get(key) ?: VoltValue.Null
        }
        
        operator fun set(key: String, value: VoltValue<*>) {
            var here: Scope? = this
            
            while (here != null && here.vars[key] == null) {
                here = here.parent
            }
            
            if (here == null) here = this
            
            if (value === VoltValue.Null) {
                here.vars.remove(key)
            }
    
            here.vars[key] = value
        }
    }
}