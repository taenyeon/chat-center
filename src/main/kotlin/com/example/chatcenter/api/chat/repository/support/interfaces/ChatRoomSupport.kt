package com.example.chatcenter.api.chat.repository.support.interfaces

import com.example.chatcenter.api.chat.domain.entity.ChatRoom

interface ChatRoomSupport{
    fun findByMemberId(memberId: Long): MutableList<ChatRoom>
    fun findByMemberIdAndRoomId(memberId:Long, roomId:String): ChatRoom?

}