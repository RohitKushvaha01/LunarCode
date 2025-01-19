package com.rk

typealias Argument = Any
data class Function(val name:String, val params:Array<String>,val unlimitedParams:Boolean = false,private val body:(args: Array<Argument>)->Any?) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Function

        if (name != other.name) return false
        if (!params.contentEquals(other.params)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + params.contentHashCode()
        return result
    }

    fun invoke(args: Array<Argument>):Any?{
        Stack.enterFunction(this,args)
        val result = body.invoke(args)
        Stack.exitFunction()
        return result
    }


}