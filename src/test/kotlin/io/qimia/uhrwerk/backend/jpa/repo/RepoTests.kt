package io.qimia.uhrwerk.backend.jpa.repo

import io.qimia.uhrwerk.backend.jpa.Utils
import io.qimia.uhrwerk.backend.jpa.model.ConnectionModel
import io.qimia.uhrwerk.backend.jpa.model.ConnectionType
import org.junit.jupiter.api.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDateTime
import javax.persistence.EntityManager

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(initializers = [RepoTests.DataSourceInitializer::class])
internal class RepoTests @Autowired constructor(
    val entityManager: EntityManager,
    val connRepo: ConnectionRepo,
    val tableRepo: TableRepo
) {

    @AfterEach
    fun afterEach() {
    }

    @BeforeEach
    fun beforeEach() {
    }

    @Test
    fun connection() {
        val connName = "JPA-Connection-Test"
        val connection = ConnectionModel(name = connName, type = ConnectionType.JDBC)
        connRepo.save(connection)
        entityManager.refresh(connection)
        println(Utils.prettyJson(connection))
        val connection2 = connRepo.findActive(connName)
        println(Utils.prettyJson(connection2))
        connRepo.deactivate(connection.id!!, LocalDateTime.now())
        val deactivated = connRepo.findById(connection.id!!).get()
        println(Utils.prettyJson(deactivated))

    }

    class DataSourceInitializer :
        ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                "spring.datasource.url=" + MY_SQL_DB.jdbcUrl,
                "spring.datasource.username=" + MY_SQL_DB.username,
                "spring.datasource.password=" + MY_SQL_DB.password
            )
        }
    }


    companion object {
        private val LOGGER = LoggerFactory.getLogger(RepoTests::class.java)

        @Container
        var MY_SQL_DB: MySQLContainer<*> = TestUtils.mysqlContainer()


        @BeforeAll
        @JvmStatic
        fun setUp() {
            val logConsumer = Slf4jLogConsumer(LOGGER)
            MY_SQL_DB.followOutput(logConsumer)
            val props = mapOf(
                "javax.persistence.jdbc.url" to MY_SQL_DB.jdbcUrl!!,
                "javax.persistence.jdbc.user" to MY_SQL_DB.username!!,
                "javax.persistence.jdbc.password" to MY_SQL_DB.password!!
            )
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {

        }
    }

}