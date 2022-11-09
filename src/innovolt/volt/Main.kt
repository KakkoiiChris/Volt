package innovolt.volt

import innovolt.volt.util.Source

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Main.kt
 *
 * Created: Thursday, November 03, 2022, 23:48:04
 *
 * @author Christian Bryce Alexander
 */
fun main(args: Array<String>) = when (args.size) {
    0    -> repl()
    
    1    -> file(args[0])
    
    else -> error("Usage: volt [fileName]")
}

private fun repl() {
    do {
        print("VOLT> ")
        
        val text = readln().takeIf { it.isNotEmpty() } ?: break
        
        val source = Source("REPL", text)
        
        exec(source)
    }
    while (true)
}

private fun file(path: String) {
    val source = Source.read(path)
    
    exec(source)
}

private fun exec(source: Source) {
    val program = source.compile()
}