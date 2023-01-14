package io.qimia.uhrwerk.backend.jpa.model

import javax.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "secret_",
    indexes = [Index(
        name = "idx_scr_name",
        columnList = "name", unique = false
    )],
    uniqueConstraints = [UniqueConstraint(
        name = "idx_scr_unique",
        columnNames = ["name", "deactivated_epoch"]
    )]
)
class SecretModel(
    @Column(
        name = "name",
        columnDefinition = "VARCHAR(128)",
        nullable = false
    )
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(
        name = "type",
        columnDefinition = "enum ('AWS','AZURE','GCP')",
        nullable = false
    )
    val type: SecretType,

    @Column(
        name = "aws_secret_name",
        columnDefinition = "VARCHAR(512)"
    )
    var awsSecretName: String? = null,

    @Column(
        name = "aws_region",
        columnDefinition = "VARCHAR(32)"
    )
    var awsRegion: String? = null,

    id: Long? = null,
    description: String? = null,
    deactivatedTs: LocalDateTime? = null,
    createdTs: LocalDateTime? = null,
    updatedTs: LocalDateTime? = null
) : BaseModel(id, description, deactivatedTs, createdTs, updatedTs) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SecretModel) return false
        if (!super.equals(other)) return false

        if (name != other.name) return false
        if (type != other.type) return false
        if (awsSecretName != other.awsSecretName) return false
        if (awsRegion != other.awsRegion) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + (awsSecretName?.hashCode() ?: 0)
        result = 31 * result + (awsRegion?.hashCode() ?: 0)
        return result
    }
}