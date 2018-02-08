package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor

interface MovementUtility {
    fun setValidDropFields(validDropFields: HashSet<Pair<Int, Int>>,
                           row: Int,
                           col: Int,
                           pieceColor: PieceColor,
                           match: Match? = null)
}