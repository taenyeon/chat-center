package com.example.chatcenter.api.chat.service

import com.example.chatcenter.api.chat.domain.constant.ChatType
import com.example.chatcenter.api.chat.domain.entity.ChatMessage
import com.example.chatcenter.api.chat.repository.ChatMessageRepository
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.function.user
import com.example.chatcenter.common.http.constant.ResponseCode
import com.example.chatcenter.common.message.kafka.Producer
import com.example.chatcenter.common.util.FileUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ChatMessageService(
    private val chatMessageRepository: ChatMessageRepository,
    private val producer: Producer,
    private val fileUtil: FileUtil,
) {
    fun add(message: ChatMessage) {
        producer.sendMessage(message)
    }

    fun addFile(
        roomId: String,
        file: MultipartFile,
    ) {
        val message = ChatMessage(
            null,
            ChatType.IMAGE,
            roomId,
            user().id,
            null,
        )
        val filePath = fileUtil.upload(file)

        message.payload = filePath

        producer.sendMessage(message)
    }

    fun select(id: String): ChatMessage {
        return chatMessageRepository.findByIdOrNull(id)
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR)
    }

    fun selectList(roomId: String): List<ChatMessage> {
        return chatMessageRepository.findAllByRoomId(roomId)
    }

    fun selectList(roomId: String, memberId: Long): List<ChatMessage> {
        return chatMessageRepository.findAllByRoomIdAndMemberId(roomId, memberId)
    }

    fun selectList(roomId: String, startCreatedAt: Long, endCreatedAt: Long): List<ChatMessage> {
        return chatMessageRepository.findAllByRoomIdAndCreatedAtBetween(roomId, startCreatedAt, endCreatedAt)
    }
}