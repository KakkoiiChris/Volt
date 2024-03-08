package innovolt.volt.linker.libraries

import innovolt.volt.linker.Link
import innovolt.volt.linker.Linker
import innovolt.volt.parser.Expr
import innovolt.volt.runtime.VoltValue
import innovolt.volt.util.Source
import innovolt.volt.util.VoltError
import java.lang.Thread
import kotlin.concurrent.thread

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Thread.kt
 *
 * Created: Friday, November 25, 2022, 00:01:33
 *
 * @author Christian Bryce Alexander
 */
object Thread : Link {
    override val name = "thread"

    override val source get() = Source.readLocal("/thread.volt")

    override fun getLinks(linker: Linker) {
        linker.addClass("Thread") { runtime, instance ->
            val name = instance["name"] as? VoltValue.String ?: VoltError.invalidLinkClassArgument("Thread", "name", "String")

            val handler = instance["handler"] as? VoltValue.Function ?: VoltError.invalidLinkClassArgument("Thread", "handler", "Function")

            val invoke = Expr.Invoke(handler.value.location, Expr.Value(handler.value.location, handler), emptyList())

            val thread = thread(start = false, name = name.value) {
                runtime.visit(invoke)
            }

            VoltValue.ClassLink(thread)
        }

        linker.addFunction("Thread.start") { _, linkData ->
            val instance = linkData.instance!!.value

            val link = instance.link!!.value as Thread

            link.start()

            VoltValue.Unit
        }

        linker.addFunction("Thread.join") { _, linkData ->
            val instance = linkData.instance!!.value

            val link = instance.link!!.value as Thread

            link.join()

            VoltValue.Unit
        }
    }

    override fun wrapUp() {
    }
}