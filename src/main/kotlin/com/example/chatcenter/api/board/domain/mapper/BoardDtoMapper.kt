package com.example.chatcenter.api.board.domain.mapper

import com.example.chatcenter.api.board.domain.dto.BoardDto
import com.example.chatcenter.api.board.domain.entity.Board
import com.example.chatcenter.api.member.domain.mapper.MemberDtoMapper
import org.mapstruct.Mapper
import com.example.chatcenter.common.interfaces.EntityMapper
import com.example.chatcenter.common.util.EncryptUtil
import org.mapstruct.BeanMapping
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy

@Mapper(uses = [MemberDtoMapper::class])
interface BoardDtoMapper : EntityMapper<Board, BoardDto> {
    override fun toEntity(dto: BoardDto): Board

    override fun toDto(entity: Board): BoardDto

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "member", ignore = true)
    fun merge(@MappingTarget entity: Board, dto: BoardDto) : Board
}