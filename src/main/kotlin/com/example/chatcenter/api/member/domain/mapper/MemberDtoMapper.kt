package com.example.chatcenter.api.member.domain.mapper

import com.example.chatcenter.api.member.domain.dto.response.MemberResponse
import com.example.chatcenter.api.member.domain.entity.Member
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import com.example.chatcenter.common.annotation.encrypt.Decrypt
import com.example.chatcenter.common.annotation.encrypt.Encrypt
import com.example.chatcenter.common.interfaces.EntityMapper
import com.example.chatcenter.common.util.EncryptUtil

@Mapper(uses = [EncryptUtil::class])
interface MemberDtoMapper : EntityMapper<Member, MemberResponse> {

    @Mapping(source = "username", target = "username", qualifiedBy = [Encrypt::class])
    @Mapping(source = "name", target = "name", qualifiedBy = [Encrypt::class])
    @Mapping(source = "phoneNumber", target = "phoneNumber", qualifiedBy = [Encrypt::class])
    override fun toEntity(dto: MemberResponse): Member

    @Mapping(source = "username", target = "username", qualifiedBy = [Decrypt::class])
    @Mapping(source = "name", target = "name", qualifiedBy = [Decrypt::class])
    @Mapping(source = "phoneNumber", target = "phoneNumber", qualifiedBy = [Decrypt::class])
    override fun toDto(entity: Member): MemberResponse

}