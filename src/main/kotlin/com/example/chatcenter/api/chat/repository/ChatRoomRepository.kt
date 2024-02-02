package com.example.chatcenter.api.chat.repository

import com.example.chatcenter.api.chat.domain.entity.ChatMember
import com.example.chatcenter.api.chat.domain.entity.ChatMessage
import com.example.chatcenter.api.chat.domain.entity.ChatRoom
import com.example.chatcenter.api.chat.repository.support.interfaces.ChatRoomSupport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ChatRoomRepository : JpaRepository<ChatRoom, String>, ChatRoomSupport {
    fun findAllByHostId(hostId : Long): MutableList<ChatRoom>
}