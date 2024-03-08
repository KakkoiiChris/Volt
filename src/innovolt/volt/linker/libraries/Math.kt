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

            VoltValue.Number(sin(n.value))
        }

        addFunction(".cos", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("cos", "n", "Number")

            VoltValue.Number(cos(n.value))
        }

        addFunction(".tan", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("tan", "n", "Number")

            VoltValue.Number(tan(n.value))
        }

        addFunction(".asin", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("asin", "n", "Number")

            VoltValue.Number(asin(n.value))
        }

        addFunction(".acos", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("acos", "n", "Number")

            VoltValue.Number(acos(n.value))
        }

        addFunction(".atan", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("atan", "n", "Number")

            VoltValue.Number(atan(n.value))
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

            VoltValue.Number(sinh(n.value))
        }

        addFunction(".cosh", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("cosh", "n", "Number")

            VoltValue.Number(cosh(n.value))
        }

        addFunction(".tanh", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("tanh", "n", "Number")

            VoltValue.Number(tanh(n.value))
        }

        addFunction(".asinh", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("asinh", "n", "Number")

            VoltValue.Number(asinh(n.value))
        }

        addFunction(".acosh", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("acosh", "n", "Number")

            VoltValue.Number(acosh(n.value))
        }

        addFunction(".atanh", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("atanh", "n", "Number")

            VoltValue.Number(atanh(n.value))
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

            VoltValue.Number(sqrt(n.value))
        }

        addFunction(".cbrt", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("cbrt", "n", "Number")

            VoltValue.Number(cbrt(n.value))
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

            VoltValue.Number(exp(n.value))
        }

        addFunction(".expm1", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("expm1", "n", "Number")

            VoltValue.Number(expm1(n.value))
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

            VoltValue.Number(ln(n.value))
        }

        addFunction(".log10", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("log10", "n", "Number")

            VoltValue.Number(log10(n.value))
        }

        addFunction(".log2", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("log2", "n", "Number")

            VoltValue.Number(log2(n.value))
        }

        addFunction(".ln1p", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("ln1p", "n", "Number")

            VoltValue.Number(ln1p(n.value))
        }

        addFunction(".ceil", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("ceil", "n", "Number")

            VoltValue.Number(ceil(n.value))
        }

        addFunction(".floor", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("floor", "n", "Number")

            VoltValue.Number(floor(n.value))
        }

        addFunction(".truncate", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("truncate", "n", "Number")

            VoltValue.Number(truncate(n.value))
        }

        addFunction(".round", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("round", "n", "Number")

            VoltValue.Number(round(n.value))
        }

        addFunction(".abs", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("abs", "n", "Number")

            VoltValue.Number(abs(n.value))
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

            VoltValue.Number(sign(n.value))
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

            VoltValue.Number(n.value.ulp)
        }

        addFunction(".nextUp", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("nextUp", "n", "Number")

            VoltValue.Number(n.value.nextUp())
        }

        addFunction(".nextDown", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("nextDown", "n", "Number")

            VoltValue.Number(n.value.nextDown())
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

            VoltValue.Boolean(n.value.isNaN())
        }

        addFunction(".isFinite", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("isFinite", "n", "Number")

            VoltValue.Boolean(n.value.isFinite())
        }

        addFunction(".fact", 1) { _, data ->
            val (n) = data.args

            n as? VoltValue.Number ?: VoltError.invalidLinkFunctionArgument("fact", "n", "Number")

            VoltValue.Number(fact(n.value))
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