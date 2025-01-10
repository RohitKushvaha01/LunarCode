package com.rk

class Interpreter : LunarCodeBaseListener() {
    private val functions = mutableMapOf<String, LunarCodeParser.BlockContext>()

    override fun enterFunctionDeclaration(ctx: LunarCodeParser.FunctionDeclarationContext) {
        val funcName = ctx.ID().text
        functions[funcName] = ctx.block()
        Logger.log("Function declared: $funcName")
    }

    override fun enterExpressionStatement(ctx: LunarCodeParser.ExpressionStatementContext) {
        if (ctx.expression().functionCall() != null) {
            val funcCall = ctx.expression().functionCall()
            val funcName = funcCall.ID().text
            val args = funcCall.argumentList()
            if (functions.contains(funcName)){

            }else if (invokeInternal(funcName,args).not()){
                Logger.FatalError("Tried to invoke undefined function $funcName")
            }
        }
    }

    private fun invokeInternal(name:String,args: LunarCodeParser. ArgumentListContext):Boolean{
        return when(name){
            "print" -> {
                println(args.expression().joinToString { it.text.removeSurrounding("\"") })
                true
            }
            else -> false
        }
    }



    data class LunarFunction(val parameters: List<String>, val block: LunarCodeParser.BlockContext)
}