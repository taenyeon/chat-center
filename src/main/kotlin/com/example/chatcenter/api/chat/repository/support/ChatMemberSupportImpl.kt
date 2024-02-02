package com.example.chatcenter.api.chat.repository.support

import com.example.chatcenter.api.chat.domain.entity.QChatMember.chatMember
import com.example.chatcenter.api.chat.domain.entity.QChatRoom.chatRoom
import com.example.chatcenter.api.chat.repository.support.interfaces.ChatMemberSupport
import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.api.member.domain.entity.QMember.member
import com.querydsl.jpa.impl.JPAQueryFactory

class ChatMemberSupportImpl(
    private val queryFactory: JPAQueryFactory,
) : ChatMemberSupport {
    override fun findAllByRoomId(roomId: String): List<Member> {
        return queryFactory
            .select(member)
            .from(chatMember)
            .leftJoin(member)
            .on(chatMember.memberId.eq(member.id))
            .where(chatMember.roomId.eq(roomId))
            .fetch()
    }
}