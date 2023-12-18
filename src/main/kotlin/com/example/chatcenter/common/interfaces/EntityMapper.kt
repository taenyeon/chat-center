package com.example.chatcenter.common.interfaces

interface EntityMapper<E, D> {

    fun toEntity(dto: D): E
    fun toDto(entity: E): D
}