package com.example.chatcenter.common.message.kafka

import com.example.chatcenter.api.chat.domain.entity.ChatMessage
import com.example.chatcenter.common.function.logger
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class Producer(
    private val kafkaTemplate: KafkaTemplate<String, ChatMessage>,
) {

    val log = logger()
    private val TOPIC = "chat"

    fun sendMessage(chatMessage: ChatMessage) {
        log.info("[KAFKA - PRODUCER] SEND - chatMessage : $chatMessage")
        kafkaTemplate.send(TOPIC, chatMessage)
    }

}