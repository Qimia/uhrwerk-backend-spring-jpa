package io.qimia.uhrwerk.template

import org.antlr.runtime.CharStream
import org.antlr.runtime.Token
import org.junit.jupiter.api.Test
import org.stringtemplate.v4.ST
import org.stringtemplate.v4.compiler.STParser


class TemplateTest {
    val EOF_TYPE = CharStream.EOF // EOF token type

    data class Something(val something:String = "I am someething")

    fun aux(token: Token):String{
        val type =token.type
        val name = if (type == EOF_TYPE) "EOF" else STParser.tokenNames[type]
        return name

    }
    @Test
    fun detectParams() {
        val hello = ST("Hello, \$(something.something)\$", '$','$')
        val tSize = hello.impl.tokens.range()
        for ( i in 0..tSize-1){
            val token = hello.impl.tokens[i]
            println(token)
            println(aux(token))
        }
        hello.add("something", Something())
        println(hello.render())
    }
}