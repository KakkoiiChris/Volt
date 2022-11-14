package innovolt.volt.parser

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    ClassPath.kt
 *
 * Created: Saturday, November 12, 2022, 23:46:44
 *
 * @author Christian Bryce Alexander
 */
class ClassPath {
    private val path = mutableListOf<String>()
    
    private fun peek() =
        if (path.isNotEmpty())
            path.last()
        else
            null
    
    fun push(name: String) {
        path.add(name)
    }
    
    fun pop() {
        path.remove(peek())
    }
    
    fun toPath(functionName: String = ""): String {
        val classes = path.joinToString(".")
        
        return if (functionName.isNotEmpty())
            "$classes.$functionName"
        else
            classes
    }
}