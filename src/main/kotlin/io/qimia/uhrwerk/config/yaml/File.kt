package io.qimia.uhrwerk.config.yaml

data class File(
    override var name: String? =null,
    var path: String? = null
) : io.qimia.uhrwerk.config.yaml.Connection()