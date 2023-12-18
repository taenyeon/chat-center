package com.example.chatcenter.common.annotation.encrypt

import org.mapstruct.Qualifier

@Qualifier
@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class Decrypt()
