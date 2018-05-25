package htwdd.chessgame.server.dto

import java.io.Serializable

/**
 * Data transfer object for a draw
 *
 * @property matchId Match reference
 * @property drawCode SAN code to indicate draw properties
 * @property startColumn Value to indicate the column of start field
 * @property startRow Value to indicate the row of start field
 */
data class DrawDTO(
        val matchId: Int = 0,
        val drawCode: String = "",
        val startColumn: Int? = null,
        val startRow: Int? = null
) : Serializable