package htwdd.chessgame.server.dto

import java.io.Serializable

/**
 * Data transfer object for a match
 *
 * @author Felix Dimmel
 *
 * @property playerWhiteId Player reference for the color white
 * @property playerBlackId Player reference for the color black
 *
 * @since 1.0.0
 */
data class MatchDTO(
        val playerWhiteId: Int = 0,
        val playerBlackId: Int = 0
) : Serializable