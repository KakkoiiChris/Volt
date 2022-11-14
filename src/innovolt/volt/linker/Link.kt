package innovolt.volt.linker

import innovolt.volt.runtime.Result
import innovolt.volt.runtime.Runtime
import innovolt.volt.util.Source

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Link.kt
 *
 * Created: Saturday, November 12, 2022, 22:32:12
 *
 * @author Christian Bryce Alexander
 */
interface Link {
    val name: String
    
    val source: Source
    
    fun getFunctions(): Map<String, Function>
    
    fun getClasses(): Map<String, Class>
    
    fun finalize()
    
    class Function(private val arity: Int, private val method: (Runtime, LinkData) -> Result<*>) {
        companion object {
            fun create(arity: Int = 0, method: (runtime: Runtime, data: LinkData) -> Result<*>) =
                Function(arity, method)
        }
        
        fun resolve(args: List<Result<*>>) =
            args.size == arity
        
        operator fun invoke(runtime: Runtime, instance: Result.Instance?, args: List<Result<*>>) =
            method(runtime, LinkData(instance, args))
    }
    
    class Class(private val method: (Runtime, Result.Instance) -> Result<*>) {
        companion object {
            fun create(method: (runtime: Runtime, instance: Result.Instance) -> Result<*>) =
                Class(method)
        }
        
        operator fun invoke(runtime: Runtime, instance: Result.Instance) {
            instance.value["\$link"] = method(runtime, instance)
        }
    }
}