package innovolt.volt.parser

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    Program.kt
 *
 * Created: Monday, November 07, 2022, 23:50:29
 *
 * @author Christian Bryce Alexander
 */
class Program(private val stmts: List<Stmt>) : Iterator<Stmt> by stmts.iterator()