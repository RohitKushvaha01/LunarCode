package com.rk

import com.rk.LunarCodeParser.*
import kotlin.system.exitProcess

class Interpreter : LunarCodeBaseListener() {
    private val functions = mutableMapOf<String,Function>()

    init {
        functions["println"] = Function(
            name = "println",
            params = arrayOf(),
            unlimitedParams = true,
            body = { args ->
                if (args.size == 1){
                    println(args[0])
                }else{
                    println(args)
                }
            }
        )

        functions["exit"] = Function(
            name = "println",
            params = arrayOf("exitCode"),
            body = { args ->
                val exitCode = args.getOrNull(0) ?: -1
                if (exitCode is Int){
                    exitProcess(exitCode)
                }else{
                    exitProcess(-1)
                }
            }
        )

        functions["printStackTrace"] = Function(
            name = "printStackTrace",
            params = arrayOf(),
            body = {
                Stack.printStackTrace()
            }
        )

        functions["printVariableTrace"] = Function(
            name = "printVariableTrace",
            params = arrayOf(),
            body = {
                Stack.printVariableStack()
            }
        )
    }


    override fun exitAssignment(ctx: AssignmentContext) {
        if (isAFunction){
            return
        }
        val variable: String = ctx.IDENTIFIER()?.text ?: throw RuntimeException("Missing identifier")
        val value: Any = evaluateExpression(ctx.expression())

        Stack.defineVariable(variable,value)
    }


    private fun evaluateExpression(expr: ExpressionContext): Any {
        // Handle arithmetic operations (addition, subtraction, multiplication, division)
        val left = evaluateTerm(expr.term(0))
        var result = left

        for (i in 1 until expr.term().size) {
            val operator = expr.getChild(2 * i - 1).text // '+' or '-'
            val right = evaluateTerm(expr.term(i))

            if (result is Int){
                result = when (operator) {
                    "+" -> result + (right as Int)
                    "-" -> result - (right as Int)
                    else -> throw RuntimeException("Unknown operator: $operator")
                }
            }else if (result is String){
                result = when (operator) {
                    "+" -> result + (right as String)
                    else -> throw RuntimeException("Unknown operator: $operator")
                }
            }

        }
        return result
    }

    var isAFunction = false
    override fun enterFunctionDecl(ctx: FunctionDeclContext?) {
        if (isAFunction){
            throw RuntimeException("Cannot define functions in this scope")
        }
        isAFunction = true
        val name = ctx!!.IDENTIFIER().text!!
        val params = ctx.parameterList()?.IDENTIFIER()
        val body = ctx.statement()

        if (functions.containsKey(name)){
            throw RuntimeException("Function with name $name is already defined")
        }

        functions[name] = Function(
            name = name,
            params = params?.map { it.text }?.toTypedArray() ?: emptyArray(),
            body = {
                eval(body)
            }
        )



    }

    fun eval(body:List<StatementContext>){
        body.forEach { statement ->
            when(statement){
                is FunctionCallStmtContext -> {
                    exitFunctionCall(statement.functionCall())
                }

                is FunctionDeclStmtContext -> {
                    enterFunctionDecl(statement.functionDecl())
                }
                is AssignmentStmtContext -> {
                    exitAssignment(statement.assignment())
                }
            }
        }
    }

    override fun exitFunctionDecl(ctx: FunctionDeclContext) {
        //now resume execution of statements
        isAFunction = false
    }

    override fun exitFunctionCall(ctx: FunctionCallContext?) {
        if (isAFunction){
            return
        }
        val name = ctx?.IDENTIFIER()?.text!!
        val argList = ctx?.argumentList()
        val args = mutableListOf<Argument>()
        if(argList?.isEmpty?.not() == true){
            val rawArgs = ctx.argumentList().expression()
            if (rawArgs.isNotEmpty()){
                for (i in 0 until rawArgs.size){
                    val arg = evaluateExpression(rawArgs[i])
                    args.add(arg)
                }
            }
        }

       val function = functions[name] ?: throw RuntimeException("Undefined function $name")
       function.invoke(args = args.toTypedArray())
    }

    private fun evaluateTerm(term: TermContext): Any {
        // Handle multiplication and division
        val left = evaluateFactor(term.factor(0))
        var result = left

        for (i in 1 until term.factor().size) {
            val operator = term.getChild(2 * i - 1).text // '*' or '/'
            val right = evaluateFactor(term.factor(i))

            result = when (operator) {
                "*" -> (result as Int) * (right as Int)
                "/" -> (result as Int) / (right as Int)
                else -> throw RuntimeException("Unknown operator: $operator")
            }
        }
        return result
    }

    private fun evaluateFactor(factor: FactorContext): Any {
        return when {
            factor.INT() != null -> factor.INT().text.toInt()
            factor.IDENTIFIER() != null -> {
                val variable = factor.IDENTIFIER().text
                return Stack.getVariable(variable)
            }
            factor.STRING() != null -> {
                val rawString = factor.STRING().text
                processString(rawString)
            }
            factor.expression() != null -> {
                // Handle parentheses, recursive evaluation
                evaluateExpression(factor.expression())
            }
            else -> throw RuntimeException("Invalid factor")
        }
    }




    private fun processString(rawString: String): String {
        val withoutQuotes = rawString.substring(1, rawString.length - 1)
        return withoutQuotes.replace("""\\n""", "\n")
            .replace("""\\t""", "\t")
            .replace("""\\r""", "\r")
            .replace("""\\"""", "\"")
            .replace("""\\\\""", "\\")
    }
}
