package io.qimia.uhrwerk.config.yaml

data class ParallelLoad(
    var query: String? = null,
    var column: String? = null,
    var num: Int? = null
)