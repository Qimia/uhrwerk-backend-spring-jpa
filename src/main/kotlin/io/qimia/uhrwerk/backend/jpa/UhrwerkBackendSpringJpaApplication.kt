package io.qimia.uhrwerk.backend.jpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class UhrwerkBackendSpringJpaApplication

fun main(args: Array<String>) {
	runApplication<UhrwerkBackendSpringJpaApplication>(*args)
}
