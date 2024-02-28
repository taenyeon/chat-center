package com.example.chatcenter.api.member.domain.entity

import au.com.console.kassava.kotlinEquals
import au.com.console.kassava.kotlinHashCode
import au.com.console.kassava.kotlinToString
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "member_connect")
class MemberConnect(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var memberId: Long?,
    var isConnected: Boolean = false,
    var lastConnectedAt: LocalDateTime?,
) {

    override fun toString() = kotlinToString(properties = toStringProperties)
    override fun equals(other: Any?) = kotlinEquals(other = other, properties = equalsAndHashCodeProperties)
    override fun hashCode() = kotlinHashCode(properties = equalsAndHashCodeProperties)

    companion object {
        private val equalsAndHashCodeProperties = arrayOf(MemberConnect::memberId)
        private val toStringProperties = arrayOf(
            MemberConnect::memberId,
            MemberConnect::isConnected,
            MemberConnect::lastConnectedAt
        )
    }
}