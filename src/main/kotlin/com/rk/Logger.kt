package com.rk

import kotlin.system.exitProcess

object Logger {
    private const val RESET = "\u001B[0m"
    private const val RED = "\u001B[31m"
    private const val GREEN = "\u001B[32m"
    private const val YELLOW = "\u001B[33m"
    private const val BLUE = "\u001B[34m"
    private const val CYAN = "\u001B[36m"

    fun log(s: String) {
        println("$GREEN$s$RESET")
    }

    fun err(e: String) {
        println("$RED$e$RESET")
    }

    fun FatalError(e:String){
        err(e)
        exitProcess(1)
    }
}
