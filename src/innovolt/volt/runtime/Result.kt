package innovolt.volt.runtime

import innovolt.volt.parser.Stmt

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
                
                is ListInstance            -> List(x)
                
                is MapInstance             -> Map(x)
                
                is FunctionInstance        -> Function(x)
                
                is Stmt.Class              -> Class(x)
                
                is ClassInstance           -> Instance(x)
                
                innovolt.volt.runtime.Null -> Null
                
                kotlin.Unit                -> Unit
                
                else                       -> TODO()
            }
    }
    
    val value: X
    
    class Boolean(override val value: kotlin.Boolean) : Result<kotlin.Boolean>
    
    class Number(override val value: Double) : Result<Double>
    
    class String(override val value: kotlin.String) : Result<kotlin.String>
    
    class List(override val value: ListInstance) : Result<ListInstance>
    
    class Map(override val value: MapInstance) : Result<MapInstance>
    
    class Function(override val value: FunctionInstance) : Result<FunctionInstance>
    
    class Class(override val value: Stmt.Class) : Result<Stmt.Class>
    
    class Instance(override val value: ClassInstance) : Result<ClassInstance>
    
    object Null : Result<innovolt.volt.runtime.Null> {
        override val value = innovolt.volt.runtime.Null
    }
    
    object Unit : Result<kotlin.Unit> {
        override val value = kotlin.Unit
    }
}