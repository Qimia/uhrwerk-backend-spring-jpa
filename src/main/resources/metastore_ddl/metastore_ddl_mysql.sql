USE UHRWERK_METASTORE_JPA;
CREATE TABLE IF NOT EXISTS CONNECTION
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                  VARCHAR(256)                           NOT NULL,
    type                  enum ('FS', 'JDBC', 'S3', 'GC', 'ABS') NOT NULL,
    path                  VARCHAR(512)                           NULL,
    jdbc_url              VARCHAR(512)                           NULL,
    jdbc_driver           VARCHAR(512)                           NULL,
    jdbc_user             VARCHAR(512)                           NULL,
    jdbc_pass             VARCHAR(512)                           NULL,
    aws_access_key_id     VARCHAR(512)                           NULL,
    aws_secret_access_key VARCHAR(512)                           NULL,
    deactivated_ts        TIMESTAMP                              NULL,
    deactivated_epoch   BIGINT AS (IFNULL((TIMESTAMPDIFF(second, '1970-01-01', deactivated_ts)),
                                          0))              NOT NULL,
    created_ts            TIMESTAMP DEFAULT CURRENT_TIMESTAMP    NULL,
    updated_ts            TIMESTAMP DEFAULT CURRENT_TIMESTAMP    NULL ON update CURRENT_TIMESTAMP,
    description           VARCHAR(512)                           NULL,
    INDEX idx_conn_name (name),
    CONSTRAINT idx_conn_unique UNIQUE (name, deactivated_epoch)
);

CREATE TABLE IF NOT EXISTS SECRET_
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(128)                        NOT NULL,
    type            enum ('AWS','AZURE','GCP')          NOT NULL,
    aws_secret_name VARCHAR(512)                        NULL,
    aws_region      VARCHAR(32)                         NULL,
    deactivated_ts  TIMESTAMP                           NULL,
    deactivated_epoch   BIGINT AS (IFNULL((TIMESTAMPDIFF(second, '1970-01-01', deactivated_ts)),
                                          0))              NOT NULL,
    created_ts      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    updated_ts      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON update CURRENT_TIMESTAMP,
    description     VARCHAR(512)                        NULL,
    INDEX idx_scr_name (name),
    CONSTRAINT idx_scr_unique UNIQUE (name, deactivated_epoch)
);

CREATE TABLE IF NOT EXISTS TABLE_
(
    id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    area                VARCHAR(128)                        NOT NULL,
    vertical            VARCHAR(128)                        NOT NULL,
    name                VARCHAR(128)                        NOT NULL,
    version             VARCHAR(128)                        NOT NULL,
    partition_unit      enum ('DAYS', 'HOURS', 'MINUTES')   NULL,
    partition_size      INT                                 NULL,
    parallelism         INT                                 NULL,
    max_bulk_size       INT                                 NULL,
    class_name          VARCHAR(512)                        NULL,
    transform_sql_query TEXT      DEFAULT NULL,
    partitioned         BOOLEAN                             NOT NULL DEFAULT FALSE,
    deactivated_ts      TIMESTAMP                           NULL,
    deactivated_epoch   BIGINT AS (IFNULL((TIMESTAMPDIFF(second, '1970-01-01', deactivated_ts)),
                                          0))              NOT NULL,
    created_ts          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    updated_ts          TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON update CURRENT_TIMESTAMP,
    description         VARCHAR(512)                        NULL,
    CONSTRAINT idx_tbl_unique UNIQUE (area, vertical, name, version, deactivated_epoch)
);

CREATE TABLE IF NOT EXISTS SOURCE
(
    id                        BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id                  BIGINT                           NOT NULL,
    connection_id             BIGINT                           NOT NULL,
    path                      VARCHAR(512)                     NOT NULL,
    format                    VARCHAR(64)                      NOT NULL,
    ingestion_mode            enum ('INTERVAL','DELTA', 'ALL') NOT NULL DEFAULT 'ALL',
    interval_temp_unit        enum ('DAYS','HOURS','MINUTES')  NULL,
    interval_temp_size        INT                              NULL,
    interval_column           VARCHAR(256)                              DEFAULT NULL,
    delta_column              VARCHAR(256)                              DEFAULT NULL,
    select_query              TEXT                                      DEFAULT NULL,
    parallel_load             BOOLEAN                          NOT NULL DEFAULT FALSE,
    parallel_partition_query  TEXT                                      DEFAULT NULL,
    parallel_partition_column VARCHAR(256)                              DEFAULT NULL,
    parallel_partition_num    INT                              NULL,
    auto_load                 BOOLEAN                          NOT NULL DEFAULT FALSE,
    deactivated_ts            TIMESTAMP                        NULL,
    deactivated_epoch   BIGINT AS (IFNULL((TIMESTAMPDIFF(second, '1970-01-01', deactivated_ts)),
                                          0))              NOT NULL,
    created_ts                TIMESTAMP                                 DEFAULT CURRENT_TIMESTAMP,
    updated_ts                TIMESTAMP                                 DEFAULT CURRENT_TIMESTAMP ON update CURRENT_TIMESTAMP,
    description               VARCHAR(512),
    CONSTRAINT idx_src_unique UNIQUE (table_id, connection_id, path, format, deactivated_epoch),
    FOREIGN KEY (table_id) REFERENCES TABLE_ (id) ON DELETE CASCADE,
    FOREIGN KEY (connection_id) REFERENCES CONNECTION (id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS TARGET
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id       BIGINT                              NOT NULL,
    connection_id  BIGINT                              NOT NULL,
    format         VARCHAR(64)                         NOT NULL,
    deactivated_ts TIMESTAMP                           NULL,
    deactivated_epoch   BIGINT AS (IFNULL((TIMESTAMPDIFF(second, '1970-01-01', deactivated_ts)),
                                          0))              NOT NULL,
    created_ts     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    updated_ts     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON update CURRENT_TIMESTAMP,
    description    VARCHAR(512),
    CONSTRAINT idx_trg_unique UNIQUE (table_id, connection_id, format, deactivated_epoch),
    FOREIGN KEY (table_id) REFERENCES TABLE_ (id) ON DELETE CASCADE,
    FOREIGN KEY (connection_id) REFERENCES CONNECTION (id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS DEPENDENCY
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_id             BIGINT                              NOT NULL,
    dependency_target_id BIGINT                              NOT NULL,
    dependency_table_id  BIGINT                              NOT NULL,
    view_name            VARCHAR(128)                        NULL,
    deactivated_ts       TIMESTAMP                           NULL,
    deactivated_epoch   BIGINT AS (IFNULL((TIMESTAMPDIFF(second, '1970-01-01', deactivated_ts)),
                                          0))              NOT NULL,
    created_ts           TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    updated_ts           TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON update CURRENT_TIMESTAMP,
    description          VARCHAR(512)                        NULL,
    CONSTRAINT idx_dpn_unique UNIQUE (table_id, dependency_target_id, dependency_table_id,
                                      deactivated_epoch),
    FOREIGN KEY (table_id) REFERENCES TABLE_ (id) ON DELETE CASCADE,
    FOREIGN KEY (dependency_target_id) REFERENCES TARGET (id) ON DELETE CASCADE,
    FOREIGN KEY (dependency_table_id) REFERENCES TABLE_ (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS PARTITION_
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    target_id    BIGINT       NOT NULL,
    partition_ts TIMESTAMP    NOT NULL,
    partitioned  BOOLEAN      NOT NULL DEFAULT FALSE,
    bookmarked   BOOLEAN      NOT NULL DEFAULT FALSE,
    max_bookmark VARCHAR(128) NULL,
    created_ts   TIMESTAMP             DEFAULT CURRENT_TIMESTAMP NULL,
    updated_ts   TIMESTAMP             DEFAULT CURRENT_TIMESTAMP NULL ON update CURRENT_TIMESTAMP,
    INDEX (partition_ts),
    INDEX (max_bookmark),
    UNIQUE (target_id, partition_ts),
    FOREIGN KEY (target_id) REFERENCES TARGET (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS PARTITION_DEPENDENCY
(
    id                      BIGINT AUTO_INCREMENT PRIMARY KEY,
    partition_id            BIGINT                              NOT NULL,
    dependency_partition_id BIGINT                              NOT NULL,
    created_ts              TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    updated_ts              TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON update CURRENT_TIMESTAMP,
    UNIQUE (partition_id, dependency_partition_id),
    FOREIGN KEY (partition_id) REFERENCES PARTITION_ (id) ON DELETE CASCADE,
    foreign key (dependency_partition_id) REFERENCES PARTITION_ (id) ON DELETE CASCADE
);