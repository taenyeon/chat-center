package com.example.chatcenter.api.chat.domain.entity

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.common.jpa.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "chat_room")
class ChatRoom(
    @Id
    var id: String?,
    var name: String?,
    var hostId: Long?,

    ) : BaseTimeEntity() {
    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(ChatRoom::id)
        private val toStringProperties = arrayOf(
            ChatRoom::id,
            ChatRoom::name,
            ChatRoom::hostId,
        )
    }
}