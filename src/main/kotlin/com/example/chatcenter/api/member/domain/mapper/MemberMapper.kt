package com.example.chatcenter.api.member.domain.mapper

import com.example.chatcenter.api.member.domain.dto.MemberDto
import com.example.chatcenter.api.member.domain.entity.Member
import com.example.chatcenter.api.user.domain.dto.JoinRequest
import com.example.chatcenter.api.user.domain.dto.UpdateRequest
import com.example.chatcenter.common.encrypt.annotation.Decrypt
import com.example.chatcenter.common.encrypt.annotation.Encrypt
import com.example.chatcenter.common.encrypt.annotation.PasswordEncrypt
import com.example.chatcenter.common.interfaces.EntityMapper
import com.example.chatcenter.common.util.EncryptUtil
import org.mapstruct.*

@Mapper(uses = [EncryptUtil::class])
interface MemberDtoMapper : EntityMapper<Member, MemberDto> {

    @Mapping(source = "username", target = "username", qualifiedBy = [Encrypt::class])
    @Mapping(source = "name", target = "name", qualifiedBy = [Encrypt::class])
    @Mapping(source = "phoneNumber", target = "phoneNumber", qualifiedBy = [Encrypt::class])
    override fun toEntity(dto: MemberDto): Member

    @Mapping(source = "username", target = "username", qualifiedBy = [Decrypt::class])
    @Mapping(source = "name", target = "name", qualifiedBy = [Decrypt::class])
    @Mapping(source = "phoneNumber", target = "phoneNumber", qualifiedBy = [Decrypt::class])
    override fun toDto(entity: Member): MemberDto

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun merge(@MappingTarget target: Member, source: UpdateRequest): Member

    @Mapping(source = "password", target = "password", qualifiedBy = [PasswordEncrypt::class])
    @Mapping(source = "username", target = "username", qualifiedBy = [Encrypt::class])
    @Mapping(source = "name", target = "name", qualifiedBy = [Encrypt::class])
    @Mapping(source = "phoneNumber", target = "phoneNumber", qualifiedBy = [Encrypt::class])
    fun toEntity(dto: JoinRequest): Member
}