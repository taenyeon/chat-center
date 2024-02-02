package com.example.chatcenter.api.chat.repository.support.interfaces

import com.example.chatcenter.api.member.domain.entity.Member

interface ChatMemberSupport {
    fun findAllByRoomId(roomId: String): List<Member>
}