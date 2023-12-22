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
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardRepository,
) {
    val log = logger()

    private val boardDtoMapper: BoardDtoMapper = Mappers.getMapper(BoardDtoMapper::class.java)
    private val memberDtoMapper: MemberDtoMapper = Mappers.getMapper(MemberDtoMapper::class.java)
    fun addBoard(boardDto: BoardDto){
        boardDto.member = memberDtoMapper.toDto(user())
        val save = boardRepository.save(boardDtoMapper.toEntity(boardDto))
        log.info("save : ${save.id}")
    }

    fun findBoard(id: Long): BoardDto {
        val board = boardRepository.findById(id)
            .orElseThrow { throw ResponseException(ResponseCode.NOT_FOUND_ERROR) }
        return boardDtoMapper.toDto(board)
    }

    fun findBoards(pageRequest: PageRequest): Page<BoardDto> {
        return boardRepository.findAll(pageRequest)
            .map { board -> boardDtoMapper.toDto(board) }
    }

    fun deleteBoard(id: Long) {
        val board = boardRepository.findById(id)
            .orElseThrow { throw ResponseException(ResponseCode.NOT_FOUND_ERROR) }
        checkOwn(board.member.id!!)
        boardRepository.delete(board)
    }

    fun modifyBoard(id: Long, boardDto: BoardDto) {
        val board = boardRepository.findById(id)
            .orElseThrow { throw ResponseException(ResponseCode.NOT_FOUND_ERROR) }
        checkOwn(board.member.id!!)
        val merge = boardDtoMapper.merge(board, boardDto)
        boardRepository.save(merge)
    }

    fun checkOwn(id: Long) {
        if (user().id != id) throw ResponseException(ResponseCode.ACCESS_DENIED_ERROR)
    }

}