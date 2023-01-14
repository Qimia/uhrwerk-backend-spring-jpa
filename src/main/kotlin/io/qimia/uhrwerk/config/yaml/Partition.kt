package io.qimia.uhrwerk.config.yaml

data class Partition(
    var unit: PartitionUnit? = null,
    var size: Int? = null,
    var column: String? = null
)