package innovolt.volt.linker.libraries

import innovolt.volt.linker.Link
import innovolt.volt.linker.Linker
import innovolt.volt.runtime.VoltValue
import innovolt.volt.util.Source
import innovolt.volt.util.VoltError
import kotlin.math.*
import kotlin.random.Random

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
        addFunction(".sin", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("sin", "n", "Number")

            n.map(::sin)
        }

        addFunction(".cos", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("cos", "n", "Number")

            n.map(::cos)
        }

        addFunction(".tan", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("tan", "n", "Number")

            n.map(::tan)
        }

        addFunction(".asin", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("asin", "n", "Number")

            n.map(::asin)
        }

        addFunction(".acos", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("acos", "n", "Number")

            n.map(::acos)
        }

        addFunction(".atan", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("atan", "n", "Number")

            n.map(::atan)
        }

        addFunction(".atan2", 2) { _, data ->
            val (y, x) = data.args

            y as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("atan2", "y", "Number")
            x as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("atan2", "x", "Number")

            VoltValue.Number(atan2(y.value, x.value))
        }

        addFunction(".sinh", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("sinh", "n", "Number")

            n.map(::sinh)
        }

        addFunction(".cosh", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("cosh", "n", "Number")

            n.map(::cosh)
        }

        addFunction(".tanh", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("tanh", "n", "Number")

            n.map(::tanh)
        }

        addFunction(".asinh", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("asinh", "n", "Number")

            n.map(::asinh)
        }

        addFunction(".acosh", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("acosh", "n", "Number")

            n.map(::acosh)
        }

        addFunction(".atanh", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("atanh", "n", "Number")

            n.map(::atanh)
        }

        addFunction(".hypot", 2) { _, data ->
            val (x, y) = data.args

            x as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("hypot", "x", "Number")
            y as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("hypot", "y", "Number")

            VoltValue.Number(hypot(x.value, y.value))
        }

        addFunction(".sqrt", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("sqrt", "n", "Number")

            n.map(::sqrt)
        }

        addFunction(".cbrt", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("cbrt", "n", "Number")

            n.map(::cbrt)
        }

        addFunction(".pow", 2) { _, data ->
            val (n, exponent) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("pow", "n", "Number")
            exponent as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("pow", "exponent", "Number")

            VoltValue.Number(n.value.pow(exponent.value))
        }

        addFunction(".exp", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("exp", "n", "Number")

            n.map(::exp)
        }

        addFunction(".expm1", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("expm1", "n", "Number")

            n.map(::expm1)
        }

        addFunction(".log", 2) { _, data ->
            val (n, base) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("log", "n", "Number")
            base as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("log", "base", "Number")

            VoltValue.Number(log(n.value, base.value))
        }

        addFunction(".ln", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("ln", "n", "Number")

            n.map(::ln)
        }

        addFunction(".log10", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("log10", "n", "Number")

            n.map(::log10)
        }

        addFunction(".log2", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("log2", "n", "Number")

            n.map(::log2)
        }

        addFunction(".ln1p", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("ln1p", "n", "Number")

            n.map(::ln1p)
        }

        addFunction(".ceil", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("ceil", "n", "Number")

            n.map(::ceil)
        }

        addFunction(".floor", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("floor", "n", "Number")

            n.map(::floor)
        }

        addFunction(".truncate", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("truncate", "n", "Number")

            n.map(::truncate)
        }

        addFunction(".round", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("round", "n", "Number")

            n.map(::round)
        }

        addFunction(".abs", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("abs", "n", "Number")

            n.map(::abs)
        }

        addFunction(".min", 2) { _, data ->
            val (a, b) = data.args

            a as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("min", "a", "Number")
            b as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("min", "b", "Number")

            VoltValue.Number(min(a.value, b.value))
        }

        addFunction(".max", 2) { _, data ->
            val (a, b) = data.args

            a as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("max", "a", "Number")
            b as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("max", "b", "Number")

            VoltValue.Number(max(a.value, b.value))
        }

        addFunction(".sign", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("sign", "n", "Number")

            n.map(::sign)
        }

        addFunction(".withSign", 2) { _, data ->
            val (a, b) = data.args

            a as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("withSign", "a", "Number")
            b as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("withSign", "b", "Number")

            VoltValue.Number(a.value.withSign(b.value))
        }

        addFunction(".ulp", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("ulp", "n", "Number")

            n.map(Double::ulp)
        }

        addFunction(".nextUp", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("nextUp", "n", "Number")

            n.map(Double::nextUp)
        }

        addFunction(".nextDown", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("nextDown", "n", "Number")

            n.map(Double::nextDown)
        }

        addFunction(".nextTowards", 2) { _, data ->
            val (n, to) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("nextTowards", "n", "Number")
            to as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("nextTowards", "to", "Number")

            VoltValue.Number(n.value.nextTowards(to.value))
        }

        addFunction(".isNaN", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("isNaN", "n", "Number")

            n.map(Double::isNaN)
        }

        addFunction(".isFinite", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("isFinite", "n", "Number")

            n.map(Double::isFinite)
        }

        addFunction(".fact", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("fact", "n", "Number")

            n.map(::fact)
        }

        addFunction(".comb", 2) { _, data ->
            val (n, k) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("comb", "n", "Number")
            k as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("comb", "k", "Number")

            VoltValue.Number(comb(n.value, k.value))
        }

        addFunction(".perm", 2) { _, data ->
            val (n, k) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("perm", "n", "Number")
            k as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("perm", "k", "Number")

            VoltValue.Number(perm(n.value, k.value))
        }

        addFunction(".sum", 1) { _, data ->
            val (list) = data.args

            list as? VoltValue.List ?: VoltError.invalidLinkFunctionArgument("sum", "list", "List")

            if (list.value.any { it !is VoltValue.Number }) VoltError.invalidListElementType("Number")

            var sum = 0.0

            for (number in list.value.map { it as VoltValue.Number }) {
                sum += number.value
            }

            VoltValue.Number(sum)
        }

        addFunction(".prod", 1) { _, data ->
            val (list) = data.args

            list as? VoltValue.List ?: VoltError.invalidLinkFunctionArgument("sum", "list", "List")

            if (list.value.any { it !is VoltValue.Number }) VoltError.invalidListElementType("Number")

            var prod = 1.0

            for (number in list.value.map { it as VoltValue.Number }) {
                prod *= number.value
            }

            VoltValue.Number(prod)
        }

        addFunction(".random") { _, _ -> VoltValue.Number(Random.nextDouble()) }
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