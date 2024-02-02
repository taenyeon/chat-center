package com.example.chatcenter.api.board.service

import com.example.chatcenter.api.board.domain.dto.BoardDto
import com.example.chatcenter.api.board.domain.entity.Board
import com.example.chatcenter.api.board.domain.mapper.BoardDtoMapper
import com.example.chatcenter.api.board.repository.BoardRepository
import com.example.chatcenter.api.member.domain.mapper.MemberDtoMapper
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.function.logger
import com.example.chatcenter.common.function.user
import com.example.chatcenter.common.http.constant.ResponseCode
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardRepository,
) {
    val log = logger()

    // MapStruct
    private val boardDtoMapper: BoardDtoMapper = Mappers.getMapper(BoardDtoMapper::class.java)
    private val memberDtoMapper: MemberDtoMapper = Mappers.getMapper(MemberDtoMapper::class.java)

    //Entity
    fun add(boardDto: BoardDto) {
        boardDto.member = memberDtoMapper.toDto(user())
        val save = boardRepository.save(boardDtoMapper.toEntity(boardDto))
        log.info("save : ${save.id}")
    }

    fun select(id: Long): Board {
        return boardRepository.findByIdOrNull(id)
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR)
    }

    fun selectList(pageRequest: PageRequest): Page<BoardDto> {
        return boardRepository.findAll(pageRequest)
            .map { board -> boardDtoMapper.toDto(board) }
    }

    fun delete(userId: Long, id: Long) {
        val board = select(id)
        checkOwn(userId, board.member.id!!)
        boardRepository.delete(board)
    }

    fun modify(userId: Long, id: Long, boardDto: BoardDto) {
        val board = boardRepository.findByIdOrNull(id)
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR)
        checkOwn(userId, board.member.id!!)
        val merge = boardDtoMapper.merge(board, boardDto)
        boardRepository.save(merge)
    }

    // Dto
    fun findDto(id: Long): BoardDto {
        val board = boardRepository.findByIdOrNull(id)
            ?: throw ResponseException(ResponseCode.NOT_FOUND_ERROR)
        return boardDtoMapper.toDto(board)
    }

    // Etc
    fun checkOwn(userId: Long, id: Long) {
        if (userId != id) throw ResponseException(ResponseCode.ACCESS_DENIED_ERROR)
    }

}