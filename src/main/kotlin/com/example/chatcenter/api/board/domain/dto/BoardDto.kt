package com.example.chatcenter.api.board.domain.dto

import com.example.chatcenter.api.member.domain.dto.MemberDto
import java.time.LocalDateTime

class BoardDto() {
    var id: Long = 0
    lateinit var member: MemberDto
    lateinit var title: String
    lateinit var payload: String
    var isDeleted: Boolean = false
    var createdAt: String? = null
    var updatedAt: String? = null
}
