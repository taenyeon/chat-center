package com.example.chatcenter.api.chat.domain.entity

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.example.chatcenter.api.member.domain.entity.Member
import jakarta.persistence.*

@Entity
@Table(name = "chat_member")
class ChatMember(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var memberId: Long,
    var roomId: String,
) {
    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(ChatMember::id)
        private val toStringProperties = arrayOf(
            ChatMember::id,
            ChatMember::memberId,
            ChatMember::roomId
        )
    }
}