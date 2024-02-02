package com.example.chatcenter.api.chat.service

import com.example.chatcenter.api.chat.domain.entity.ChatMember
import com.example.chatcenter.api.chat.domain.entity.ChatRoom
import com.example.chatcenter.api.chat.repository.ChatMemberRepository
import com.example.chatcenter.api.chat.repository.ChatRoomRepository
import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.http.constant.ResponseCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMemberRepository: ChatMemberRepository,
) {

    fun select(roomId: String): ChatRoom {
        return chatRoomRepository.findByIdOrNull(roomId)
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR)
    }

    fun selectList(userId: Long): MutableList<ChatRoom> {
        return chatRoomRepository.findAllByHostId(userId)
    }

    fun add(userId: Long, name: String): String {
        // create room
        val roomId = UUID.randomUUID().toString()
        val chatRoom = ChatRoom(roomId, name, userId)
        chatRoomRepository.save(chatRoom)

        //  init member
        val chatMember = ChatMember(null, userId, roomId)
        chatMemberRepository.save(chatMember)
        return roomId
    }

    fun delete(userId: Long, roomId: String) {
        val chatRoom = select(roomId)
        checkOwn(userId, chatRoom.hostId!!)
        chatRoomRepository.delete(chatRoom)
    }

    fun checkOwn(userId: Long, id: Long) {
        if (userId != id) throw ResponseException(ResponseCode.ACCESS_DENIED_ERROR)
    }

}