package com.rk

class Interpreter : LunarCodeBaseListener() {
    private val memory: MutableMap<String, Any> = HashMap()
    private val variableTypes: MutableMap<String, String> = HashMap() // To track the type of each variable

    // Handle assignment of variables, enforcing type consistency
    override fun exitAssignment(ctx: LunarCodeParser.AssignmentContext) {
        val variable: String = ctx.IDENTIFIER()?.text ?: throw RuntimeException("Missing identifier")

        // Evaluate the expression on the right-hand side
        val value: Any = evaluateExpression(ctx.expression())

        // Check if the variable already exists and if its type matches the new value's type
        if (variable in variableTypes) {
            val existingType = variableTypes[variable]
            val newType = when (value) {
                is Int -> "Int"
                is String -> "String"
                else -> throw RuntimeException("Unsupported value type")
            }

            if (existingType != newType) {
                throw RuntimeException("Type mismatch: Cannot assign '$newType' to variable '$variable' (previously assigned as '$existingType')")
            }
        }

        // Store the variable value and type in memory
        memory[variable] = value
        variableTypes[variable] = when (value) {
            is Int -> "Int"
            is String -> "String"
            else -> throw RuntimeException("Unsupported value type")
        }
    }

    // Print the value of a variable
    override fun exitPrintStmt(ctx: LunarCodeParser.PrintStmtContext) {
        val variable: String = ctx.printStatement().id?.text ?: throw RuntimeException("Missing identifier")
        val value = memory[variable]
        if (value != null) {
            println(value)
        } else {
            throw RuntimeException("Variable '$variable' not found")
        }
    }

    private fun evaluateExpression(expr: LunarCodeParser.ExpressionContext): Any {
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

    private fun evaluateTerm(term: LunarCodeParser.TermContext): Any {
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

    private fun evaluateFactor(factor: LunarCodeParser.FactorContext): Any {
        return when {
            factor.INT() != null -> factor.INT().text.toInt()
            factor.IDENTIFIER() != null -> {
                val variable = factor.IDENTIFIER().text
                val value = memory[variable] ?: throw RuntimeException("Variable '$variable' not found")
                value
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
