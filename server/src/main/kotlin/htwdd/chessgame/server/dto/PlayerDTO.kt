package htwdd.chessgame.server.dto

import java.io.Serializable

/**
 * Data transfer object for a player
 *
 * @author Felix Dimmel
 *
 * @property name The name of the player
 * @property password The password of the player
 *
 * @since 1.0.0
 */
data class PlayerDTO(
        val name: String = "",
        val password: String = ""
) : Serializable