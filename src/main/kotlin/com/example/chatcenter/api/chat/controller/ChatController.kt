package com.example.chatcenter.api.chat.controller

import com.example.chatcenter.api.chat.domain.entity.ChatMessage
import com.example.chatcenter.api.chat.service.ChatMessageService
import com.example.chatcenter.common.http.constant.ResponseCode
import com.example.chatcenter.common.http.domain.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chat")
class ChatController(
    private val chatMessageService: ChatMessageService
) {
    @PostMapping("")
    fun add(@RequestBody message: ChatMessage): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(chatMessageService.add(message))
    }

    @GetMapping("/{roomId}")
    fun selectList(@PathVariable roomId: String): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(chatMessageService.selectList(roomId));
    }

    @GetMapping("/time")
    fun selectBetweenTime() {

    }


}