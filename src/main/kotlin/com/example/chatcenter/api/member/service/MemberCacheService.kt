package com.example.chatcenter.api.member.service

import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.api.member.repository.MemberRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.http.constant.ResponseCode
import org.springframework.cache.annotation.CacheEvict

@Service
class MemberCacheService(
    private val memberRepository: MemberRepository
) {

    @Cacheable(value = ["member"], key = "#id")
    fun find(id: Long): Member {
        return memberRepository.findById(id)
            .orElseThrow { ResponseException(ResponseCode.NOT_FOUND_ERROR) }
    }

    @CacheEvict(value = ["member"], key = "#id")
    fun drop(id: Long) {
    }
}