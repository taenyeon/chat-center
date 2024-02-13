package com.example.chatcenter.common.message.kafka

import com.example.chatcenter.api.chat.domain.entity.ChatMessage
import com.example.chatcenter.api.chat.repository.ChatMessageRepository
import com.example.chatcenter.common.function.logger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class Consumer(
    private val chatMessageRepository: ChatMessageRepository
) {
    val log = logger()

    @KafkaListener(topics = ["chat"], groupId = "chat-center")
    fun receiveMessage(chatMessage: ChatMessage) {
        chatMessageRepository.save(chatMessage)
    }
}