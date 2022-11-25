package innovolt.volt.linker

import innovolt.volt.linker.libraries.Core
import innovolt.volt.parser.Expr
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
    
    private val functions = mutableMapOf<String, Link.Function>()
    private val classes = mutableMapOf<String, Link.Class>()
    
    init {
        this += Core
    }
    
    operator fun plusAssign(link: Link) {
        links[link.name] = link
    }
    
    fun import(name: Expr.Name): Source {
        val link = links[name.value] ?: VoltError.missingLink(name.value, name.location)
        
        functions.putAll(link.getFunctions())
        classes.putAll(link.getClasses())
        
        return link.source
    }
    
    fun getFunction(path: String) =
        functions[path]
    
    fun getClass(path: String) =
        classes[path]
}