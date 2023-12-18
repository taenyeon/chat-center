package com.example.chatcenter.api.user.controller

import com.example.chatcenter.common.http.domain.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.chatcenter.api.user.domain.dto.JoinRequest
import com.example.chatcenter.api.user.domain.dto.JwtToken
import com.example.chatcenter.api.user.domain.dto.LoginRequest
import com.example.chatcenter.api.user.service.UserService
import com.example.chatcenter.common.exception.ResponseException
import com.example.chatcenter.common.function.logger
import com.example.chatcenter.common.function.user
import com.example.chatcenter.common.http.constant.ResponseCode

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
) {
    val log = logger()

    @GetMapping("")
    fun my(): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(userService.getUser())
    }

    @PostMapping("")
    fun join(@RequestBody joinRequest: JoinRequest): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(userService.join(joinRequest))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(userService.login(loginRequest))
    }

    @DeleteMapping("/logout")
    fun logout(): ResponseEntity<Response> {
        val user = user()
        log.info("logoutUser - id : ${user.id}")
        userService.logout(user.id!!)
        return ResponseCode.SUCCESS.toResponse()
    }

    @GetMapping("/accessToken")
    fun reIssueAccessToken(@RequestHeader(name = "refresh_token") refreshToken: String?): ResponseEntity<Response> {

        if (refreshToken.isNullOrBlank()) {
            throw ResponseException(ResponseCode.INVALID_REQUEST_PARAM)
        }

        val jwtToken = JwtToken(
            userService.reIssueAccessToken(refreshToken),
            refreshToken
        )
        return ResponseCode.SUCCESS.toResponse(jwtToken)
    }


}