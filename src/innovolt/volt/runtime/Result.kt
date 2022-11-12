package innovolt.volt.runtime

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Result.kt
 *
 * Created: Thursday, November 10, 2022, 15:03:49
 *
 * @author Christian Bryce Alexander
 */
interface Result<X> {
    companion object {
        fun of(x: Any) =
            when (x) {
                is Result<*>               -> x
                
                is kotlin.Boolean          -> Boolean(x)
                
                is Double                  -> Number(x)
                
                is kotlin.String           -> String(x)
                
                is VoltList                -> List(x)
                
                is VoltMap                 -> Map(x)
                
                is VoltFunction            -> Function(x)
                
                is VoltClass               -> Class(x)
                
                is VoltInstance            -> Instance(x)
                
                innovolt.volt.runtime.Null -> Null
                
                kotlin.Unit                -> Unit
                
                else                       -> TODO()
            }
    }
    
    val value: X
    
    class Boolean(override val value: kotlin.Boolean) : Result<kotlin.Boolean>
    
    class Number(override val value: Double) : Result<Double>
    
    class String(override val value: kotlin.String) : Result<kotlin.String>
    
    class List(override val value: VoltList) : Result<VoltList>
    
    class Map(override val value: VoltMap) : Result<VoltMap>
    
    class Function(override val value: VoltFunction) : Result<VoltFunction>
    
    class Class(override val value: VoltClass) : Result<VoltClass>
    
    class Instance(override val value: VoltInstance) : Result<VoltInstance>
    
    object Null : Result<innovolt.volt.runtime.Null> {
        override val value = innovolt.volt.runtime.Null
    }
    
    object Unit : Result<kotlin.Unit> {
        override val value = kotlin.Unit
    }
}