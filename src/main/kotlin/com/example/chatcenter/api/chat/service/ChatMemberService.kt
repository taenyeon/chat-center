package com.example.chatcenter.api.chat.service

import com.example.chatcenter.api.chat.domain.entity.ChatMember
import com.example.chatcenter.api.chat.repository.ChatMemberRepository
import com.example.chatcenter.api.chat.repository.custom.ChatMemberCustomRepository
import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.http.constant.ResponseCode
import org.springframework.stereotype.Service

@Service
class ChatMemberService(
    private val chatMemberRepository: ChatMemberRepository,
    private val chatMemberSupport: ChatMemberCustomRepository,
) {

    fun add(userId: Long, roomId: String) {
        val chatMember = ChatMember(null, userId, roomId)
        chatMemberRepository.save(chatMember)
    }

    fun select(userId: Long, roomId: String): Member {
        return chatMemberSupport.findMemberByMemberIdAndRoomId(userId, roomId)
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR)
    }

    fun selectList(roomId: String): MutableList<Member> {
        return chatMemberSupport.findAllMemberByRoomId(roomId)
    }

    fun delete(userId: Long, roomId: String) {
        val chatMember = (chatMemberRepository.findByMemberIdAndRoomId(userId, roomId)
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR))
        chatMemberRepository.delete(chatMember)
    }
}