package com.example.chatcenter.common.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtil {


    companion object {
    private val yyyyMMddHHmmssFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")
    private val yyyyMMddFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    private val HHmmssFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HHmmssSSS")
        fun yyyyMMdd(): String? {
            val now = LocalDateTime.now()
            return now.format(yyyyMMddFormatter)
        }

        fun yyyyMMddHHmmss(): String? {
            val now = LocalDateTime.now()
            return now.format(yyyyMMddHHmmssFormatter)
        }

        fun HHmmss(): String? {
            val now = LocalDateTime.now()
            return now.format(HHmmssFormatter)
        }

        fun yyyyMMdd(now: LocalDateTime): String? {
            return now.format(yyyyMMddFormatter)
        }

        fun yyyyMMddHHmmss(now: LocalDateTime): String? {
            return now.format(yyyyMMddHHmmssFormatter)
        }

        fun HHmmss(now: LocalDateTime): String? {
            return now.format(HHmmssFormatter)
        }
    }
}