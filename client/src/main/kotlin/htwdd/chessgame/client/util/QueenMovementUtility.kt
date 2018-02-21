package htwdd.chessgame.client.util

import htwdd.chessgame.client.model.Match

class QueenMovementUtility : MovementUtility {
    private val bishop = BishopMovementUtility()
    private val rook = RookMovementUtility()

    override fun getMovementFields(movementFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        bishop.getMovementFields(movementFields, row, col, match)
        rook.getMovementFields(movementFields, row, col, match)
    }

    override fun getThreadedFields(threatedFields: HashSet<Pair<Int, Int>>, row: Int, col: Int, match: Match) {
        bishop.getThreadedFields(threatedFields, row, col, match)
        rook.getThreadedFields(threatedFields, row, col, match)
    }
}