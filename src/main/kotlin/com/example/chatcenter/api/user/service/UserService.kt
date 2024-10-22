package com.example.chatcenter.api.user.service

import com.example.chatcenter.api.member.domain.dto.MemberDto
import com.example.chatcenter.api.member.service.MemberService
import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.api.member.domain.mapper.MemberDtoMapper
import com.example.chatcenter.api.member.repository.MemberRepository
import com.example.chatcenter.api.member.service.MemberCacheService
import com.example.chatcenter.api.user.domain.dto.*
import com.example.chatcenter.api.user.domain.mapper.UserMapper
import org.mapstruct.factory.Mappers
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import com.example.chatcenter.api.user.domain.mapper.JoinRequestMapper
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.function.user
import com.example.chatcenter.common.http.constant.ResponseCode
import com.example.chatcenter.common.security.constant.TokenStatus
import com.example.chatcenter.common.security.provider.JwtTokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Service
class UserService(
    private val memberService: MemberService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    private val userMapper: UserMapper = Mappers.getMapper(UserMapper::class.java)
    private val memberMapper: MemberDtoMapper = Mappers.getMapper(MemberDtoMapper::class.java)

    fun findUser(): MemberDto {
        return userMapper.toMemberResponse(SecurityContextHolder.getContext().authentication.details as User)
    }

    fun join(joinRequest: JoinRequest) {
        memberService.checkExistMember(joinRequest.username)
        val member = memberMapper.toEntity(joinRequest)
        memberService.addMember(member)
    }

    fun login(loginRequest: LoginRequest): JwtToken {
        val member: Member = memberService.findMember(loginRequest.username)
        matchingPassword(loginRequest.password, member.password!!)
        return jwtTokenProvider.generateToken(member.id!!)
    }

    fun logout() {
        jwtTokenProvider.dropRefreshToken(findUser().id!!)
    }

    fun update(update: UpdateRequest) {
        memberService.update(findUser().id!!, update)
    }

    fun reIssueAccessToken(refreshToken: String): String {
        when (jwtTokenProvider.validateToken(refreshToken)) {
            TokenStatus.ALLOW -> {
                val id = jwtTokenProvider.parseIdFromJWT(refreshToken)
                return jwtTokenProvider.generateAccessToken(id, now = Date())
            }

            else -> throw ResponseException(ResponseCode.INVALID_TOKEN)
        }
    }

    fun matchingPassword(rawPassword: String, encodedPassword: String) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword))
            throw ResponseException(ResponseCode.WRONG_PASSWORD_ERROR)
    }
}