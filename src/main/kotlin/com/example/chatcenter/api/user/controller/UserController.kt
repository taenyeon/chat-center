package com.example.chatcenter.api.user.controller

import com.example.chatcenter.common.http.domain.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.chatcenter.api.user.domain.dto.JoinRequest
import com.example.chatcenter.api.user.domain.dto.JwtToken
import com.example.chatcenter.api.user.domain.dto.LoginRequest
import com.example.chatcenter.api.user.domain.dto.UpdateRequest
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
        return ResponseCode.SUCCESS.toResponse(userService.findUser())
    }

    @PostMapping("/join")
    fun join(@RequestBody joinRequest: JoinRequest): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(userService.join(joinRequest))
    }

    @PutMapping("")
    fun update(@RequestBody updateRequest: UpdateRequest): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(userService.update(updateRequest))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(userService.login(loginRequest))
    }

    @DeleteMapping("/logout")
    fun logout(): ResponseEntity<Response> {
        userService.logout()
        return ResponseCode.SUCCESS.toResponse()
    }

    @PostMapping("/accessToken")
    fun reIssueAccessToken(@RequestBody jwtToken: JwtToken): ResponseEntity<Response> {
        val refreshToken = jwtToken.refreshToken
        log.info("refreshToken : $refreshToken")

        if (refreshToken.isBlank()) throw ResponseException(ResponseCode.INVALID_REQUEST_PARAM)
        val resultJwtToken = JwtToken(
            userService.reIssueAccessToken(refreshToken),
            refreshToken
        )
        return ResponseCode.SUCCESS.toResponse(resultJwtToken)
    }


}