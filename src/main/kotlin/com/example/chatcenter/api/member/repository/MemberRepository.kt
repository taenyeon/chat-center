package com.example.chatcenter.api.member.repository

import com.example.chatcenter.api.member.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface MemberRepository : JpaRepository<Member, Long> {

    fun findByUsername(username: String?): Member?

    fun findAllByIdIsNot(id: Long): MutableList<Member>

}