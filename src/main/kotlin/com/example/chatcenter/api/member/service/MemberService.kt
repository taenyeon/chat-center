package com.example.chatcenter.api.member.service

import com.example.chatcenter.api.member.domain.mapper.MemberDtoMapper
import com.example.chatcenter.api.member.domain.dto.MemberDto
import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.api.member.repository.MemberRepository
import com.example.chatcenter.api.user.domain.dto.UpdateRequest
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.function.encrypt
import com.example.chatcenter.common.function.logger
import com.example.chatcenter.common.http.constant.ResponseCode
import org.springframework.data.repository.findByIdOrNull

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberCacheService: MemberCacheService,
) {
    val log = logger()

    // mapStruct
    private val memberDtoMapper: MemberDtoMapper = Mappers.getMapper(MemberDtoMapper::class.java)


    // Entity
    fun addMember(member: Member) {
        checkExistMember(member.username!!)
        memberRepository.save(member)
    }

    fun deleteMember(id: Long) {
        try {
            memberRepository.deleteById(id)
        } catch (e: Exception) {
            throw ResponseException(ResponseCode.NOT_FOUND_ERROR, e)
        }
    }


    fun findMember(username: String): Member {
        log.info("username : $username")
        return memberRepository.findByUsername(username.encrypt())
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR)
    }

    fun checkExistMember(username: String) {
        if (memberRepository.findByUsername(username.encrypt()) != null) {
            throw ResponseException(ResponseCode.EXIST_MEMBER)
        }
    }

    fun findMember(id: Long): Member {
        return memberRepository.findByIdOrNull(id)
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR)
    }

    fun findMemberList(): MutableList<Member> {
        return memberRepository.findAll()
    }

    // Dto
    fun findMemberDto(username: String): MemberDto {
        return memberDtoMapper.toDto(findMember(username))
    }

    fun findMemberDto(id: Long): MemberDto {
        return memberDtoMapper.toDto(findMember(id))
    }

    fun findMemberDtoList(id: Long): List<MemberDto> {
        return memberRepository.findAllByIdIsNot(id)
            .map { member -> memberDtoMapper.toDto(member) }
    }

    fun update(id: Long, update: UpdateRequest) {
        val member = findMember(id)
        val merge = memberDtoMapper.merge(member, update)
        memberRepository.save(merge)
        memberCacheService.drop(id)
    }

}