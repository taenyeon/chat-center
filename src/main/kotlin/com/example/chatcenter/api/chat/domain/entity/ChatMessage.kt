package com.example.chatcenter.api.chat.domain.entity

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.example.chatcenter.api.chat.domain.constant.ChatType
import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.MongoId
import java.time.LocalDateTime

@Document(collection = "message")
class ChatMessage(
    @MongoId
    var id: String?,
    var type: ChatType = ChatType.TEXT,
    var roomId: String?,
    var memberId: Long?,
    var payload: String?,
    var createdAt: Long = System.currentTimeMillis(),
) {

    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(ChatMessage::id)
        private val toStringProperties = arrayOf(
            ChatMessage::id,
            ChatMessage::roomId,
            ChatMessage::memberId,
        )
    }
}