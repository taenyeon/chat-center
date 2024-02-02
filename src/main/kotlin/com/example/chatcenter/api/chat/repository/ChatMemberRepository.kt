package com.example.chatcenter.api.chat.repository

import com.example.chatcenter.api.chat.domain.entity.ChatMember
import com.example.chatcenter.api.chat.domain.entity.ChatMessage
import com.example.chatcenter.api.chat.repository.support.interfaces.ChatMemberSupport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ChatMemberRepository : JpaRepository<ChatMember, String>, ChatMemberSupport {
    fun findByMemberIdAndRoomId(memberId: Long, roomId: String): ChatMember?
}