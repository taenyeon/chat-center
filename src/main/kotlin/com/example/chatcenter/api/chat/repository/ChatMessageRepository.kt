package com.example.chatcenter.api.chat.repository

import com.example.chatcenter.api.chat.domain.entity.ChatMessage
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ChatMessageRepository : MongoRepository<ChatMessage, String> {
    fun findAllByRoomIdAndCreatedAtBetween(
        roomId: String,
        startCreatedAt: Long,
        endCreatedAt: Long
    ): List<ChatMessage>

    fun findAllByRoomId(roomId: String): List<ChatMessage>

    fun findAllByRoomIdAndMemberId(roomId: String, memberId: Long): List<ChatMessage>
}