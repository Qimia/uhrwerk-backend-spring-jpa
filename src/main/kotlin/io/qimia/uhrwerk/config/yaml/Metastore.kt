package io.qimia.uhrwerk.config.yaml

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

@JsonRootName("meta_store")
data class Metastore(
    @JsonProperty("env_name")
    var envName: String? = null,
    @JsonProperty("jdbc_url")
    var jdbcUrl: String? = null,
    @JsonProperty("jdbc_driver")
    var jdbcDriver: String? = null,
    var user: String? = null,
    var password: String? = null,
)