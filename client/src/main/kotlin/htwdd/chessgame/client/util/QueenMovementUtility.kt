package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match
import htwdd.chessgame.client.model.PieceColor

class QueenMovementUtility : MovementUtility {
    private val bishop = BishopMovementUtility()
    private val rook = RookMovementUtility()

    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, pieceColor: PieceColor, match: Match?) {
        bishop.getMovementFields(movementFields, row, col, pieceColor)
        rook.getMovementFields(movementFields, row, col, pieceColor)
    }

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, pieceColor: PieceColor, match: Match?) {
        getMovementFields(threatedFields, row, col, pieceColor, match)
    }
}