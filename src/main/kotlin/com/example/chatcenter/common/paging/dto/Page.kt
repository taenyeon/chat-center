package com.example.chatcenter.common.paging.dto

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort


class Page {
    var currentPage : Int = 0
    var searchSize : Int = 10
    var isEnd: Boolean = false;

    constructor()
    constructor(currentPage : Int, searchSize: Int) {
        this.currentPage = currentPage
        this.searchSize = searchSize
    }

    fun request(): PageRequest {
        return PageRequest.of(currentPage, searchSize)
    }
}