package com.example.chatcenter.api.chat.service

import com.example.chatcenter.api.chat.domain.dto.RoomAddRequest
import com.example.chatcenter.api.chat.domain.entity.ChatMember
import com.example.chatcenter.api.chat.domain.entity.ChatRoom
import com.example.chatcenter.api.chat.repository.ChatMemberRepository
import com.example.chatcenter.api.chat.repository.ChatRoomRepository
import com.example.chatcenter.api.chat.repository.custom.ChatRoomCustomRepository
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.http.constant.ResponseCode
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ChatRoomService(
    private val chatRoomRepository: ChatRoomRepository,
    private val chatMemberRepository: ChatMemberRepository,
    private val chatRoomSupport: ChatRoomCustomRepository
) {

    fun select(roomId: String): ChatRoom {
        return chatRoomRepository.findByIdOrNull(roomId)
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR)
    }

    fun select(memberId: Long, roomId: String): ChatRoom {
        return chatRoomSupport.findByMemberIdAndRoomId(memberId, roomId)
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR)
    }

    fun selectList(memberId: Long): MutableList<ChatRoom> {
        return chatRoomSupport.findByMemberId(memberId)
    }

    fun selectListByHost(userId: Long): MutableList<ChatRoom> {
        return chatRoomRepository.findAllByHostId(userId)
    }


    fun add(userId: Long, roomAddRequest: RoomAddRequest): String {

        // create room
        val roomId = UUID.randomUUID().toString()
        val chatRoom = ChatRoom(roomId, roomAddRequest.roomName, userId)
        chatRoomRepository.save(chatRoom)

        //  init member
        val chatMember = ChatMember(null, userId, roomId)
        chatMemberRepository.save(chatMember)

        // init selectedMembers
        roomAddRequest.selectedMembers.forEach { memberId ->
            val selectedChatMember = ChatMember(null, memberId, roomId)
            chatMemberRepository.save(selectedChatMember)
        }

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