package com.example.chatcenter.api.chat.repository.support

import com.example.chatcenter.api.chat.domain.entity.ChatRoom
import com.example.chatcenter.api.chat.domain.entity.QChatMember.chatMember
import com.example.chatcenter.api.chat.domain.entity.QChatRoom.chatRoom
import com.example.chatcenter.api.chat.repository.support.interfaces.ChatRoomSupport
import com.example.chatcenter.api.member.domain.entity.QMember.member
import com.querydsl.jpa.impl.JPAQueryFactory

class ChatRoomSupportImpl(
    private val queryFactory: JPAQueryFactory,
) : ChatRoomSupport {
    override fun findByMemberId(memberId: Long): List<ChatRoom> {
        return queryFactory
            .select(chatRoom)
            .from(chatMember)
            .leftJoin(chatRoom)
            .on(chatRoom.id.eq(chatMember.roomId))
            .where(chatMember.memberId.eq(memberId))
            .fetch()
    }

    override fun findByMemberIdAndRoomId(memberId: Long, roomId: String): ChatRoom? {
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