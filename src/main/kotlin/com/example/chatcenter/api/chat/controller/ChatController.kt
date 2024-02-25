package com.example.chatcenter.api.chat.controller

import com.example.chatcenter.api.chat.domain.entity.ChatMessage
import com.example.chatcenter.api.chat.service.ChatMessageService
import com.example.chatcenter.common.http.constant.ResponseCode
import com.example.chatcenter.common.http.domain.Response
import com.example.chatcenter.common.message.kafka.Producer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatController(
    private val chatMessageService: ChatMessageService
) {
    @PostMapping("")
    fun add(@RequestBody message: ChatMessage): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(chatMessageService.add(message));
    }

    @GetMapping("")
    fun select(){

    }





}