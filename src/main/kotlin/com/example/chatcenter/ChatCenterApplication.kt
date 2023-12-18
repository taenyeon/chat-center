package com.example.chatcenter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatCenterApplication

fun main(args: Array<String>) {
    runApplication<ChatCenterApplication>(*args)
}
