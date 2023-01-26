package io.qimia.uhrwerk.config.aws

import com.google.common.truth.Truth.assertThat
import io.qimia.uhrwerk.config.aws.S3FileReader.S3FileReader.endsWithFile
import io.qimia.uhrwerk.config.aws.S3FileReader.S3FileReader.s3Uri
import io.qimia.uhrwerk.config.aws.S3FileReader.S3FileReader.toDirFile
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class S3FileReaderUtilsTest {
    @Test
    fun endsWith() {
        val path1 = "root/dir1/dir2/"
        assertThat(endsWithFile(path1)).isFalse()

        val path2 = "something_here"
        assertThat(endsWithFile(path2)).isFalse()

        val blank = " "
        assertThat(endsWithFile(blank)).isFalse()

        val file1 = "root/dir1/dir2/file.yaml"
        assertThat(endsWithFile(file1)).isTrue()

        val file2 = "root/dir1/dir2/file.sql"
        assertThat(endsWithFile(file2)).isTrue()

        val notFile = "root/dir1/dir2/file.something/something"
        assertThat(endsWithFile(notFile)).isFalse()
    }

    @Test
    fun dirFileTest1() {
        val loc = "/root/dir1/dir2/table_a.yml"
        val (dir, file) = toDirFile(loc)

        assertThat(dir).isNotNull()
        assertThat(dir).isEqualTo("root/dir1/dir2/")

        assertThat(file).isNotNull()
        assertThat(file).isEqualTo("table_a.yml")
    }

    @Test
    fun dirFileTest2() {
        val loc = "/table_a.yml"
        val (dir, file) = toDirFile(loc)
        assertThat(dir).isNull()

        assertThat(file).isNotNull()
        assertThat(file).isEqualTo("table_a.yml")
    }
    @Test
    fun dirFileTest3() {
        val loc = "/"
        val (dir, file) = toDirFile(loc)
        assertThat(dir).isNull()
        assertThat(file).isNull()
    }

    @Test
    fun dirFileTest4() {
        val loc = ""
        val (dir, file) = toDirFile(loc)
        assertThat(dir).isNull()
        assertThat(file).isNull()
    }

    @Test
    fun dirFileTest5() {
        val loc = "some_dir"
        val (dir, file) = toDirFile(loc)
        assertThat(dir).isNotNull()
        assertThat(dir).isEqualTo("some_dir")
        assertThat(file).isNull()
    }

    @Test
    fun s3UriTest1() {

        val loc = "s3://mytest-bucket/root/dir1/dir2/"
        val uri = s3Uri(loc)
        assertThat(uri.bucket).isNotNull()
        assertThat(uri.bucket).isEqualTo("mytest-bucket")

        assertThat(uri.dir).isNotNull()
        assertThat(uri.dir).isEqualTo("root/dir1/dir2/")

        assertThat(uri.file).isNull()

        println(uri)

    }

    @Test
    fun s3UriTest2() {
        val loc = "s3://mytest-bucket/root/dir1/dir2/table_a.yml"
        val uri = s3Uri(loc)
        assertThat(uri.bucket).isNotNull()
        assertThat(uri.bucket).isEqualTo("mytest-bucket")

        assertThat(uri.dir).isNotNull()
        assertThat(uri.dir).isEqualTo("root/dir1/dir2/")

        assertThat(uri.file).isNotNull()
        assertThat(uri.file).isEqualTo("table_a.yml")

        println(uri)
    }

    @Test
    fun s3UriTest3() {
        val loc = "s3://mytest-bucket/table_a.yml"
        val uri = s3Uri(loc)
        assertThat(uri.bucket).isNotNull()
        assertThat(uri.bucket).isEqualTo("mytest-bucket")

        assertThat(uri.dir).isNull()

        assertThat(uri.file).isNotNull()
        assertThat(uri.file).isEqualTo("table_a.yml")

        println(uri)
    }

    @Test
    fun s3UriTest4() {
        val loc = "s3://mytest-bucket/"
        val uri = s3Uri(loc)
        assertThat(uri.bucket).isNotNull()
        assertThat(uri.bucket).isEqualTo("mytest-bucket")

        assertThat(uri.dir).isNull()

        assertThat(uri.file).isNull()

        println(uri)
    }

    @Test
    fun s3UriTest5() {
        val loc = "file://mytest-bucket/"
        val uri = assertThrows<IllegalArgumentException> { s3Uri(loc) }
    }

    @Test
    fun s3UriTest6() {
        val loc = "just something"
        val uri = s3Uri(loc)
        assertThat(uri.bucket).isNull()

        assertThat(uri.dir).isNull()

        assertThat(uri.file).isNull()

        println(uri)
    }
}