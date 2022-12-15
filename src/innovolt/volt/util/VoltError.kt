package innovolt.volt.util

import innovolt.volt.lexer.Location
import innovolt.volt.lexer.Token
import innovolt.volt.parser.Expr
import innovolt.volt.parser.Stmt
import innovolt.volt.runtime.Callable
import innovolt.volt.runtime.Result
import innovolt.volt.runtime.VoltFunction
import kotlin.reflect.KClass

/**
 * Volt
 *
 * Copyright (C) 2022, KakkoiiChris
 *
 * File:    VoltError.kt
 *
 * Created: Friday, November 04, 2022, 00:38:10
 *
 * @author Christian Bryce Alexander
 */
class VoltError(stage: String, msg: String, loc: Location) : RuntimeException("Volt $stage Error: $msg!$loc") {
    companion object {
        private fun forLexer(message: String, location: Location): Nothing =
            throw VoltError("Lexer", message, location)
        
        fun invalidCharacter(invalid: Char, expected: Char, location: Location): Nothing =
            forLexer("Character '$invalid' is invalid; expected $expected", location)
        
        fun invalidString(invalid: String, expected: String, location: Location): Nothing =
            forLexer("String '$invalid' is invalid; expected $expected", location)
        
        fun invalidHexadecimal(invalid: Char, location: Location): Nothing =
            forLexer("Character '$invalid' is not a valid hexadecimal digit", location)
        
        fun invalidEscape(invalid: Char, location: Location): Nothing =
            forLexer("Character escape '\\$invalid' is invalid", location)
        
        fun unclosedString(location: Location): Nothing =
            forLexer("String is unclosed", location)
        
        fun illegalCharacter(illegal: Char, location: Location): Nothing =
            forLexer("Character '$illegal' is illegal", location)
        
        private fun forParser(message: String, location: Location): Nothing =
            throw VoltError("Parser", message, location)
        
        fun invalidTokenType(invalid: Token.Type, expected: Token.Type, location: Location): Nothing =
            forParser("Token type '$invalid' is invalid; expected $expected", location)
        
        fun invalidAssignmentTarget(location: Location): Nothing =
            forParser("Can only assign to names, indexers, and member accessors", location)
        
        fun invalidTerminal(invalid: Token.Type, location: Location): Nothing =
            forParser("Terminal expression beginning with token type '$invalid' is invalid; expected a value, a name, a '(', a '[', or a '{'", location)
        
        fun invalidLambdaParameter(location: Location): Nothing =
            forParser("Lambda parameter must be a valid name", location)
        
        fun <X : Token.Type> invalidTokenType(invalid: Token.Type, expected: KClass<X>, location: Location): Nothing =
            forParser("Token type '$invalid is invalid; expected $expected", location)
        
        private fun forRuntime(message: String, location: Location): Nothing =
            throw VoltError("Runtime", message, location)
        
        fun unhandledBreak(location: Location): Nothing =
            forRuntime("Unhandled break statement", location)
        
        fun unhandledContinue(location: Location): Nothing =
            forRuntime("Unhandled continue statement", location)
        
        fun invalidCondition(location: Location): Nothing =
            forRuntime("Condition does not result in a boolean value", location)
        
        fun invalidLeftOperand(operand: Result<*>, operator: Expr.Binary.Operator, location: Location): Nothing =
            forRuntime("Left operand '$operand' for '${operator.type} operator is invalid", location)
        
        fun invalidRightOperand(operand: Result<*>, operator: Expr.Binary.Operator, location: Location): Nothing =
            forRuntime("Right operand '$operand' for '${operator.type} operator is invalid", location)
        
        fun invalidOperand(operand: Result<*>, operator: Expr.Prefix.Operator, location: Location): Nothing =
            forRuntime("Operand '$operand' for '${operator.type} operator is invalid", location)
        
        fun nonAccessedValue(value: Result<*>, location: Location): Nothing =
            forRuntime("Value of type '${value.javaClass.simpleName}' cannot be accessed", location)
        
        fun invalidIndex(target: Result<*>, index: Result<*>, location: Location): Nothing =
            forRuntime("Value of type '${target.javaClass.simpleName}' cannot be indexed by a value of type '${index.javaClass.simpleName}'", location)
        
        fun nonIndexedValue(value: Result<*>, location: Location): Nothing =
            forRuntime("Value of type '${value.javaClass.simpleName}' cannot be indexed", location)
        
        fun nonInvokedValue(value: Result<*>, location: Location): Nothing =
            forRuntime("Value of type '${value.javaClass.simpleName}' cannot be invoked", location)
        
        fun argumentResolution(callable: Callable, location: Location): Nothing =
            forRuntime("Couldn't resolve callable '$callable'", location)
        
        fun argumentLinkResolution(function: VoltFunction, location: Location): Nothing =
            forRuntime("Couldn't resolve link for function '${function.name}'", location)
        
        fun nonIterableValue(value: Result<*>, location: Location): Nothing =
            forRuntime("Value of type '${value.javaClass.simpleName}' cannot be iterated over", location)
        
        fun noLink(function: Stmt.Function, location: Location): Nothing =
            forRuntime("No link exists for function '${function.name.value}'", location)
        
        private fun forLinker(message: String, location: Location): Nothing =
            throw VoltError("Runtime", message, location)
        
        fun invalidLinkArgument(path: String, name: String, expectedType: String): Nothing =
            forLinker("Function '$path' link argument '$name' expects a value of type '$expectedType'", Location.none)
        
        fun missingLink(name: String, location: Location): Nothing =
            forLexer("No link found for '$name' library", location)
    }
}