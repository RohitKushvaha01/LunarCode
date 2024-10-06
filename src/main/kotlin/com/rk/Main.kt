package com.rk

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

fun main() {
    // Input expression
    val input = "5 + 3 - 2"

    // Use the generated lexer and parser
    val lexer = ExprLexer(CharStreams.fromString(input))  // Lexer breaks input into tokens
    val tokens = CommonTokenStream(lexer)  // Feed tokens to the parser
    val parser = ExprParser(tokens)

    // Parse the expression
    val tree = parser.prog()  // Start parsing from 'prog' rule

    // Create a visitor to evaluate the expression
    val visitor = EvalVisitor()
    val result = visitor.visit(tree)

    // Print the result
    println("Result: $result")
}

class EvalVisitor : ExprBaseVisitor<Int>() {
    override fun visitAddSubExpr(ctx: ExprParser.AddSubExprContext): Int {
        val left = visit(ctx.expr(0))  // Visit the left expression
        val right = visit(ctx.expr(1)) // Visit the right expression

        return when (ctx.getChild(1).text) {  // Get the operator
            "+" -> left + right
            "-" -> left - right
            else -> 0
        }
    }

    override fun visitIntExpr(ctx: ExprParser.IntExprContext): Int {
        return ctx.INT().text.toInt()  // Convert INT to Integer
    }
}
