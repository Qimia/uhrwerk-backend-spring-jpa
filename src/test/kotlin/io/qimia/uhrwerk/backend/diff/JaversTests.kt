package io.qimia.uhrwerk.backend.diff

import io.qimia.uhrwerk.backend.jpa.model.ConnectionModel
import io.qimia.uhrwerk.backend.jpa.model.ConnectionType
import org.javers.core.JaversBuilder
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class JaversTests {
    @Test
    fun connections() {
        val conn1 = ConnectionModel(name = "conn1", type = ConnectionType.JDBC, id = 1)
        val ts = LocalDateTime.now()
        val conn2 = ConnectionModel(
            name = "conn1",
            type = ConnectionType.JDBC,
            id = 1,
            createdTs = ts,
            updatedTs = ts
        )

        val javers = JaversBuilder.javers()
            .build()
        val diff = javers.compare(conn1, conn2)

        println(diff.prettyPrint())

        diff.changes.forEach { println(it) }

    }
}