package io.qimia.uhrwerk.config.yaml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
data class Dependency(
    @JsonProperty("ref")
    var reference: Reference? = null,
    var view: String? = null,
    var format: String? = null,
)
