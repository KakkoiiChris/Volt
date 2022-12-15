package innovolt.volt.linker

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
    
    fun getLinks(linker: Linker)
    
    fun finalize()
}