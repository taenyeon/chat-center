package com.example.chatcenter.api.member.controller

import com.example.chatcenter.api.member.service.MemberService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import com.example.chatcenter.common.function.logger
import com.example.chatcenter.common.function.user
import com.example.chatcenter.common.http.constant.ResponseCode
import com.example.chatcenter.common.http.domain.Response

@RestController
@RequestMapping("/api/member")
class MemberController(private val memberService: MemberService) {
    val log = logger()

    @GetMapping("/{username}")
    fun select(@PathVariable username: String): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(memberService.findMemberDto(username))
    }

    @GetMapping("")
    fun selectList(): ResponseEntity<Response> {
        return ResponseCode.SUCCESS.toResponse(memberService.findMemberDtoList(user().id!!))
    }



}