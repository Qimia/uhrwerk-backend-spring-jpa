package io.qimia.uhrwerk.config.aws

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.core.exception.SdkClientException
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ListObjectsRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import java.net.URI
import java.net.URISyntaxException


data class S3URI(
    var bucket: String? = null, var dir: String? = null, var file: String? = null
)

open class S3FileReader(private val regionName: String, private val useProfile: Boolean = false) {
    private val region = Region.of(regionName)
    private var s3Client: S3Client

    init {
        require(
            Region.regions().contains(region)
        ) { "The given region=$regionName doesn't exist." }
        s3Client = if (useProfile)
            S3Client.builder()
                .credentialsProvider(ProfileCredentialsProvider.create())
                .region(region).build()
        else
            S3Client.builder()
                .region(region).build()
    }


    fun listBucketObjects(loc: String): List<S3URI> {
        val list = mutableListOf<S3URI>()
        try {
            val uri = s3Uri(loc)
            val listObjects =
                ListObjectsRequest.builder()
                    .bucket(uri.bucket)
                    .prefix(uri.dir)
                    .build()
            val res = s3Client.listObjects(listObjects)
            val objects = res.contents()
            for (myValue in objects) {
                val cUri = s3Uri(myValue.key())
                cUri.bucket = uri.bucket
                list.add(cUri)
            }
        } catch (e: Exception) {
            when (e) {
                is S3Exception -> println(e.awsErrorDetails().errorMessage())
                is SdkClientException -> println(e.stackTrace)
            }

        }
        return list
    }


    companion object S3FileReader {

        fun s3Uri(loc: String): S3URI {
            var bucket: String? = null
            var dir: String? = null
            var file: String? = null

            try {
                val uri = URI(loc)

                val scheme = uri.scheme
                if (!scheme.isNullOrEmpty())
                    require(scheme == "s3")

                val host = uri.host
                if (!host.isNullOrEmpty() && host.isNotBlank())
                    bucket = host.trim()

                val dirFile = toDirFile(uri.path)
                dir = dirFile.first
                file = dirFile.second

            } catch (exp: URISyntaxException) {
            }
            return S3URI(bucket, dir, file)
        }

        fun toDirFile(loc: String?): Pair<String?, String?> {
            var dir: String? = null
            var file: String? = null

            if (!loc.isNullOrEmpty()) {
                val path = loc.replaceFirst("/", "").trim()
                if (path.isNotEmpty())
                    if (endsWithFile(path)) {
                        val idx = path.lastIndexOf('/')
                        if (idx > 0) {
                            dir = path.substring(0, idx + 1).trim()
                            file = path.substring(idx + 1).trim()
                        } else
                            file = path.trim()
                    } else dir = path.trim()
            }
            return dir to file
        }

        fun endsWithFile(path: String): Boolean {
            if (!path.isNullOrEmpty() && path.isNotBlank()) {
                val regex = "\\.\\w+$".toRegex()
                return regex.containsMatchIn(path)
            }
            return false

        }
    }
}