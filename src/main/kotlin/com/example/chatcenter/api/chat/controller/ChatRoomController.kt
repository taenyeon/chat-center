package com.example.chatcenter.api.chat.controller

import com.example.chatcenter.api.chat.domain.dto.RoomAddRequest
import com.example.chatcenter.api.chat.service.ChatRoomService
import com.example.chatcenter.common.function.logger
import com.example.chatcenter.common.function.user
import com.example.chatcenter.common.http.constant.ResponseCode
import com.example.chatcenter.common.http.domain.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/chatRoom")
class ChatRoomController(
    private val chatRoomService: ChatRoomService
) {
    val log = logger()

    @PostMapping("")
    fun add(@RequestBody roomAddRequest: RoomAddRequest): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(chatRoomService.add(user().id!!, roomAddRequest.roomName))
    }

    @GetMapping("/{roomId}")
    fun select(@PathVariable roomId: String): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(chatRoomService.select(roomId))
    }

    @DeleteMapping("/{roomId}")
    fun delete(@PathVariable roomId: String): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(chatRoomService.delete(user().id!!, roomId))
    }
}