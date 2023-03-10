package io.qimia.uhrwerk.config.yaml

import com.fasterxml.jackson.annotation.JsonProperty

enum class PartitionUnit {
    @JsonProperty("weeks")
    WEEKS,

    @JsonProperty("days")
    DAYS,

    @JsonProperty("hours")
    HOURS,

    @JsonProperty("minutes")
    MINUTES
}