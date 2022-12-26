package innovolt.volt.linker

import innovolt.volt.linker.libraries.Core
import innovolt.volt.linker.libraries.Math
import innovolt.volt.parser.Expr
import innovolt.volt.runtime.Result
import innovolt.volt.runtime.Runtime
import innovolt.volt.util.Source
import innovolt.volt.util.VoltError

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Linker.kt
 *
 * Created: Saturday, November 12, 2022, 22:22:44
 *
 * @author Christian Bryce Alexander
 */
class Linker {
    private val links = mutableMapOf<String, Link>()
    
    private val functions = mutableMapOf<String, Function>()
    private val classes = mutableMapOf<String, Class>()
    
    init {
        this += Core
        this += Math
    }
    
    operator fun plusAssign(link: Link) {
        links[link.name] = link
    }
    
    fun import(name: Expr.Name): Source {
        val link = links[name.value] ?: VoltError.missingLink(name.value, name.location)
        
        link.getLinks(this)
        
        return link.source
    }
    
    fun addFunction(path: String, arity: Int = 0, method: (Runtime, LinkData) -> Result<*>) {
        functions[path] = Function(path, arity, method)
    }
    
    fun addClass(path: String, method: (Runtime, Result.Instance) -> Result<*>) {
        classes[path] = Class(method)
    }
    
    fun getFunction(path: String) =
        functions[path]
    
    fun getClass(path: String) =
        classes[path]

    fun wrapUp() {
        links.values.forEach(Link::wrapUp)
    }

    class Function internal constructor(private val path: String, private val arity: Int, private val method: (Runtime, LinkData) -> Result<*>) {
        fun resolve(args: List<Result<*>>) =
            args.size == arity
        
        operator fun invoke(runtime: Runtime, instance: Result.Instance?, args: List<Result<*>>) =
            method(runtime, LinkData(instance, args))
    }
    
    class Class internal constructor(private val method: (Runtime, Result.Instance) -> Result<*>) {
        operator fun invoke(runtime: Runtime, instance: Result.Instance) {
            instance.value["\$link"] = method(runtime, instance)
        }
    }
}