package com.example.chatcenter.common.util

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.http.constant.ResponseCode
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@Component
class FileUtil(
    private val s3Client: AmazonS3Client,
    @Value("\${cloud.aws.s3.bucket}")
    val bucket: String,
) {

    fun upload(file: MultipartFile): String {

        val now = LocalDateTime.now()
        val date = DateUtil.yyyyMMdd(now)
        val time = DateUtil.HHmmss(now)

        val objectMetadata = ObjectMetadata()
        objectMetadata.contentLength = file.size
        objectMetadata.contentType = file.contentType
        val filePath = "$date/$time/${file.originalFilename}"
        try {
            val inputStream = file.inputStream
            s3Client.putObject(
                bucket,
                filePath,
                inputStream,
                objectMetadata
            )
            return s3Client.getUrl(bucket, filePath).toString()
        } catch (e: Exception) {
            throw ResponseException(ResponseCode.UNKNOWN_ERROR)
        }
    }
}