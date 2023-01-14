package io.qimia.uhrwerk.config.yaml

import com.fasterxml.jackson.annotation.JsonProperty

data class JDBC(
    override var name: String? = null,
    @JsonProperty("jdbc_url")
    var jdbcUrl: String? = null,
    @JsonProperty("jdbc_driver")
    var jdbcDriver: String? = null,
    var user: String? = null,
    var password: String? = null
) : io.qimia.uhrwerk.config.yaml.Connection()