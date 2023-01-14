package io.qimia.uhrwerk.backend.jpa.model

import javax.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "table_",
    uniqueConstraints = [UniqueConstraint(
        name = "idx_tbl_unique",
        columnNames = ["area", "vertical", "name", "version", "deactivated_epoch"]
    )]
)
class TableModel(
    @Column(
        name = "area",
        columnDefinition = "VARCHAR(128)",
        nullable = false
    )
    val area: String,

    @Column(
        name = "vertical",
        columnDefinition = "VARCHAR(128)",
        nullable = false
    )
    val vertical: String,

    @Column(
        name = "name",
        columnDefinition = "VARCHAR(128)",
        nullable = false
    )
    val name: String,

    @Column(
        name = "version",
        columnDefinition = "VARCHAR(128)",
        nullable = false
    )
    val version: String,

    @Column(
        name = "class_name",
        columnDefinition = "VARCHAR(512)"
    )
    var className: String? = null,

    @Column(
        name = "transform_sql_query",
        columnDefinition = "TEXT"
    )
    var transformSqlQuery: String? = null,

    @Column(
        name = "parallelism",
        columnDefinition = "INT"
    )
    var parallelism: Int? = null,

    @Column(
        name = "max_bulk_size",
        columnDefinition = "INT"
    )
    var maxBulkSize: Int? = null,

    @Enumerated(EnumType.STRING)
    @Column(
        name = "partition_unit",
        columnDefinition = "enum ('DAYS','HOURS','MINUTES')"
    )
    var partitionUnit: PartitionUnit? = null,


    @Column(
        name = "partition_size",
        columnDefinition = "INT"
    )
    var partitionSize: Int? = null,
    @Column(
        name = "partitioned",
        columnDefinition = "BOOLEAN",
        nullable = false
    )
    var partitioned: Boolean = false,

    @Column(nullable = true)
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "table"
    )
    var sources: List<SourceModel>? = null,

    @Column(nullable = true)
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "table"
    )
    var dependencies: List<DependencyModel>? = null,

    @Column(nullable = true)
    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "table"
    )
    var targets: List<TargetModel>? = null,

    id: Long? = null,
    description: String? = null,
    deactivatedTs: LocalDateTime? = null,
    createdTs: LocalDateTime? = null,
    updatedTs: LocalDateTime? = null
) : BaseModel(id, description, deactivatedTs, createdTs, updatedTs)