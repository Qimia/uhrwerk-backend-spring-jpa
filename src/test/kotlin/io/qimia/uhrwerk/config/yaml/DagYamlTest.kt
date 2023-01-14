package io.qimia.uhrwerk.config.yaml

import TestUtils.fileToString
import io.qimia.uhrwerk.config.yaml.YamlUtils.objectMapper
import org.junit.jupiter.api.Test


class DagYamlTest {


    @Test
    fun read() {
        val tree = MAPPER.readTree(YAML)

        tree.isObject

        val token = tree.asToken()

        println(token)

    }

    companion object {
        private const val CONNECTION_YAML = "config/dag-config-new.yml"
        private val YAML = fileToString(CONNECTION_YAML)!!
        private val MAPPER = objectMapper()

    }
}