package io.qimia.uhrwerk.config.yaml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class Target(
    @JsonProperty("connection_name")
    var connectionName: String? = null,
    var format: String? = null
)