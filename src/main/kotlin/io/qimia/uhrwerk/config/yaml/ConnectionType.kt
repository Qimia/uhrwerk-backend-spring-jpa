package io.qimia.uhrwerk.config.yaml

import com.fasterxml.jackson.annotation.JsonProperty

enum class ConnectionType {
    @JsonProperty("jdbc")
    JDBC,

    @JsonProperty("s3")
    S3,

    @JsonProperty("file")
    FILE
}