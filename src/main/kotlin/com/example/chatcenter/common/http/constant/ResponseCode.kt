package com.example.chatcenter.common.http.constant

import com.example.chatcenter.common.http.domain.Response
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity


enum class ResponseCode(
    private val code: String,
    private val message: String,
    private val statusCode: Int = 200,
) {

    // SUCCESS
    SUCCESS("SUCCESS", "success."),

    // ERROR

    // USER
    WRONG_PASSWORD_ERROR("WRONG_PASSWORD_ERROR", "wrong password error occurred."),
    ALREADY_LOGOUT_ERROR("ALREADY_LOGOUT_ERROR", "already logout error occurred."),

    // AUTH
    AUTH_ERROR("AUTH_ERROR", "auth error occurred.", 401),
    INVALID_TOKEN("INVALID_TOKEN_ERROR", "invalid token error occurred.", 401),
    ACCESS_DENIED_ERROR("ACCESS_DENIED_ERROR", "access denied error occurred.", 403),

    // MEMBER
    EXIST_MEMBER("EXIST_MEMBER", "already member error occurred."),

    // DEFAULT
    UNKNOWN_ERROR("UNKNOWN_ERROR", "unknown error occurred."),
    MANUAL_ERROR("MANUAL_ERROR", "manual error occurred."),
    NOT_FOUND_ERROR("NOT_FOUNT_ERROR", "not found error occurred."),
    INVALID_REQUEST_PARAM("INVALID_REQUEST_PARAM", "invalid request parameter error occurred."),

    // ENCRYPT
    ENCRYPT_ERROR("ENCRYPT_ERROR", "encrypt error occurred"),
    DECRYPT_ERROR("DECRYPT_ERROR", "decrypt error occurred"),
    ;

    fun toResponse(): ResponseEntity<Response> {
        val response = Response(this.code, this.message)
        return ResponseEntity
            .status(this.statusCode)
            .body(response)
    }

    fun toResponse(body: Any): ResponseEntity<Response> {
        val response = Response(this.code, this.message, body)
        return ResponseEntity
            .status(this.statusCode)
            .body(response)
    }

    fun toResponse(headers: HttpHeaders): ResponseEntity<Response> {
        val response = Response(this.code, this.message)
        return ResponseEntity
            .status(this.statusCode)
            .headers(headers)
            .body(response)
    }

    fun toResponse(headers: HttpHeaders, body: Any): ResponseEntity<Response> {
        val response = Response(this.code, this.message, body)
        return ResponseEntity
            .status(this.statusCode)
            .headers(headers)
            .body(response)
    }

    fun print(): String {
        return "ResponseCode - status: $statusCode, code : $code, message: $message"
    }
}