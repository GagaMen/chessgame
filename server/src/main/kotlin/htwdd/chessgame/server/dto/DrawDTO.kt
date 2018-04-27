package htwdd.chessgame.server.dto

import java.io.Serializable

data class DrawDTO(
        val matchId: Int = 0,
        val drawCode: String = "",
        val startColumn: Int? = null,
        val startRow: Int? = null
) : Serializable