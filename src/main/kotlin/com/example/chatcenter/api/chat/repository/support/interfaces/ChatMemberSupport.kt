package com.example.chatcenter.api.chat.repository.support.interfaces

import com.example.chatcenter.api.member.domain.entity.Member
import org.springframework.stereotype.Repository

interface ChatMemberSupport {
    fun findAllMemberByRoomId(roomId: String): MutableList<Member>
    fun findMemberByMemberIdAndRoomId(memberId:Long, roomId: String): Member?
}