package com.example.chatcenter.api.chat.service

import com.example.chatcenter.api.chat.domain.entity.ChatMember
import com.example.chatcenter.api.chat.repository.ChatMemberRepository
import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.http.constant.ResponseCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChatMemberService(
    private val chatMemberRepository: ChatMemberRepository,
) {

    fun add(userId: Long, roomId: String) {
        val chatMember = ChatMember(null, userId, roomId)
        chatMemberRepository.save(chatMember)
    }

    fun delete(userId: Long, roomId: String) {
        val chatMember = (chatMemberRepository.findByMemberIdAndRoomId(userId, roomId)
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR))
        chatMemberRepository.delete(chatMember)
    }
}