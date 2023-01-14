package io.qimia.uhrwerk.config.yaml

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    Type(value = io.qimia.uhrwerk.config.yaml.JDBC::class, name = "jdbc"),
    Type(value = io.qimia.uhrwerk.config.yaml.S3::class, name = "s3"),
    Type(value = io.qimia.uhrwerk.config.yaml.File::class, name = "file")
)
open class Connection(
    open var name: String? = null
)