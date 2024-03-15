package com.example.chatcenter.api.chat.domain.dto

import jakarta.validation.constraints.NotNull

class RoomAddRequest {

    @NotNull
    lateinit var roomName: String

    var selectedMembers: List<Long> = mutableListOf()
}