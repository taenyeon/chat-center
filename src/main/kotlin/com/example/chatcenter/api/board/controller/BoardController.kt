package com.example.chatcenter.api.board.controller

import com.example.chatcenter.api.board.domain.dto.BoardDto
import com.example.chatcenter.api.board.service.BoardService
import com.example.chatcenter.common.function.logger
import com.example.chatcenter.common.http.constant.ResponseCode
import com.example.chatcenter.common.http.domain.Response
import com.example.chatcenter.common.paging.annotation.QueryString
import com.example.chatcenter.common.paging.dto.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/board")
class BoardController(
    private val boardService: BoardService
) {
    val log = logger()

    @PostMapping("")
    fun addBoard(@RequestBody boardDto: BoardDto): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(boardService.addBoard(boardDto))
    }

    @GetMapping("/{id}")
    fun findBoard(@PathVariable id: Long): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(boardService.findBoard(id))
    }

    @GetMapping("")
    fun findBoards(@QueryString page: Page): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(boardService.findBoards(page.request()))
    }


}