package io.qimia.uhrwerk.config.yaml

import com.fasterxml.jackson.annotation.JsonProperty

data class S3(
    override var name: String? = null,
    var path: String? = null,
    @JsonProperty("secret_id")
    var secretId: String? = null,
    @JsonProperty("secret_key")
    var secretKey: String? = null
) : io.qimia.uhrwerk.config.yaml.Connection()