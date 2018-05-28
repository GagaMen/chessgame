package htwdd.chessgame.server.dto

import java.io.Serializable

/**
 * Data transfer object for a player password
 *
 * @author Felix Dimmel
 *
 * @property password The updated password of the player
 *
 * @since 1.0.0
 */
data class PasswordDTO(
        val password: String = ""
) : Serializable