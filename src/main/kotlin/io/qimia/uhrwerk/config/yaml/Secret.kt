package io.qimia.uhrwerk.config.yaml

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = io.qimia.uhrwerk.config.yaml.AWSSecret::class, name = "aws")
)
open class Secret(
    open var name: String? = null
)