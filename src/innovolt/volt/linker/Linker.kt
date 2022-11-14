package innovolt.volt.linker

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
    private val links = mutableListOf<Link>()
    
    private val functions = mutableMapOf<String, Link.Function>()
    private val classes = mutableMapOf<String, Link.Class>()
    
    init {
        addLink(CoreLink)
    }
    
    fun addLink(link: Link) {
        links += link
        
        functions.putAll(link.getFunctions())
        classes.putAll(link.getClasses())
    }
    
    fun getSources() =
        links.map { it.source }
    
    fun getFunction(path: String) =
        functions[path]
    
    fun getClass(path: String) =
        classes[path]
}