package htwdd.chessgame.server.dto

import java.io.Serializable

/**
 * Data transfer object for a player
 *
 * @property name The name of the player
 * @property password The password of the player
 */
data class PlayerDTO(
        val name: String = "",
        val password: String = ""
) : Serializable