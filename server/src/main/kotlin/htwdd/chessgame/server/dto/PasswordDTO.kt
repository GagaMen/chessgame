package htwdd.chessgame.server.dto

import java.io.Serializable

/**
 * Data transfer object for a player password
 *
 * @property password The updated password of the player
 */
data class PasswordDTO(
        val password: String = ""
) : Serializable