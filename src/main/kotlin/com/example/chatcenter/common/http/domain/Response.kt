package com.example.chatcenter.common.http.domain

class Response(
    var resultCode: String,
    var resultMessage: String,
    var body: Any? = null
) {

}