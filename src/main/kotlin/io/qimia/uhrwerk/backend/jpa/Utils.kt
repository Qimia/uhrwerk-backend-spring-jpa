package io.qimia.uhrwerk.backend.jpa

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

object Utils {
    private val jackson: ObjectMapper = ObjectMapper().registerModule(JavaTimeModule())

    fun prettyJson(obj: Any): String = jackson.writerWithDefaultPrettyPrinter().writeValueAsString(obj)

}