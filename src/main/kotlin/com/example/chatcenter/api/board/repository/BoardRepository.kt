package com.example.chatcenter.api.board.repository

import com.example.chatcenter.api.board.domain.entity.Board
import com.example.chatcenter.api.member.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface BoardRepository : JpaRepository<Board, Long> {

}