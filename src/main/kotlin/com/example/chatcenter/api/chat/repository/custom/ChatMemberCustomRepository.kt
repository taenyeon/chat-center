package com.example.chatcenter.api.chat.repository.custom

import com.example.chatcenter.api.chat.domain.entity.QChatMember.chatMember
import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.api.member.domain.entity.QMember.member
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ChatMemberCustomRepository(
    private val queryFactory: JPAQueryFactory,
){
    fun findAllMemberByRoomId(roomId: String): MutableList<Member> {
        return queryFactory
            .select(member)
            .from(member)
            .leftJoin(chatMember)
            .on(chatMember.memberId.eq(member.id))
            .where(chatMember.roomId.eq(roomId))
            .fetch()
    }

    fun findMemberByMemberIdAndRoomId(memberId: Long, roomId: String): Member? {
        return queryFactory
            .select(member)
            .from(chatMember)
            .leftJoin(member)
            .on(chatMember.memberId.eq(member.id))
            .where(
                chatMember.memberId.eq(memberId)
                    .and(chatMember.roomId.eq(roomId))
            )
            .fetchFirst()
    }
}