package com.example.chatcenter.api.chat.service

import com.example.chatcenter.api.chat.domain.entity.ChatRoom
import com.example.chatcenter.api.chat.repository.ChatRoomRepository
import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.api.member.repository.MemberRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.http.constant.ResponseCode
import org.springframework.data.repository.findByIdOrNull

@Service
class ChatRoomCacheService(
    private val chatRoomRepository: ChatRoomRepository
) {

    @Cacheable(value = ["chatRoom"], key = "#id")
    fun select(id: String): ChatRoom {
        return chatRoomRepository.findByIdOrNull(id)
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR)
    }

}