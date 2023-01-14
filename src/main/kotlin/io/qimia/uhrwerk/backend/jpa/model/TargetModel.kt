package io.qimia.uhrwerk.backend.jpa.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "target",
    uniqueConstraints = [UniqueConstraint(
        name = "idx_trg_unique",
        columnNames = ["table_id", "connection_id", "format", "deactivated_epoch"]
    )]
)
class TargetModel(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "table_id",
        columnDefinition = "BIGINT",
        nullable = false
    )
    @JsonIgnore
    val table: TableModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "connection_id",
        columnDefinition = "BIGINT",
        nullable = false
    )
    val connection: ConnectionModel,

    @Column(
        name = "format",
        columnDefinition = "VARCHAR(64)",
        nullable = false
    )
    val format: String,

    id: Long? = null,
    description: String? = null,
    deactivatedTs: LocalDateTime? = null,
    createdTs: LocalDateTime? = null,
    updatedTs: LocalDateTime? = null
) : BaseModel(id, description, deactivatedTs, createdTs, updatedTs)