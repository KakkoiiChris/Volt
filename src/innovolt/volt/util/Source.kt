package innovolt.volt.util

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Source.kt
 *
 * Created: Thursday, November 03, 2022, 23:54:56
 *
 * @author Christian Bryce Alexander
 */
data class Source(val name: String, val text: String) {
    companion object {
        fun readLocal(path: String): Source {
            val name = path.substring(path.lastIndexOf('/') + 1, path.indexOf('.'))
            
            val text = String(Files.readAllBytes(Paths.get(Source::class.java.getResource(path)!!.toURI())))
            
            return Source(name, text)
        }
    
        fun read(path: String): Source {
            val name = path.substring(path.lastIndexOf('/') + 1, path.indexOf('.'))
        
            val text = String(Files.readAllBytes(Paths.get(path)))
            
            return Source(name, text)
        }
    }
}