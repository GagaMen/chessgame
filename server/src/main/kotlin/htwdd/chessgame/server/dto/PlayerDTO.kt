package htwdd.chessgame.server.dto

import java.io.Serializable

data class PlayerDTO(
        val name: String = "",
        val password: String = ""
) : Serializable