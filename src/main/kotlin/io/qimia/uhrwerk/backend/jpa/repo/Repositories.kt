package io.qimia.uhrwerk.backend.jpa.repo

import io.qimia.uhrwerk.backend.jpa.model.*
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface TargetRepo : CrudRepository<TargetModel, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE TargetModel t SET t.deactivatedTs = :ts where t.id = :id")
    fun deactivate(@Param("id") id: Long, @Param("ts") ts: LocalDateTime)
}

interface SourceRepo : CrudRepository<SourceModel, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE SourceModel s SET s.deactivatedTs = :ts where s.id = :id")
    fun deactivate(@Param("id") id: Long, @Param("ts") ts: LocalDateTime)
}

interface DependencyRepo : CrudRepository<DependencyModel, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE DependencyModel d SET d.deactivatedTs = :ts where d.id = :id")
    fun deactivate(@Param("id") id: Long, @Param("ts") ts: LocalDateTime)
}

interface ConnectionRepo : CrudRepository<ConnectionModel, Long> {
    @Query(
        "SELECT c FROM ConnectionModel c " +
                "where c.name = :name " +
                "AND c.deactivatedTs IS NULL"
    )
    fun findActive(@Param("name") name: String): ConnectionModel


    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE ConnectionModel c SET c.deactivatedTs = :ts where c.id = :id")
    fun deactivate(@Param("id") id: Long, @Param("ts") ts: LocalDateTime)
}

interface SecretRepo : CrudRepository<SecretModel, Long> {
    @Query(
        "SELECT s FROM SecretModel s " +
                "where s.name = :name " +
                "AND s.deactivatedTs IS NULL"
    )
    fun findActive(@Param("name") name: String): ConnectionModel
}


interface TableRepo : CrudRepository<TableModel, Long> {
    @Query(
        "SELECT tbl FROM TableModel tbl " +
                "WHERE tbl.area = :area " +
                "AND tbl.vertical = :vertical " +
                "AND tbl.name = :name " +
                "AND tbl.version = :version " +
                "AND tbl.deactivatedTs IS NULL"
    )
    fun findTable(
        @Param("area") area: String,
        @Param("vertical") vertical: String,
        @Param("name") name: String,
        @Param("version") version: String
    ): TableModel
}