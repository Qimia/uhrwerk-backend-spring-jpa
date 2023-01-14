package io.qimia.uhrwerk.config.yaml

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class Source(
    @JsonProperty("connection_name")
    var connectionName: String? = null,
    var path: String? = null,
    var format: String? = null,
    var version: String? = null,
    var partition: Partition? = null,
    @JsonProperty("parallel_load")
    var parallelLoad: ParallelLoad? = null,
    var select: Select? = null,
    @JsonProperty("auto_load")
    var autoLoad: Boolean = false,
    @JsonProperty("ingestion_mode")
    var ingestionMode: IngestionMode = IngestionMode.ALL
)