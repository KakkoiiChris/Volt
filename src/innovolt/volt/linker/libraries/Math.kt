package innovolt.volt.linker.libraries

import innovolt.volt.linker.Link
import innovolt.volt.linker.Linker
import innovolt.volt.runtime.Result
import innovolt.volt.util.Source
import innovolt.volt.util.VoltError
import kotlin.math.abs

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Math.kt
 *
 * Created: Friday, November 25, 2022, 00:01:13
 *
 * @author Christian Bryce Alexander
 */
object Math : Link {
    override val name = "math"
    
    override val source = Source.readLocal("/math.volt")
    
    override fun getLinks(linker: Linker) = with(linker) {
        addFunction(".abs", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("abs", "n", "Number")
            
            Result.Number(abs(n.value))
        }
    }
    
    override fun finalize() {
        TODO("Not yet implemented")
    }
    
}