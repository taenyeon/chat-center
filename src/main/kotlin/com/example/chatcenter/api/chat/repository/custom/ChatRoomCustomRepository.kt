package com.example.chatcenter.api.chat.repository.custom

import com.example.chatcenter.api.chat.domain.entity.ChatRoom
import com.example.chatcenter.api.chat.domain.entity.QChatMember.chatMember
import com.example.chatcenter.api.chat.domain.entity.QChatRoom.chatRoom
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ChatRoomCustomRepository(
    private val queryFactory: JPAQueryFactory,
) {
    fun findByMemberId(memberId: Long): MutableList<ChatRoom> {
        return queryFactory
            .select(chatRoom)
            .from(chatMember)
            .leftJoin(chatRoom)
            .on(chatRoom.id.eq(chatMember.roomId))
            .where(chatMember.memberId.eq(memberId))
            .fetch()
    }

    fun findByMemberIdAndRoomId(memberId: Long, roomId: String): ChatRoom? {
        return queryFactory
            .select(chatRoom)
            .from(chatMember)
            .leftJoin(chatRoom)
            .on(chatRoom.id.eq(chatMember.roomId))
            .where(
                chatMember.memberId.eq(memberId)
                    .and(chatMember.roomId.eq(roomId))
            )
            .fetchFirst()
    }
}