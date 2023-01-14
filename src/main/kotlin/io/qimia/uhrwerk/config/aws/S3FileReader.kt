package io.qimia.uhrwerk.config.aws

import io.qimia.uhrwerk.config.aws.S3FileReader.S3FileReader.s3Uri
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.core.exception.SdkClientException
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ListObjectsRequest
import software.amazon.awssdk.services.s3.model.S3Exception
import java.net.URI
import java.net.URISyntaxException


data class S3URI(
    var bucket: String? = null, var prefix: String? = null, var file: String? = null
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
                    .prefix(uri.prefix)
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
            var s3 = S3URI()
            try {
                val uri = URI(loc)
                val scheme = uri.scheme
                if (!scheme.isNullOrEmpty())
                    require(scheme == "s3")
                val bucket = uri.host
                if (!bucket.isNullOrEmpty() && bucket.isNotBlank())
                    s3.bucket = bucket

                var path = uri.path
                if (!path.isNullOrEmpty()) {
                    path = path.replaceFirst("/", "").trim()
                    if (path.isNotEmpty())
                        if (endsWithFile(path)) {
                            val idx = path.lastIndexOf('/')
                            if (idx > 0) {
                                s3.prefix = path.substring(0, idx + 1).trim()
                                s3.file = path.substring(idx + 1).trim()
                            } else
                                s3.file = path.trim()
                        } else s3.prefix = path.trim()
                }
            } catch (exp: URISyntaxException) {
            }
            return s3
        }

        fun endsWithFile(path: String): Boolean {
            val regex = "\\.\\w+$".toRegex()
            return regex.containsMatchIn(path)
        }

    }
}