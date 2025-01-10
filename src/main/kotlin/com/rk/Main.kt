package com.rk

import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.Recognizer
import java.io.File

fun main(args:Array<String>) {
    val file = File("example.lc")

    if (file.exists().not()){
        println("File ${file.absolutePath} not path")
        return
    }else if (file.isFile.not()){
        println("File ${file.absolutePath} is a directory")
        return
    }

    try {
        compile(file.readText())
    }catch (e:Exception){
        e.printStackTrace()
    }


}
fun compile(code:String) {
    val lexer = LunarCodeLexer(CharStreams.fromString(code))
    val tokens = CommonTokenStream(lexer)
    val parser = LunarCodeParser(tokens)

    parser.removeErrorListeners()
    parser.addErrorListener(object : BaseErrorListener() {
        override fun syntaxError(
            recognizer: Recognizer<*, *>?,
            offendingSymbol: Any?,
            line: Int,
            charPositionInLine: Int,
            msg: String?,
            e: RecognitionException?
        ) {
            throw IllegalArgumentException("Syntax Error at line $line:$charPositionInLine - $msg")
        }
    })




}






