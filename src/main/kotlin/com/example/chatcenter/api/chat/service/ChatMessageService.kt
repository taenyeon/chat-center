package com.example.chatcenter.api.chat.service

import com.example.chatcenter.api.chat.domain.entity.ChatMessage
import com.example.chatcenter.api.chat.repository.ChatMessageRepository
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.http.constant.ResponseCode
import com.example.chatcenter.common.message.kafka.Producer
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ChatMessageService(
    private val chatMessageRepository: ChatMessageRepository,
    private val producer: Producer
) {
    fun add(chatMessage: ChatMessage) {
        producer.sendMessage(chatMessage)
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

    fun selectList(roomId: String, startCreatedAt:Long, endCreatedAt:Long): List<ChatMessage> {
        return chatMessageRepository.findAllByRoomIdAndCreatedAtBetween(roomId, startCreatedAt, endCreatedAt)
    }
}