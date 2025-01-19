package com.rk

import java.util.Stack

object Stack {
    private val functionStack = Stack<Function>()
    private val variableStack = Stack<MutableMap<String, Any>>()

    init {
        variableStack.push(mutableMapOf())
    }

    fun enterFunction(function: Function, args: Array<Argument>) {
        functionStack.push(function)
        variableStack.push(mutableMapOf())
        if (function.unlimitedParams.not()) {
            for (i in args.indices) {
                val name = function.params[i]
                val value = args[i]
                defineVariable(name, value)
            }
        }

    }

    fun exitFunction(): Function {
        variableStack.pop()
        return functionStack.pop()
    }

    fun defineVariable(name: String, value: Any) {
        if (variableStack.isEmpty()) {
            throw RuntimeException("No active scope to define variable '$name'")
        }
        variableStack.peek()[name] = value
    }


    fun getVariable(name: String): Any {
        for (scope in variableStack.reversed()) {
            if (scope.containsKey(name)) {
                return scope[name] ?: throw RuntimeException("Variable '$name' has no value")
            }
        }
        throw RuntimeException("Variable '$name' not found in any active scope")
    }


    fun peek(): Function? {
        return functionStack.peekOrNull()
    }

    fun isEmpty(): Boolean = functionStack.isEmpty()

    fun printStackTrace() {
        println("Current function stack:")
        if (functionStack.isEmpty()) {
            println("<empty>")
        } else {
            functionStack.forEach { function -> println(" at ${function.name}(${function.params.joinToString { it }})") }
        }
    }

    fun printVariableStack(){
        println("Current variable stack:")
        if (variableStack.isEmpty()) {
            println("<empty>")
        } else {
            variableStack.forEachIndexed { index, scope ->
                println("Scope $index: ${scope.entries.joinToString { "${it.key}=${it.value}" }}")
            }
        }
    }

    private fun <E> Stack<E>.peekOrNull(): E? = if (this.isEmpty()) null else this.peek()
}
