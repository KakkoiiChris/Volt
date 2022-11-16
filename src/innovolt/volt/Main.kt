package innovolt.volt

import innovolt.volt.runtime.Runtime
import innovolt.volt.util.Source
import innovolt.volt.util.VoltError
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

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
    val runtime = Runtime()
    
    try {
        runtime.start()
        
        do {
            print("VOLT> ")
            
            val text = readln().takeIf { it.isNotEmpty() } ?: break
            
            try {
                val source = Source("REPL", text)
                
                exec(runtime, source)
            }
            catch (error: VoltError) {
                val source = Source("REPL", "return $text;")
                
                exec(runtime, source)
            }
        }
        while (true)
    }
    finally {
        runtime.stop()
    }
}

private fun file(path: String) {
    val runtime = Runtime()
    
    runtime.start()
    
    val source = Source.read(path)
    
    exec(runtime, source)
    
    runtime.stop()
}

@OptIn(ExperimentalTime::class)
private fun exec(runtime: Runtime, source: Source) {
    val program = source.compile()
    
    val (result, time) = measureTimedValue {
        try {
            runtime.run(program)
        }
        catch (error: VoltError) {
            error.printStackTrace()
        }
    }
    
    println("Done: $result (${time.inWholeNanoseconds / 1E6} ms)")
}