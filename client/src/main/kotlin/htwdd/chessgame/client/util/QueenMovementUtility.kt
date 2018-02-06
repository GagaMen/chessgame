package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.PieceColor

class QueenMovementUtility : MovementUtility {
    private val bishop = BishopMovementUtility()
    private val rook = RookMovementUtility()

    override fun setValidDropFields(validDropFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, pieceColor: PieceColor) {
        bishop.setValidDropFields(validDropFields, row, col, pieceColor)
        rook.setValidDropFields(validDropFields, row, col, pieceColor)
    }
}