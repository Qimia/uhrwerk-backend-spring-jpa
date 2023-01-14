package io.qimia.uhrwerk.backend.jpa.model

import javax.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "dependency",
    uniqueConstraints = [UniqueConstraint(
        name = "idx_dpn_unique",
        columnNames = ["table_id", "dependency_target_id", "dependency_table_id", "deactivated_epoch"]
    )]
)
class DependencyModel(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "table_id",
        columnDefinition = "BIGINT",
        nullable = false
    )
    var table: TableModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "dependency_target_id",
        columnDefinition = "BIGINT",
        nullable = false
    )
    var dependencyTarget: TargetModel,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "dependency_table_id",
        columnDefinition = "BIGINT",
        nullable = false
    )
    var dependencyTable: TableModel,

    @Column(
        name = "view_name",
        columnDefinition = "VARCHAR(128)"
    )
    var viewName: String? = null,

    id: Long? = null,
    description: String? = null,
    deactivatedTs: LocalDateTime? = null,
    createdTs: LocalDateTime? = null,
    updatedTs: LocalDateTime? = null
) : BaseModel(id, description, deactivatedTs, createdTs, updatedTs)