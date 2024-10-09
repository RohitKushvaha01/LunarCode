package com.rk

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.File

fun main(args:Array<String>) {


    val file = File(args[0],"example.lc")

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
    // Use the generated lexer and parser
    val lexer = LunarCodeLexer(CharStreams.fromString(code))  // Lexer breaks input into tokens
    val tokens = CommonTokenStream(lexer)  // Feed tokens to the parser
    val parser = LunarCodeParser(tokens)


}





