import org.testcontainers.containers.BindMode
import org.testcontainers.containers.MySQLContainer

object TestUtils {
    fun mysqlContainer(): MySQLContainer<*> = MySQLContainer("mysql:8.0.31")
        .withClasspathResourceMapping(
            "metastore_ddl/create_metastore_db_mysql.sql",
            "/docker-entrypoint-initdb.d/create_metastore_db_mysql.sql",
            BindMode.READ_ONLY
        )
        .withClasspathResourceMapping(
            "metastore_ddl/metastore_ddl_mysql.sql",
            "/docker-entrypoint-initdb.d/metastore_ddl_mysql.sql",
            BindMode.READ_ONLY
        )
        .withPassword("61ePGqq20u9TZjbNhf0")
        .withDatabaseName("UHRWERK_METASTORE_JPA")
        .withCommand("--default-authentication-plugin=mysql_native_password --innodb_buffer_pool_size=1G --innodb_log_file_size=256M --innodb_flush_log_at_trx_commit=1 --innodb_flush_method=O_DIRECT --explicit_defaults_for_timestamp=1 --lower_case_table_names=1")


    fun fileToString(fileName: String) =
        this::class.java.getResourceAsStream(fileName)
            ?.bufferedReader()?.readText()

    fun filePath(fileName: String) =
        this::class.java.getResource(fileName)?.path
}
