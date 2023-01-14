package io.qimia.uhrwerk.backend.jpa.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "source",
    uniqueConstraints = [UniqueConstraint(
        name = "idx_src_unique",
        columnNames = ["table_id", "connection_id", "path", "format", "deactivated_epoch"]
    )]
)
class SourceModel(

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
        name = "path",
        columnDefinition = "VARCHAR(512)",
        nullable = false
    )
    val path: String,

    @Column(
        name = "format",
        columnDefinition = "VARCHAR(64)",
        nullable = false
    )
    val format: String,

    @Enumerated(EnumType.STRING)
    @Column(
        name = "ingestion_mode",
        columnDefinition = "enum ('INTERVAL','DELTA', 'ALL')",
        nullable = false
    )
    val ingestionMode: IngestionMode = IngestionMode.ALL,

    @Column(
        name = "parallel_load",
        columnDefinition = "BOOLEAN",
        nullable = false
    )
    val parallelLoad: Boolean = false,

    @Column(
        name = "auto_load",
        columnDefinition = "BOOLEAN",
        nullable = false
    )
    val autoLoad: Boolean = false,

    @Enumerated(EnumType.STRING)
    @Column(
        name = "interval_temp_unit",
        columnDefinition = "enum ('DAYS','HOURS','MINUTES')"
    )
    var intervalTempUnit: PartitionUnit? = null,

    @Column(
        name = "interval_temp_size",
        columnDefinition = "INT"
    )
    var intervalTempSize: Int = 0,

    @Column(
        name = "interval_column",
        columnDefinition = "VARCHAR(256)"
    )
    var intervalColumn: String? = null,

    @Column(
        name = "delta_column",
        columnDefinition = "VARCHAR(256)"
    )
    var deltaColumn: String? = null,

    @Column(
        name = "select_query",
        columnDefinition = "TEXT"
    )
    var selectQuery: String? = null,

    @Column(
        name = "parallel_partition_query",
        columnDefinition = "TEXT"
    )
    var parallelPartitionQuery: String? = null,

    @Column(
        name = "parallel_partition_column",
        columnDefinition = "VARCHAR(256)"
    )
    var parallelPartitionColumn: String? = null,

    @Column(
        name = "parallel_partition_num",
        columnDefinition = "INT"
    )
    var parallelPartitionNum: Int? = 0,

    id: Long? = null,
    description: String? = null,
    deactivatedTs: LocalDateTime? = null,
    createdTs: LocalDateTime? = null,
    updatedTs: LocalDateTime? = null

) : BaseModel(id, description, deactivatedTs, createdTs, updatedTs)