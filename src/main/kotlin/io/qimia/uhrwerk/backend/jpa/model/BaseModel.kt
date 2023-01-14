package io.qimia.uhrwerk.backend.jpa.model

import org.javers.core.metamodel.annotation.DiffIgnore
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
open class BaseModel(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    var id: Long? = null,

    @Column(
        name = "description",
        columnDefinition = "VARCHAR(512)"
    )
    var description: String? = null,

    @Column(
        name = "created_ts",
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP",
        updatable = false,
        insertable = false
    )
    @DiffIgnore
    var createdTs: LocalDateTime? = null,

    @Column(
        name = "updated_ts",
        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP",
        updatable = true,
        insertable = false
    )
    @DiffIgnore
    var updatedTs: LocalDateTime? = null,
    @Column(
        name = "deactivated_ts",
        columnDefinition = "TIMESTAMP",
        updatable = false
    )
    var deactivatedTs: LocalDateTime? = null,

    ) {
    @Column(
        name = "deactivated_epoch",
        columnDefinition = "BIGINT AS (IFNULL((TIMESTAMPDIFF(second, '1970-01-01', deactivated_ts)),0))",
        nullable = false,
        insertable = false,
        updatable = false
    )
    var deactivatedEpoch: Long? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BaseModel) return false

        if (id != null && other.id != null && id != other.id) return false
        if (description != other.description) return false
        if (deactivatedTs != other.deactivatedTs) return false
        if (createdTs != other.createdTs) return false
        if (updatedTs != other.updatedTs) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (deactivatedTs?.hashCode() ?: 0)
        result = 31 * result + (createdTs?.hashCode() ?: 0)
        result = 31 * result + (updatedTs?.hashCode() ?: 0)
        return result
    }
}
