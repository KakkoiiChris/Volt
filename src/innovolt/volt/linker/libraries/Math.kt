package innovolt.volt.linker.libraries

import innovolt.volt.linker.Link
import innovolt.volt.linker.Linker
import innovolt.volt.runtime.Result
import innovolt.volt.util.Source
import innovolt.volt.util.VoltError
import java.lang.Math
import kotlin.math.*

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
@OptIn(ExperimentalStdlibApi::class)
object Math : Link {
    override val name = "math"
    
    override val source = Source.readLocal("/math.volt")
    
    override fun getLinks(linker: Linker) = with(linker) {
        addFunction(".sin", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("sin", "n", "Number")
            
            Result.Number(sin(n.value))
        }
        
        addFunction(".cos", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("cos", "n", "Number")
            
            Result.Number(cos(n.value))
        }
        
        addFunction(".tan", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("tan", "n", "Number")
            
            Result.Number(tan(n.value))
        }
        
        addFunction(".asin", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("asin", "n", "Number")
            
            Result.Number(asin(n.value))
        }
        
        addFunction(".acos", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("acos", "n", "Number")
            
            Result.Number(acos(n.value))
        }
        
        addFunction(".atan", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("atan", "n", "Number")
            
            Result.Number(atan(n.value))
        }
        
        addFunction(".atan2", 2) { _, data ->
            val (y, x) = data.args
            
            y as? Result.Number ?: VoltError.invalidLinkArgument("atan2", "y", "Number")
            x as? Result.Number ?: VoltError.invalidLinkArgument("atan2", "x", "Number")
            
            Result.Number(atan2(y.value, x.value))
        }
        
        addFunction(".sinh", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("sinh", "n", "Number")
            
            Result.Number(sinh(n.value))
        }
        
        addFunction(".cosh", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("cosh", "n", "Number")
            
            Result.Number(cosh(n.value))
        }
        
        addFunction(".tanh", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("tanh", "n", "Number")
            
            Result.Number(tanh(n.value))
        }
        
        addFunction(".asinh", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("asinh", "n", "Number")
            
            Result.Number(asinh(n.value))
        }
        
        addFunction(".acosh", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("acosh", "n", "Number")
            
            Result.Number(acosh(n.value))
        }
        
        addFunction(".atanh", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("atanh", "n", "Number")
            
            Result.Number(atanh(n.value))
        }
        
        addFunction(".hypot", 2) { _, data ->
            val (x, y) = data.args
            
            x as? Result.Number ?: VoltError.invalidLinkArgument("hypot", "x", "Number")
            y as? Result.Number ?: VoltError.invalidLinkArgument("hypot", "y", "Number")
            
            Result.Number(hypot(x.value, y.value))
        }
        
        addFunction(".sqrt", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("sqrt", "n", "Number")
            
            Result.Number(sqrt(n.value))
        }
        
        addFunction(".cbrt", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("cbrt", "n", "Number")
            
            Result.Number(Math.cbrt(n.value))
        }
        
        addFunction(".pow", 2) { _, data ->
            val (n, exponent) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("pow", "n", "Number")
            exponent as? Result.Number ?: VoltError.invalidLinkArgument("pow", "exponent", "Number")
            
            Result.Number(n.value.pow(exponent.value))
        }
        
        addFunction(".exp", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("exp", "n", "Number")
            
            Result.Number(exp(n.value))
        }
        
        addFunction(".expm1", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("expm1", "n", "Number")
            
            Result.Number(expm1(n.value))
        }
        
        addFunction(".log", 2) { _, data ->
            val (n, base) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("log", "n", "Number")
            base as? Result.Number ?: VoltError.invalidLinkArgument("log", "base", "Number")
            
            Result.Number(log(n.value, base.value))
        }
        
        addFunction(".ln", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("ln", "n", "Number")
            
            Result.Number(ln(n.value))
        }
        
        addFunction(".log10", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("log10", "n", "Number")
            
            Result.Number(log10(n.value))
        }
        
        addFunction(".log2", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("log2", "n", "Number")
            
            Result.Number(log2(n.value))
        }
        
        addFunction(".ln1p", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("ln1p", "n", "Number")
            
            Result.Number(ln1p(n.value))
        }
        
        addFunction(".ceil", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("ceil", "n", "Number")
            
            Result.Number(ceil(n.value))
        }
        
        addFunction(".floor", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("floor", "n", "Number")
            
            Result.Number(floor(n.value))
        }
        
        addFunction(".truncate", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("truncate", "n", "Number")
            
            Result.Number(truncate(n.value))
        }
        
        addFunction(".round", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("round", "n", "Number")
            
            Result.Number(round(n.value))
        }
        
        addFunction(".abs", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("abs", "n", "Number")
            
            Result.Number(abs(n.value))
        }
        
        addFunction(".min", 2) { _, data ->
            val (a, b) = data.args
            
            a as? Result.Number ?: VoltError.invalidLinkArgument("min", "a", "Number")
            b as? Result.Number ?: VoltError.invalidLinkArgument("min", "b", "Number")
            
            Result.Number(min(a.value, b.value))
        }
        
        addFunction(".max", 2) { _, data ->
            val (a, b) = data.args
            
            a as? Result.Number ?: VoltError.invalidLinkArgument("max", "a", "Number")
            b as? Result.Number ?: VoltError.invalidLinkArgument("max", "b", "Number")
            
            Result.Number(max(a.value, b.value))
        }
        
        addFunction(".sign", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("sign", "n", "Number")
            
            Result.Number(sign(n.value))
        }
        
        addFunction(".withSign", 2) { _, data ->
            val (a, b) = data.args
            
            a as? Result.Number ?: VoltError.invalidLinkArgument("withSign", "a", "Number")
            b as? Result.Number ?: VoltError.invalidLinkArgument("withSign", "b", "Number")
            
            Result.Number(a.value.withSign(b.value))
        }
        
        addFunction(".ulp", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("ulp", "n", "Number")
            
            Result.Number(n.value.ulp)
        }
        
        addFunction(".nextUp", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("nextUp", "n", "Number")
            
            Result.Number(n.value.nextUp())
        }
        
        addFunction(".nextDown", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("nextDown", "n", "Number")
            
            Result.Number(n.value.nextDown())
        }
        
        addFunction(".nextTowards", 2) { _, data ->
            val (n, to) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("nextTowards", "n", "Number")
            to as? Result.Number ?: VoltError.invalidLinkArgument("nextTowards", "to", "Number")
            
            Result.Number(n.value.nextTowards(to.value))
        }
        
        addFunction(".isNaN", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("isNaN", "n", "Number")
            
            Result.Boolean(n.value.isNaN())
        }
        
        addFunction(".isFinite", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("isFinite", "n", "Number")
            
            Result.Boolean(n.value.isFinite())
        }
        
        addFunction(".fact", 1) { _, data ->
            val (n) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("fact", "n", "Number")
            
            Result.Number(fact(n.value))
        }
        
        addFunction(".comb", 2) { _, data ->
            val (n, k) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("comb", "n", "Number")
            k as? Result.Number ?: VoltError.invalidLinkArgument("comb", "k", "Number")
            
            Result.Number(comb(n.value, k.value))
        }
        
        addFunction(".perm", 2) { _, data ->
            val (n, k) = data.args
            
            n as? Result.Number ?: VoltError.invalidLinkArgument("perm", "n", "Number")
            k as? Result.Number ?: VoltError.invalidLinkArgument("perm", "k", "Number")
            
            Result.Number(perm(n.value, k.value))
        }
        
        addFunction(".sum", 1) { _, data ->
            val (list) = data.args
            
            list as? Result.List ?: VoltError.invalidLinkArgument("sum", "list", "List")
            
            if (list.value.any { it !is Result.Number }) TODO("SUM LIST MUST BE ALL NUMBERS")
            
            var sum = 0.0
            
            for (number in list.value.map { it as Result.Number }) {
                sum += number.value
            }
            
            Result.Number(sum)
        }
        
        addFunction(".prod", 1) { _, data ->
            val (list) = data.args
            
            list as? Result.List ?: VoltError.invalidLinkArgument("sum", "list", "List")
            
            if (list.value.any { it !is Result.Number }) TODO("SUM LIST MUST BE ALL NUMBERS")
            
            var prod = 1.0
            
            for (number in list.value.map { it as Result.Number }) {
                prod *= number.value
            }
            
            Result.Number(prod)
        }
    }
    
    private fun fact(n: Double): Double {
        if (n == 1.0) return 1.0
        
        return n * fact(n - 1)
    }
    
    private fun perm(n: Double, k: Double) =
        fact(n) / fact(n - k)
    
    private fun comb(n: Double, k: Double) =
        fact(n) / (fact(n - k) * fact(k))
    
    override fun wrapUp() = Unit
}