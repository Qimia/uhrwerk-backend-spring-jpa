package io.qimia.uhrwerk.backend.jpa.model

import javax.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "connection",
    indexes = [Index(
        name = "idx_conn_name",
        columnList = "name", unique = false
    )],
    uniqueConstraints = [UniqueConstraint(
        name = "idx_conn_unique",
        columnNames = ["name", "deactivated_epoch"]
    )]
)
class ConnectionModel(
    @Column(
        name = "name",
        columnDefinition = "VARCHAR(256)",
        nullable = false
    )
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(
        name = "type",
        columnDefinition = "enum ('FS', 'JDBC', 'S3', 'GC', 'ABS')",
        nullable = false
    )
    val type: ConnectionType,

    @Column(
        name = "path",
        columnDefinition = "VARCHAR(512)"
    )
    var path: String? = null,

    @Column(
        name = "jdbc_url",
        columnDefinition = "VARCHAR(512)"
    )
    var jdbcUrl: String? = null,

    @Column(name = "jdbc_driver", columnDefinition = "VARCHAR(512)")
    var jdbcDriver: String? = null,

    @Column(name = "jdbc_user", columnDefinition = "VARCHAR(512)")
    var jdbcUser: String? = null,

    @Column(name = "jdbc_pass", columnDefinition = "VARCHAR(512)")
    var jdbcPass: String? = null,

    @Column(name = "aws_access_key_id", columnDefinition = "VARCHAR(512)")
    var awsAccessKeyID: String? = null,

    @Column(name = "aws_secret_access_key", columnDefinition = "VARCHAR(512)")
    var awsSecretAccessKey: String? = null,

    id: Long? = null,
    description: String? = null,
    deactivatedTs: LocalDateTime? = null,
    createdTs: LocalDateTime? = null,
    updatedTs: LocalDateTime? = null

) : BaseModel(id, description, deactivatedTs, createdTs, updatedTs) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ConnectionModel) return false
        if (!super.equals(other)) return false

        if (name != other.name) return false
        if (type != other.type) return false
        if (path != other.path) return false
        if (jdbcUrl != other.jdbcUrl) return false
        if (jdbcDriver != other.jdbcDriver) return false
        if (jdbcUser != other.jdbcUser) return false
        if (jdbcPass != other.jdbcPass) return false
        if (awsAccessKeyID != other.awsAccessKeyID) return false
        if (awsSecretAccessKey != other.awsSecretAccessKey) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (path?.hashCode() ?: 0)
        result = 31 * result + (jdbcUrl?.hashCode() ?: 0)
        result = 31 * result + (jdbcDriver?.hashCode() ?: 0)
        result = 31 * result + (jdbcUser?.hashCode() ?: 0)
        result = 31 * result + (jdbcPass?.hashCode() ?: 0)
        result = 31 * result + (awsAccessKeyID?.hashCode() ?: 0)
        result = 31 * result + (awsSecretAccessKey?.hashCode() ?: 0)
        return result
    }
}