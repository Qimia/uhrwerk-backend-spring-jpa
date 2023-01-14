package io.qimia.uhrwerk.config.yaml

import com.fasterxml.jackson.annotation.JsonProperty

data class AWSSecret(
    override var name: String? = null,
    @JsonProperty("aws_secret_name")
    var awsSecretName: String? = null,
    @JsonProperty("aws_region")
    var awsRegion: String? = null
) : io.qimia.uhrwerk.config.yaml.Secret()