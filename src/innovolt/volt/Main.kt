package innovolt.volt

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

private fun repl() {}

private fun file(path: String) {

}

private fun exec() {}