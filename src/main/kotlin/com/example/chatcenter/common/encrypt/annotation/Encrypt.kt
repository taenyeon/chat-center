package com.example.chatcenter.common.encrypt.annotation

import org.mapstruct.Qualifier

@Qualifier
@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class Encrypt()
