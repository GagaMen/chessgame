package htwdd.chessgame.server.dto

import java.io.Serializable

data class MatchDTO(
        val playerWhiteId: Int = 0,
        val playerBlackId: Int = 0
) : Serializable