package io.qimia.uhrwerk.config.aws

import org.junit.jupiter.api.Test


class S3FileReaderTest {
    @Test
    fun listS3OFiles() {
        val reader = S3FileReader("eu-west-1", true)
        val list = reader.listBucketObjects("s3://qimiaio-uhrwerk-dev-code01/")
        list.forEach { println(it) }
    }
}