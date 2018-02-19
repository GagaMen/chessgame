package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor

interface MovementUtility {
    fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>,
                          row: Int,
                          col: Int,
                          pieceColor: PieceColor,
                          match: Match? = null)

    fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>,
                          row: Int,
                          col: Int,
                          pieceColor: PieceColor,
                          match: Match? = null)
}