package htwdd.chessgame.server.dto

import java.io.Serializable

/**
 * Data transfer object for a match
 *
 * @property playerWhiteId Player reference for the color white
 * @property playerBlackId Player reference for the color black
 */
data class MatchDTO(
        val playerWhiteId: Int = 0,
        val playerBlackId: Int = 0
) : Serializable