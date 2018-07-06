package htwdd.chessgame.server.dto

import java.io.Serializable

/**
 * Data transfer object for a matchId
 *
 * @author Felix Dimmel
 *
 * @property matchId Match reference
 *
 * @since 1.0.0
 */
class MatchIdDTO(
        val matchId: Int = 0
) : Serializable