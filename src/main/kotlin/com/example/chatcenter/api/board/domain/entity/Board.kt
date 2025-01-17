package com.example.chatcenter.api.board.domain.entity

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
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
) : BaseTimeEntity() {
    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Board::id)
        private val toStringProperties = arrayOf(
            Board::id,
            Board::title,
        )
    }
}