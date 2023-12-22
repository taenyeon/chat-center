package com.example.chatcenter.api.board.domain.entity

import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.common.jpa.entity.BaseTimeEntity
import jakarta.persistence.*

@Entity
@Table(name = "board")
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    @OneToOne
    @JoinColumn(name = "user_id")
    var member: Member,
    var title : String,
    var payload : String,
    var isDeleted: Boolean
) : BaseTimeEntity()