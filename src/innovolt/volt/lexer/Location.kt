package innovolt.volt.lexer

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Location.kt
 *
 * Created: Friday, November 04, 2022, 00:38:34
 *
 * @author Christian Bryce Alexander
 */
data class Location(val name: String, val row: Int, val column: Int) {
    companion object {
        val none = Location("", 0, 0)
    }
    
    override fun toString() =
        if (name.isNotEmpty()) " @ $name.vt ($row, $column)" else ""
}