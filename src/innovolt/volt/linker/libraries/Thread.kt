package innovolt.volt.linker.libraries

import innovolt.volt.linker.Link
import innovolt.volt.linker.Linker
import innovolt.volt.parser.Expr
import innovolt.volt.runtime.Result
import innovolt.volt.util.Source
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
            val name = instance["name"] as? Result.String ?: TODO()

            val handler = instance["handler"] as? Result.Function ?: TODO()

            val invoke = Expr.Invoke(handler.value.location, Expr.Value(handler.value.location, handler.value), emptyList())

            val thread = thread(start = false, name = name.value) {
                runtime.visit(invoke)
            }

            Result.ClassLink(thread)
        }

        linker.addFunction("Thread.start") { runtime, linkData ->
            val link = (linkData.instance?.value?.link ?: TODO()).value as Thread

            link.start()

            Result.Unit
        }

        linker.addFunction("Thread.join") { runtime, linkData ->
            val link = (linkData.instance?.value?.link ?: TODO()).value as Thread

            link.join()

            Result.Unit
        }
    }

    override fun wrapUp() {
    }
}