package com.example.chatcenter.api.member.domain.entity

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import com.example.chatcenter.api.board.domain.entity.Board
import com.example.chatcenter.common.jpa.entity.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var username: String?,
    var password: String?,
    var name: String?,
    var phoneNumber: String?,
) : BaseTimeEntity() {
    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(Member::id)
        private val toStringProperties = arrayOf(
            Member::id,
            Member::username,
            Member::name
        )
    }
}