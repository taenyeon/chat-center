package com.example.chatcenter.api.chat.service

import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.common.function.logger
import com.example.chatcenter.api.chat.repository.support.ChatMemberSupportImpl
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.cache.annotation.CacheEvict

@Service
class ChatMemberCacheService(
    private val chatMemberSupport: ChatMemberSupportImpl,
) {
    val log = logger()
    @Cacheable(value = ["chatMember"], key = "#roomId")
    fun selectList(roomId: String): MutableList<Member> {
        return chatMemberSupport.findAllMemberByRoomId(roomId)
    }

    @CacheEvict(value = ["chatMember"], key = "#roomId")
    fun delete(roomId: String) {
        log.info("[REDIS] DELETE - chatMember::$roomId")
    }

}