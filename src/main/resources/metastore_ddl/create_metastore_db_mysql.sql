-- DROP DATABASE IF EXISTS UHRWERK_METASTORE;
CREATE DATABASE IF NOT EXISTS UHRWERK_METASTORE_JPA;
CREATE USER IF NOT EXISTS 'UHRWERK_USER'@'%' IDENTIFIED BY 'Xq92vFqEKF7TB8H9';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX, DROP ON UHRWERK_METASTORE_JPA.* TO 'UHRWERK_USER'@'%';
FLUSH PRIVILEGES;